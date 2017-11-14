package com.manage.kernel.core.admin.service.business.impl;

import com.manage.base.act.*;
import com.manage.base.act.enums.ActVariable;
import com.manage.base.act.support.ActBusiness;
import com.manage.base.act.vars.ActApproveObj;
import com.manage.base.act.vars.ActParams;
import com.manage.base.constant.Constants;
import com.manage.base.database.enums.ActProcess;
import com.manage.base.database.enums.FlowSource;
import com.manage.base.enums.ActStatus;
import com.manage.base.enums.NewsMachine;
import com.manage.base.exception.ActNotSupportException;
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
import java.util.List;

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
        String actUserId = SessionHelper.userAccount();
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskAssignee(actUserId).active();
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
        flow.setProcessId(processId);
        flow.setSubject(getSubjectByProcessId(processId));
        HistoricProcessInstance historicProcess = getHistoricProcessInstance(processId);
        ActBusiness business = ActBusiness.fromBusinessKey(historicProcess.getBusinessKey());
        flow.setBusinessKey(business.getBusinessId());
        flow.setBusinessType(business.getTypeName());
        flow.setFlowSource(business.getSource());
        flow.setRejectTime(CoreUtil.fromDate(task.getCreateTime()));
        return flow;
    }

    @Override
    public PageResult submitTaskList(PageQuery page, FlowDto query) {
        PageResult result = new PageResult();
        List<FlowDto> flows = new ArrayList<>();
        String actUserId = SessionHelper.userAccount();
        HistoricProcessInstanceQuery processQuery = historyService.createHistoricProcessInstanceQuery();
        processQuery.startedBy(actUserId);
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
            flow.setSubject(getSubjectByProcessId(process.getId()));
            flow.setProcessTime(CoreUtil.fromDate(process.getStartTime()));
            flow.setProcessTimeEnd(CoreUtil.fromDate(process.getEndTime()));
            if (process.getEndTime() == null) {
                flow.setStatus(ActStatus.PENDING);
            } else {
                flow.setStatus(ActStatus.COMPLETE);
            }
            HistoricTaskInstanceQuery historicTaskQuery = historyService.createHistoricTaskInstanceQuery();
            List<HistoricTaskInstance> historicTasks = historicTaskQuery.processInstanceId(process.getId())
                    .orderByTaskCreateTime().desc().list();
            if (!CollectionUtils.isEmpty(historicTasks)) {
                HistoricTaskInstance task = historicTasks.get(0);
                if (task.getEndTime() == null) {
                    if (historicTasks.size() >= 2) {
                        flow.setTaskName(historicTasks.get(1).getName());
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
            flows.add(flow);
        }
        return result;
    }

    @Override
    @Transactional
    public PageResult pendingTaskList(PageQuery page, FlowDto query) {

        PageResult result = new PageResult();
        List<FlowDto> flows = new ArrayList<>();
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateGroup(userActGroupId()).active();
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

    private String userActGroupId() {
        AdUser user = userRepo.findUserByAccount(SessionHelper.user().getAccount());
        return NewsMachine.actGroupId(user.getApproveRole(), user.getOrganCode());
    }

    private FlowDto taskWithPending(Task task) {
        String processId = task.getProcessInstanceId();
        FlowDto flow = new FlowDto();
        flow.setTaskId(task.getId());
        flow.setTaskName(task.getName());
        flow.setProcessId(processId);
        flow.setSubject(getSubjectByProcessId(processId));
        HistoricProcessInstance historicProcess = getHistoricProcessInstance(processId);
        ActBusiness business = ActBusiness.fromBusinessKey(historicProcess.getBusinessKey());
        flow.setBusinessKey(business.getBusinessId());
        flow.setBusinessType(business.getTypeName());
        flow.setFlowSource(business.getSource());
        flow.setApplyTime(LocalDateTime.fromDateFields(historicProcess.getStartTime()));
        flow.setReceiveTime(LocalDateTime.fromDateFields(task.getCreateTime()));
        ProcessUser processUser = businessService.getProcessUser(historicProcess.getStartUserId());
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

        ActProcess actProcess = approveDto.getProcess();
        String account = SessionHelper.user().getAccount();
        ActApproveObj approve = new ActApproveObj();
        approve.setUserId(account);
        approve.setProcess(actProcess);
        approve.setComment(approveDto.getComment());
        taskService.setVariableLocal(task.getId(), ActVariable.TASK_APPROVE.varName(), approve);
        taskService.addComment(task.getId(), task.getProcessInstanceId(), approve.getComment());

        FlowProcess flowProcess = flowProcessRepo.findByProcessId(task.getProcessInstanceId());
        if (flowProcess == null) {
            throw new ActNotSupportException();
        }

        ActParams params = new ActParams();
        params.setFlowAction(approveDto.getProcess());
        params.setApproveGroups(
                NewsMachine.nextGroupIds(task.getTaskDefinitionKey(), actProcess, flowProcess.getApplyOrganCode()));
        taskService.complete(task.getId(), params.build());
        HistoricProcessInstance process = getHistoricProcessInstance(task.getProcessInstanceId());
        String businessId = process.getBusinessKey();
        boolean processEnd = false;
        if (process.getEndTime() != null) {
            processEnd = true;
        }
        flowProcessRepo.save(flowProcess);
        businessService.saveApproveTask(task, businessId, approve);
        actFlowService.handleBusinessStatus(businessId, approveDto.getProcess(), processEnd);
        return processDetail(task.getProcessInstanceId(), true);
    }

    @Override
    @Transactional
    public void cancelTask(String processId) {
        String actUserId = SessionHelper.userAccount();
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.processInstanceId(processId).taskAssignee(actUserId).active().singleResult();
        if (task == null) {
            throw new ActTaskNotFoundException();
        }
        ActProcess process = ActProcess.CANCEL;
        ActParams params = new ActParams();
        params.setFlowAction(process);
        taskService.complete(task.getId(), params.build());
        HistoricProcessInstance processInstance = getHistoricProcessInstance(task.getProcessInstanceId());
        ActApproveObj approve = new ActApproveObj();
        approve.setUserId(actUserId);
        approve.setComment(Messages.get("text.act.comment.cancel"));
        approve.setProcess(process);
        String businessId = processInstance.getBusinessKey();
        businessService.saveApproveTask(task, businessId, approve);
        actFlowService.handleBusinessStatus(businessId, process, false);
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
        detail.setSubject(getSubjectByProcessId(processId));
        ActBusiness business = ActBusiness.fromBusinessKey(processInstance.getBusinessKey());
        detail.setFlowSource(business.getSource());
        detail.setBusinessKey(business.getBusinessId());
        detail.setBusinessType(business.getTypeName());
        HistoricProcessInstance process = getHistoricProcessInstance(processId);
        if (process != null) {
            ProcessUser processUser = businessService.getProcessUser(process.getStartUserId());
            detail.setApplyUser(processUser.getName());
            detail.setApplyUserOrgan(processUser.getOrganName());
            detail.setApplyTime(LocalDateTime.fromDateFields(process.getStartTime()));
        }
        detail.setApproveHistories(processApproveHistory(processId));
        if (!isApprove) {
            return detail;
        }
        TaskQuery taskQuery = taskService.createTaskQuery();
        Task task = taskQuery.processInstanceId(processId).taskCandidateGroup(userActGroupId()).singleResult();
        if (task == null) {
            detail.setCanApprove(false);
        } else {
            detail.setCanApprove(true);
        }
        return detail;
    }

    private List<ApproveHistory> processApproveHistory(String processId) {
        HistoricActivityInstanceQuery historicActivityQuery = historyService.createHistoricActivityInstanceQuery();
        List<HistoricActivityInstance> activityInstances = historicActivityQuery.activityType(Constants.ACT_USER_TASK)
                .processInstanceId(processId).orderByHistoricActivityInstanceStartTime().asc().list();
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

    @Override
    @Transactional
    public NewestFlowDto newestFlow(AdUser adUser) {
        NewestFlowDto newestFlow = new NewestFlowDto();
        List<FlowDto> pendingFlows = new ArrayList<>();
        List<Task> pendingTesks = taskService.createTaskQuery().taskCandidateGroup(userActGroupId()).active().list();
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
        FlowNotification message = new FlowNotification();
        message.setPendingCount(taskService.createTaskQuery().taskCandidateGroup(userActGroupId()).active().count());
        message.setRejectCount(taskService.createTaskQuery().taskAssignee(adUser.getAccount()).active().count());
        message.calculateTotal();
        return message;
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

    private String getSubjectByProcessId(String processId) {
        ProcessVariable variable = businessService.getProcessVaribale(processId);
        if (variable == null) {
            return null;
        }
        return variable.getSubject();
    }
}

