package com.manage.kernel.core.admin.service.business.impl;

import com.manage.base.act.*;
import com.manage.base.act.enums.ActVariable;
import com.manage.base.act.support.ActBusiness;
import com.manage.base.act.vars.ActApproveObj;
import com.manage.base.constant.Constants;
import com.manage.base.database.enums.ActProcess;
import com.manage.base.database.enums.FlowSource;
import com.manage.base.enums.ActStatus;
import com.manage.base.exception.ActTaskNotFoundException;
import com.manage.base.exception.NewsNotFoundException;
import com.manage.base.exception.NotFoundException;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.utils.CoreUtil;
import com.manage.base.utils.StringHandler;
import com.manage.kernel.core.admin.service.activiti.IActFlowService;
import com.manage.kernel.core.model.dto.ApproveDto;
import com.manage.kernel.core.model.dto.ApproveHistory;
import com.manage.kernel.core.model.dto.FlowDto;
import com.manage.kernel.core.model.dto.FlowNotification;
import com.manage.kernel.core.model.dto.NewestFlowDto;
import com.manage.kernel.core.model.dto.ProcessDetail;
import com.manage.kernel.core.admin.service.activiti.IActBusinessService;
import com.manage.kernel.core.admin.service.business.IFlowService;
import com.manage.kernel.core.model.parser.NewsParser;
import com.manage.kernel.jpa.entity.ActApproveTask;
import com.manage.kernel.jpa.entity.AdUser;
import com.manage.kernel.jpa.entity.FlowProcess;
import com.manage.kernel.jpa.entity.News;
import com.manage.kernel.jpa.repository.ActApproveTaskRepo;
import com.manage.kernel.jpa.repository.AdUserRepo;
import com.manage.kernel.jpa.repository.FlowProcessRepo;
import com.manage.kernel.spring.comm.Messages;
import com.manage.kernel.spring.comm.SessionHelper;
import javax.persistence.criteria.Predicate;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.activiti.engine.task.TaskQuery;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bert on 2017/10/6.
 */
@Service
public class FlowService implements IFlowService {

    @Autowired
    private AdUserRepo userRepo;

    @Autowired
    private ActApproveTaskRepo approveTaskRepo;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IActBusinessService businessService;

    @Autowired
    private IActFlowService actFlowService;

    @Autowired
    private FlowProcessRepo flowProcessRepo;

    @Override
    @Transactional
    public PageResult rejectTaskList(PageQuery page, FlowDto query) {
        PageResult result = new PageResult();
        List<FlowDto> flows = new ArrayList<>();
        String account = SessionHelper.user().getAccount();
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(account).active();
        if (StringHandler.isNotBlank(query.getSubject())) {
            taskQuery.processVariableValueLike(ActVariable.FLOW_SUBJECT.varName(), "%" + query.getSubject() + "%");
        }
        if (StringHandler.isNotBlank(query.getBusinessKey())) {
            taskQuery.processVariableValueLike(ActVariable.FLOW_BUSINESS.varName(), query.getBusinessKey() + "%");
        }
        if (StringHandler.isNotNull(query.getQueryTime())) {
            taskQuery.taskCreatedAfter(query.getQueryTime().toDate());
        }
        if (StringHandler.isNotNull(query.getQueryTimeEnd())) {
            taskQuery.taskCreatedBefore(query.getQueryTimeEnd().toDate());
        }
        long count = taskQuery.count();
        result.setTotal(count);
        result.setRows(flows);
        if (count == 0) {
            return result;
        }

        FlowDto flow;
        List<Task> tasks = taskQuery.orderByTaskCreateTime().desc().listPage(page.offset(), page.limit());
        for (Task task : tasks) {
            flow = taskWithReject(task);
            flows.add(flow);
        }
        return result;
    }

