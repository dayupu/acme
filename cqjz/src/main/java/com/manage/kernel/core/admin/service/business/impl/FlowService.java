package com.manage.kernel.core.admin.service.business.impl;

import com.manage.base.act.ActApprove;
import com.manage.base.act.ActBusiness;
import com.manage.base.act.ActSource;
import com.manage.base.act.ProcessVariable;
import com.manage.base.constant.ActConstants;
import com.manage.base.database.enums.NewsType;
import com.manage.base.enums.ActStatus;
import com.manage.base.exception.ActTaskNotFoundException;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.utils.CoreUtil;
import com.manage.base.utils.StringUtil;
import com.manage.kernel.core.admin.apply.dto.ApproveDto;
import com.manage.kernel.core.admin.apply.dto.ApproveHistory;
import com.manage.kernel.core.admin.apply.dto.FlowDto;
import com.manage.kernel.core.admin.apply.dto.ProcessDetail;
import com.manage.kernel.core.admin.service.activiti.IActBusinessService;
import com.manage.kernel.core.admin.service.business.IFlowService;
import com.manage.kernel.jpa.entity.ActApproveTask;
import com.manage.kernel.jpa.entity.AdUser;
import com.manage.kernel.jpa.repository.ActApproveTaskRepo;
import com.manage.kernel.jpa.repository.AdUserRepo;
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

    @Override
    @Transactional
    public PageResult rejectTaskList(PageQuery page, FlowDto query) {
        PageResult result = new PageResult();
        List<FlowDto> flows = new ArrayList<>();
        String account = SessionHelper.user().getAccount();
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(account).active();
        if (StringUtil.isNotBlank(query.getSubject())) {
            taskQuery.processVariableValueLike(ActConstants.PROCESS_SUBJECT, "%" + query.getSubject() + "%");
        }
        if (StringUtil.isNotBlank(query.getProcessType())) {
            taskQuery.processVariableValueLike(ActConstants.PROCESS_TYPE, query.getProcessType() + "%");
        }
        if (StringUtil.isNotNull(query.getQueryTime())) {
            taskQuery.taskCreatedAfter(query.getQueryTime().toDate());
        }
        if (StringUtil.isNotNull(query.getQueryTimeEnd())) {
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
            flow = new FlowDto();
            String processId = task.getProcessInstanceId();
            HistoricProcessInstance processInstance = getHistoricProcessInstance(processId);
            ProcessVariable variable = businessService.getProcessVaribale(processId);

            flow.setProcessId(processId);
            flow.setSubject(variable.getSubject());
            ActBusiness business = ActBusiness.fromBusinessKey(processInstance.getBusinessKey());
            flow.setBusinessNumber(business.getNumber());
            flow.setBusinessSource(business.getSource());
            flow.setProcessType(variable.getProcessTypeName());
            flow.setRejectTime(CoreUtil.fromDate(task.getCreateTime()));
            flows.add(flow);
        }
        return result;
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
        if (StringUtil.isNotBlank(query.getSubject())) {
            processQuery.variableValueLike(ActConstants.PROCESS_SUBJECT, "%" + query.getSubject() + "%");
        }
        if (StringUtil.isNotBlank(query.getProcessType())) {
            processQuery.variableValueLike(ActConstants.PROCESS_TYPE, query.getProcessType() + "%");
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
            ProcessVariable variable = businessService.getProcessVaribale(process.getId());
            flow.setSubject(variable.getSubject());
            ActBusiness business = ActBusiness.fromBusinessKey(process.getBusinessKey());
            flow.setBusinessNumber(business.getNumber());
            flow.setBusinessSource(business.getSource());
            flow.setProcessType(variable.getProcessTypeName());
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
        if (StringUtil.isNotBlank(query.getSubject())) {
            taskQuery.processVariableValueLike(ActConstants.PROCESS_SUBJECT, "%" + query.getSubject() + "%");
        }
        if (StringUtil.isNotBlank(query.getProcessType())) {
            taskQuery.processVariableValueLike(ActConstants.PROCESS_TYPE, query.getProcessType() + "%");
        }
        if (StringUtil.isNotNull(query.getQueryTime())) {
            taskQuery.taskCreatedAfter(query.getQueryTime().toDate());
        }
        if (StringUtil.isNotNull(query.getQueryTimeEnd())) {
            taskQuery.taskCreatedBefore(query.getQueryTime().toDate());
        }

        long count = taskQuery.count();
        result.setTotal(count);
        result.setRows(flows);
        if (count == 0) {
            return result;
        }

        List<Task> tasks = taskQuery.listPage(page.offset(), page.limit());
        FlowDto flow;
        String processId;
        for (Task task : tasks) {
            flow = new FlowDto();
            processId = task.getProcessInstanceId();
            HistoricProcessInstance processInstance = getHistoricProcessInstance(processId);
            if (processInstance == null) {
                continue;
            }

            ProcessVariable variable = businessService.getProcessVaribale(processId);
            flow.setTaskId(task.getId());
            flow.setTaskName(task.getName());
            flow.setProcessId(processId);
            flow.setSubject(variable.getSubject());
            ActBusiness business = ActBusiness.fromBusinessKey(processInstance.getBusinessKey());
            flow.setBusinessNumber(business.getNumber());
            flow.setBusinessSource(business.getSource());
            flow.setProcessType(variable.getProcessTypeName());
            flow.setApplyUser(getUserName(processInstance.getStartUserId()));
            flow.setApplyTime(LocalDateTime.fromDateFields(processInstance.getStartTime()));
            flow.setReceiveTime(LocalDateTime.fromDateFields(task.getCreateTime()));
            flows.add(flow);
        }
        return result;
    }

    @Override
    @Transactional
    public PageResult approvedTaskList(PageQuery page, FlowDto query) {
        Page<ActApproveTask> userPage = approveTaskRepo.findAll((root, criteriaQuery, cb) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(cb.equal(root.get("approveUser"), SessionHelper.user().getAccount()));
            if (StringUtil.isNotBlank(query.getSubject())) {
                list.add(cb.like(root.get("subject"), "%" + query.getSubject() + "%"));
            }
            if (StringUtil.isNotBlank(query.getProcessType())) {
                list.add(cb.like(root.get("processType"), query.getProcessType() + "%"));
            }
            if (StringUtil.isNotNull(query.getQueryTime())) {
                list.add(cb.greaterThanOrEqualTo(root.get("approveTime"), query.getQueryTime()));
            }
            if (StringUtil.isNotNull(query.getQueryTimeEnd())) {
                list.add(cb.lessThanOrEqualTo(root.get("approveTime"), query.getQueryTimeEnd()));
            }
            return cb.and(list.toArray(new Predicate[0]));
        }, page.sortPageDefault("id"));

        PageResult<FlowDto> pageResult = new PageResult<>();

        FlowDto flow;
        List<FlowDto> flowDtos = new ArrayList<>();
        for (ActApproveTask approveTask : userPage.getContent()) {
            flow = new FlowDto();
            flow.setTaskId(approveTask.getTaskId());
            flow.setTaskName(approveTask.getTaskName());
            flow.setProcessId(approveTask.getProcInstId());
            flow.setSubject(approveTask.getSubject());
            ActBusiness business = ActBusiness.fromBusinessKey(approveTask.getBusinessKey());
            flow.setBusinessNumber(business.getNumber());
            flow.setBusinessSource(business.getSource());
            flow.setProcessType(ActSource.processTypeName(approveTask.getProcessType()));
            flow.setProcess(approveTask.getApproveResult());
            flow.setProcessTime(approveTask.getApproveTime());
            flowDtos.add(flow);
        }

        pageResult.setTotal(userPage.getTotalElements());
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
        ActApprove approve = new ActApprove();
        approve.setUserId(account);
        approve.setProcess(approveDto.getProcess());
        approve.setComment(approveDto.getComment());
        taskService.setVariableLocal(task.getId(), ActConstants.TASK_APPROVE, approve);
        taskService.addComment(task.getId(), task.getProcessInstanceId(), approve.getComment());
        Map<String, Object> map = new HashMap<>();
        map.put(ActConstants.TEMP_ACTION, approveDto.getProcess().action());
        taskService.complete(task.getId(), map);

        HistoricProcessInstance processInstance = getHistoricProcessInstance(task.getProcessInstanceId());
        ActBusiness actBusiness = ActBusiness.fromBusinessKey(processInstance.getBusinessKey());
        boolean processEnd = false;
        if (processInstance.getEndTime() != null) {
            processEnd = true;
        }

        businessService.saveApproveTask(task, actBusiness, approve);
        businessService.changeStatus(actBusiness, approveDto.getProcess(), processEnd);
        return processDetail(task.getProcessInstanceId(), true);
    }

    @Override
    @Transactional
    public ProcessDetail processDetail(String processId, boolean isApprove) {

        HistoricProcessInstance processInstance = getHistoricProcessInstance(processId);
        if (processInstance == null) {
            return null;
        }

        ProcessVariable variable = businessService.getProcessVaribale(processId);
        ProcessDetail detail = new ProcessDetail();
        detail.setProcessId(processId);
        detail.setSubject(variable.getSubject());
        ActBusiness business = ActBusiness.fromBusinessKey(processInstance.getBusinessKey());
        detail.setBusinessNumber(business.getNumber());
        detail.setBusinessSource(business.getSource());
        detail.setProcessType(variable.getProcessTypeName());
        HistoricProcessInstance historicProcessInstance = getHistoricProcessInstance(processId);
        if (historicProcessInstance != null) {
            detail.setApplyUser(getUserName(historicProcessInstance.getStartUserId()));
            detail.setApplyTime(LocalDateTime.fromDateFields(historicProcessInstance.getStartTime()));
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
                .activityType(ActConstants.NODE_USER_TASK).processInstanceId(processId)
                .orderByHistoricActivityInstanceStartTime().asc().list();
        List<ApproveHistory> histories = new ArrayList<>();
        ApproveHistory history;
        for (HistoricActivityInstance activity : activityInstances) {
            if (activity.getEndTime() == null) {
                continue;
            }

            ActApprove approve = getHistoricActApprove(activity.getTaskId());
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
            history.setApproveUser(getUserName(approve.getUserId()));
            history.setProcess(approve.getProcess());
            history.setStartTime(LocalDateTime.fromDateFields(activity.getStartTime()));
            history.setEndTime(LocalDateTime.fromDateFields(activity.getEndTime()));
            histories.add(history);
        }

        return histories;
    }

    private HistoricProcessInstance getHistoricProcessInstance(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
    }

    private String getUserName(String account) {
        if (account == null) {
            return null;
        }
        return userRepo.getUserNameByAccount(account);
    }

    private ActApprove getHistoricActApprove(String taskId) {
        HistoricVariableInstance variable = historyService.createHistoricVariableInstanceQuery().taskId(taskId)
                .variableName(ActConstants.TASK_APPROVE).singleResult();
        if (variable == null) {
            return null;
        }
        return (ActApprove) variable.getValue();
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

}