    private FlowDto taskWithReject(Task task) {
        FlowDto flow = new FlowDto();
        String processId = task.getProcessInstanceId();
        HistoricProcessInstance processInstance = getHistoricProcessInstance(processId);
        flow.setProcessId(processId);
        ActBusiness business = ActBusiness.fromBusinessKey(processInstance.getBusinessKey());
        flow.setBusinessKey(business.getBusinessId());
        flow.setBusinessType(business.getTypeName());

        flow.setFlowSource(business.getSource());
        flow.setRejectTime(CoreUtil.fromDate(task.getCreateTime()));
        ProcessVariable variable = businessService.getProcessVaribale(processId);
        flow.setSubject(variable.getSubject());

        return flow;
    }

    @Override
    public PageResult submitTaskList(PageQuery page, FlowDto query) {
        PageResult result = new PageResult();
        List<FlowDto> flows = new ArrayList<>();
        String account = SessionHelper.user().getAccount();

        HistoricProcessInstanceQuery processQuery = historyService.createHistoricProcessInstanceQuery()
                .startedBy(account);
        if (query.getQueryTime() != null) {
            processQuery.startedAfter(query.getQueryTime().toDate());
        }
        if (query.getQueryTimeEnd() != null) {
            processQuery.startedBefore(query.getQueryTimeEnd().toDate());
        }
        if (StringHandler.isNotBlank(query.getSubject())) {
            processQuery.variableValueLike(ActVariable.FLOW_SUBJECT.varName(), "%" + query.getSubject() + "%");
        }
        if (StringHandler.isNotBlank(query.getBusinessKey())) {
            processQuery.variableValueLike(ActVariable.FLOW_BUSINESS.varName(), query.getBusinessKey() + "%");
        }

        long count = processQuery.count();
        result.setTotal(count);
        result.setRows(flows);
        if (count == 0) {
            return result;
        }

        FlowDto flow;
        List<HistoricProcessInstance> processes = processQuery.orderByProcessInstanceStartTime().desc()
                .listPage(page.offset(), page.limit());
        for (HistoricProcessInstance process : processes) {
            flow = new FlowDto();
            flow.setProcessId(process.getId());
            flow.setProcessTime(CoreUtil.fromDate(process.getStartTime()));
            flow.setProcessTimeEnd(CoreUtil.fromDate(process.getEndTime()));
            if (process.getEndTime() == null) {
                flow.setStatus(ActStatus.PENDING);
            } else {
                flow.setStatus(ActStatus.COMPLETE);
            }
            List<HistoricTaskInstance> taskInstances = historyService.createHistoricTaskInstanceQuery()
                    .processInstanceId(process.getId()).orderByTaskCreateTime().desc().list();
            if (!CollectionUtils.isEmpty(taskInstances)) {
                HistoricTaskInstance task = taskInstances.get(0);
                if (task.getEndTime() == null) {
                    if (taskInstances.size() >= 2) {
                        flow.setTaskName(taskInstances.get(1).getName());
                    }
                    flow.setNextTaskName(task.getName());
                } else {
                    flow.setTaskName(task.getName());
                }

            }

            ActBusiness business = ActBusiness.fromBusinessKey(process.getBusinessKey());
            flow.setBusinessKey(business.getBusinessId());
            flow.setFlowSource(business.getSource());
            flow.setBusinessType(business.getTypeName());
            ProcessVariable variable = businessService.getProcessVaribale(process.getId());
            flow.setSubject(variable.getSubject());

            flows.add(flow);
        }
        return result;
    }

    @Override
    @Transactional
    public PageResult pendingTaskList(PageQuery page, FlowDto query) {

        PageResult result = new PageResult();
        List<FlowDto> flows = new ArrayList<>();
        String account = SessionHelper.user().getAccount();
        AdUser user = userRepo.findUserByAccount(account);
        String groupId = user.getApproveRole().actGroupId();
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateGroup(groupId).active();
        if (StringHandler.isNotBlank(query.getSubject())) {
            taskQuery.processVariableValueLike(ActVariable.FLOW_SUBJECT.varName(), "%" + query.getSubject() + "%");
        }
        if (StringHandler.isNotBlank(query.getBusinessKey())) {
            taskQuery.processVariableValueLike(ActVariable.FLOW_BUSINESS.varName(), query.getBusinessKey() + "%");
        }
        if (StringHandler.isNotNull(query.getQueryTime())) {
            taskQuery.taskCreatedAfter(query.getQueryTime().toDate());
        }
        if (StringHandler.isNotNull(query.getQueryTimeEnd())) {
            taskQuery.taskCreatedBefore(query.getQueryTime().toDate());
        }

        long count = taskQuery.count();
        result.setTotal(count);
        result.setRows(flows);
        if (count == 0) {
            return result;
        }
        List<Task> tasks = taskQuery.listPage(page.offset(), page.limit());
        for (Task task : tasks) {
            flows.add(taskWithPending(task));
        }
        return result;
    }

    private FlowDto taskWithPending(Task task) {
        FlowDto flow = new FlowDto();
        String processId = task.getProcessInstanceId();
        HistoricProcessInstance process = getHistoricProcessInstance(processId);
        flow.setTaskId(task.getId());
        flow.setTaskName(task.getName());
        flow.setProcessId(processId);

        ActBusiness business = ActBusiness.fromBusinessKey(process.getBusinessKey());
        flow.setBusinessKey(business.getBusinessId());
        flow.setBusinessType(business.getTypeName());
        flow.setFlowSource(business.getSource());
        flow.setApplyTime(LocalDateTime.fromDateFields(process.getStartTime()));
        flow.setReceiveTime(LocalDateTime.fromDateFields(task.getCreateTime()));
        ProcessVariable variable = businessService.getProcessVaribale(processId);
        flow.setSubject(variable.getSubject());
        ProcessUser processUser = businessService.getProcessUser(process.getStartUserId());
        flow.setApplyUser(processUser.getName());
        flow.setApplyUserOrgan(processUser.getOrganName());
        return flow;
    }

    @Override
    @Transactional
    public PageResult approvedTaskList(PageQuery page, FlowDto query) {
        Page<ActApproveTask> taskResult = approveTaskRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.equal(root.get("approveUser"), SessionHelper.user().getAccount()));
            if (StringHandler.isNotBlank(query.getSubject())) {
                list.add(cb.like(root.get("subject"), "%" + query.getSubject() + "%"));
            }
            if (StringHandler.isNotBlank(query.getBusinessKey())) {
                list.add(cb.like(root.get("businessKey"), query.getBusinessKey() + "%"));
            }
            if (StringHandler.isNotNull(query.getQueryTime())) {
                list.add(cb.greaterThanOrEqualTo(root.get("approveTime"), query.getQueryTime()));
            }
            if (StringHandler.isNotNull(query.getQueryTimeEnd())) {
                list.add(cb.lessThanOrEqualTo(root.get("approveTime"), query.getQueryTimeEnd()));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, page.sortPageDefault("id"));

        PageResult<FlowDto> pageResult = new PageResult<>();

        FlowDto flow;
        List<FlowDto> flowDtos = new ArrayList<>();
        for (ActApproveTask approveTask : taskResult.getContent()) {
            flow = new FlowDto();
            flow.setTaskId(approveTask.getTaskId());
            flow.setTaskName(approveTask.getTaskName());
            flow.setProcessId(approveTask.getProcInstId());
            flow.setSubject(approveTask.getSubject());
            ActBusiness business = ActBusiness.fromBusinessKey(approveTask.getBusinessKey());
            flow.setBusinessKey(business.getBusinessId());
            flow.setBusinessType(business.getTypeName());
            flow.setFlowSource(business.getSource());
            flow.setProcess(approveTask.getApproveResult());
            flow.setProcessTime(approveTask.getApproveTime());
            ProcessUser user = businessService.getProcessUser(approveTask.getApplyUser());
            flow.setApplyUser(user.getName());
            flow.setApplyUserOrgan(user.getOrganName());
            flowDtos.add(flow);
        }

        pageResult.setTotal(taskResult.getTotalElements());
        pageResult.setRows(flowDtos);
        return pageResult;
    }

    @Override
    @Transactional
    public ProcessDetail approveTask(ApproveDto approveDto) {

        Task task = taskService.createTaskQuery().taskId(approveDto.getTaskId()).singleResult();
        if (task == null) {
            throw new ActTaskNotFoundException();
        }

        String account = SessionHelper.user().getAccount();
        ActApproveObj approve = new ActApproveObj();
        approve.setUserId(account);
        approve.setProcess(approveDto.getProcess());
        approve.setComment(approveDto.getComment());
        taskService.setVariableLocal(task.getId(), ActVariable.TASK_APPROVE.varName(), approve);
        taskService.addComment(task.getId(), task.getProcessInstanceId(), approve.getComment());

        Map<String, Object> map = new HashMap<>();
        map.put(ActVariable.FLOW_ACTION.varName(), approveDto.getProcess().action());
        taskService.complete(task.getId(), map);

        HistoricProcessInstance process = getHistoricProcessInstance(task.getProcessInstanceId());
        String businessId = process.getBusinessKey();
        boolean processEnd = false;
        if (process.getEndTime() != null) {
            processEnd = true;
        }

        businessService.saveApproveTask(task, businessId, approve);
        actFlowService.handleBusinessStatus(businessId, approveDto.getProcess(), processEnd);
        return processDetail(task.getProcessInstanceId(), true);
    }

    @Override
    @Transactional
    public ProcessDetail processDetail(String processId, boolean isApprove) {

        HistoricProcessInstance processInstance = getHistoricProcessInstance(processId);
        if (processInstance == null) {
            return null;
        }

        ProcessDetail detail = new ProcessDetail();
        detail.setProcessId(processId);
        ActBusiness business = ActBusiness.fromBusinessKey(processInstance.getBusinessKey());
        detail.setFlowSource(business.getSource());
        detail.setBusinessKey(business.getBusinessId());
        detail.setBusinessType(business.getTypeName());
        ProcessVariable variable = businessService.getProcessVaribale(processId);
        detail.setSubject(variable.getSubject());
        HistoricProcessInstance process = getHistoricProcessInstance(processId);
        if (process != null) {
            ProcessUser processUser = businessService.getProcessUser(process.getStartUserId());
            detail.setApplyUser(processUser.getName());
            detail.setApplyUserOrgan(processUser.getOrganName());
            detail.setApplyTime(LocalDateTime.fromDateFields(process.getStartTime()));
        }

        detail.setApproveHistories(approveHistory(processId));
        if (!isApprove) {
            return detail;
        }

        String account = SessionHelper.user().getAccount();
        AdUser user = userRepo.findUserByAccount(account);
        Task task = taskService.createTaskQuery().processInstanceId(processId)
                .taskCandidateGroup(user.getApproveRole().actGroupId()).singleResult();
        if (task == null) {
            detail.setCanApprove(false);
        } else {
            detail.setCanApprove(true);
        }
        return detail;
    }

    private List<ApproveHistory> approveHistory(String processId) {

        List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery()
                .activityType(Constants.ACT_USER_TASK).processInstanceId(processId)
                .orderByHistoricActivityInstanceStartTime().asc().list();
        List<ApproveHistory> histories = new ArrayList<>();
        ApproveHistory history;
        for (HistoricActivityInstance activity : activityInstances) {
            if (activity.getEndTime() == null) {
                continue;
            }
            ActApproveObj approve = getHistoricActApprove(activity.getTaskId());
            if (approve == null) {
                continue;
            }
            history = new ApproveHistory();
            List<Comment> comments = taskService.getTaskComments(activity.getTaskId());
            StringBuilder builder = new StringBuilder();
            for (Comment comment : comments) {
                builder.append(comment.getFullMessage());
            }
            TaskInfo task = getTaskInfo(activity.getTaskId());
            if (task != null) {
                history.setTaskName(task.getName());
            }
            history.setComment(builder.toString());
            history.setProcess(approve.getProcess());
            history.setStartTime(LocalDateTime.fromDateFields(activity.getStartTime()));
            history.setEndTime(LocalDateTime.fromDateFields(activity.getEndTime()));

            ProcessUser processUser = businessService.getProcessUser(approve.getUserId());
            history.setApproveUser(processUser.getName());
            history.setApproveUserOrgan(processUser.getOrganName());

            histories.add(history);
        }

        return histories;
    }

    private HistoricProcessInstance getHistoricProcessInstance(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    private ActApproveObj getHistoricActApprove(String taskId) {
        HistoricVariableInstance variable = historyService.createHistoricVariableInstanceQuery().taskId(taskId)
                .variableName(ActVariable.TASK_APPROVE.varName()).singleResult();
        if (variable == null) {
            return null;
        }
        return (ActApproveObj) variable.getValue();
    }

    private TaskInfo getTaskInfo(String taskId) {
        TaskInfo task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task != null) {
            return task;
        }
        task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        if (task != null) {
            return task;
        }
        return null;
    }

    private void clear() {
        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment : deployments) {
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }

    @Override
    @Transactional
    public NewestFlowDto newestFlow(AdUser adUser) {
        NewestFlowDto newestFlow = new NewestFlowDto();
        AdUser user = userRepo.findUserByAccount(adUser.getAccount());
        String groupId = user.getApproveRole().actGroupId();

        List<FlowDto> pendingFlows = new ArrayList<>();
        List<Task> pendingTesks = taskService.createTaskQuery().taskCandidateGroup(groupId).active().list();
        for (Task task : pendingTesks) {
            pendingFlows.add(taskWithPending(task));
        }

        List<FlowDto> rejectFlows = new ArrayList<>();
        List<Task> rejectTasks = taskService.createTaskQuery().taskAssignee(adUser.getAccount()).active().list();
        for (Task task : rejectTasks) {
            rejectFlows.add(taskWithReject(task));
        }

        newestFlow.setPendingTask(pendingFlows);
        newestFlow.setRejectTask(rejectFlows);
        return newestFlow;
    }

    @Override
    public FlowNotification notification(AdUser adUser) {
        FlowNotification notification = new FlowNotification();
        AdUser user = userRepo.findUserByAccount(adUser.getAccount());
        String groupId = user.getApproveRole().actGroupId();
        notification.setPendingCount(taskService.createTaskQuery().taskCandidateGroup(groupId).active().count());
        notification.setRejectCount(taskService.createTaskQuery().taskAssignee(adUser.getAccount()).active().count());
        notification.calculateTotal();
        return notification;
    }

    @Override
    @Transactional
    public void cancelProcess(String processId) {
        String account = SessionHelper.user().getAccount();
        Task task = taskService.createTaskQuery().processInstanceId(processId).taskAssignee(account).active()
                .singleResult();
        if (task == null) {
            throw new ActTaskNotFoundException();
        }

        Map<String, Object> vars = new HashMap<>();
        vars.put(ActVariable.FLOW_ACTION.varName(), ActProcess.CANCEL.action());
        taskService.complete(task.getId(), vars);
        HistoricProcessInstance processInstance = getHistoricProcessInstance(task.getProcessInstanceId());

        ActApproveObj approve = new ActApproveObj();
        approve.setUserId(account);
        approve.setComment(Messages.get("text.act.comment.cancel"));
        approve.setProcess(ActProcess.CANCEL);

        String businessId = processInstance.getBusinessKey();
        businessService.saveApproveTask(task, businessId, approve);
        actFlowService.handleBusinessStatus(businessId, ActProcess.CANCEL, false);
    }

    @Override
    @Transactional
    public Object businessObject(String businessId) {

        FlowProcess flowProcess = flowProcessRepo.findByBusinessId(businessId);
        if (flowProcess == null) {
            throw new NotFoundException();
        }

        if (flowProcess.getSource() == FlowSource.NEWS) {
            News news = flowProcess.getNews();
            if (news == null) {
                throw new NewsNotFoundException();
            }
            return NewsParser.toDto(news);
        }

        throw new NotFoundException();
    }
}

