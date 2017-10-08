package com.manage.kernel.core.admin.service.business.impl;

import com.manage.base.act.ActApprove;
import com.manage.base.act.ActBusiness;
import com.manage.base.constant.ActConstants;
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
import com.manage.kernel.jpa.news.entity.User;
import com.manage.kernel.jpa.news.repository.UserRepo;
import com.manage.kernel.spring.comm.SessionHelper;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRepo userRepo;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IActBusinessService actProcessService;

    @Override
    @Transactional
    public PageResult rejectTaskList(PageQuery page, FlowDto query) {
        PageResult result = new PageResult();
        List<FlowDto> flows = new ArrayList<>();
        String account = SessionHelper.user().getAccount();
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(account).active();
        if (StringUtil.isNotBlank(query.getSubject())) {
            taskQuery.processVariableValueLike(ActConstants.ACT_VAR_SUBJECT, "%" + query.getSubject() + "%");
        }
        if (StringUtil.isNotNull(query.getQueryStartTime())) {
            taskQuery.taskCreatedAfter(query.getQueryStartTime().toDate());
        }
        if (StringUtil.isNotNull(query.getQueryEndTime())) {
            taskQuery.taskCreatedBefore(query.getQueryEndTime().toDate());
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
            flow.setProcessId(processId);
            flow.setSubject(getSubject(processId));
            HistoricProcessInstance processInstance = getHistoricProcessInstance(processId);
            ActBusiness business = ActBusiness.fromBusinessKey(processInstance.getBusinessKey());
            flow.setBusinessNumber(business.getNumber());
            flow.setBusinessSource(business.getSource());
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

        HistoricProcessInstanceQuery processInstanceQuery = historyService.createHistoricProcessInstanceQuery()
                .startedBy(account);
        if (query.getQueryStartTime() != null) {
            processInstanceQuery.startedAfter(query.getQueryStartTime().toDate());
        }
        if (query.getQueryEndTime() != null) {
            processInstanceQuery.startedBefore(query.getQueryEndTime().toDate());
        }

        long count = processInstanceQuery.count();
        result.setTotal(count);
        result.setRows(flows);
        if (count == 0) {
            return result;
        }

        FlowDto flow;
        List<HistoricProcessInstance> processes = processInstanceQuery.orderByProcessInstanceStartTime().desc()
                .listPage(page.offset(), page.limit());
        for (HistoricProcessInstance process : processes) {
            flow = new FlowDto();
            flow.setProcessId(process.getId());
            flow.setProcessStartTime(CoreUtil.fromDate(process.getStartTime()));
            flow.setProcessEndTime(CoreUtil.fromDate(process.getEndTime()));
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

                flow.setSubject(getSubject(process.getId()));
            }

            ActBusiness business = ActBusiness.fromBusinessKey(process.getBusinessKey());
            flow.setBusinessNumber(business.getNumber());
            flow.setBusinessSource(business.getSource());

            flows.add(flow);
        }
        return result;
    }

    private void clear() {
        // 删除发布信息
        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
        for (Deployment deployment : deployments) {
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }


    @Override
    @Transactional
    public ProcessDetail approveProcess(ApproveDto approveDto) {

        Task task = taskService.createTaskQuery().taskId(approveDto.getTaskId()).singleResult();
        if (task == null) {
            throw new ActTaskNotFoundException();
        }

        String account = SessionHelper.user().getAccount();
        ActApprove approve = new ActApprove();
        approve.setUserId(account);
        approve.setProcess(approveDto.getProcess());
        taskService.setVariableLocal(task.getId(), ActConstants.ACT_VAR_TAK_APPROVE, approve);
        taskService.addComment(task.getId(), task.getProcessInstanceId(), approveDto.getComment());
        Map<String, Object> map = new HashMap<>();
        map.put(ActConstants.ACT_VAR_ACTION, approveDto.getProcess().action());
        taskService.complete(task.getId(), map);

        HistoricProcessInstance processInstance = getHistoricProcessInstance(task.getProcessInstanceId());
        ActBusiness actBusiness = ActBusiness.fromBusinessKey(processInstance.getBusinessKey());
        boolean processEnd = false;
        if (processInstance.getEndTime() != null) {
            processEnd = true;
        }
        actProcessService.changeStatus(actBusiness, approveDto.getProcess(), processEnd);
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
        detail.setSubject(getSubject(processId));
        ActBusiness business = ActBusiness.fromBusinessKey(processInstance.getBusinessKey());
        detail.setBusinessNumber(business.getNumber());
        detail.setBusinessSource(business.getSource());

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
        User user = userRepo.findUserByAccount(account);

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
                .activityType(ActConstants.ACT_TYPE_USER_TASK).processInstanceId(processId)
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

            history.setTaskName(getTaskName(activity.getTaskId()));
            history.setComment(builder.toString());
            history.setApproveUser(getUserName(approve.getUserId()));
            history.setProcess(approve.getProcess());
            history.setStartTime(LocalDateTime.fromDateFields(activity.getStartTime()));
            history.setEndTime(LocalDateTime.fromDateFields(activity.getEndTime()));
            histories.add(history);
        }

        return histories;
    }


    @Override
    @Transactional
    public PageResult pendingTaskList(PageQuery page, FlowDto query) {

        PageResult result = new PageResult();
        List<FlowDto> flows = new ArrayList<>();
        String account = SessionHelper.user().getAccount();
        User user = userRepo.findUserByAccount(account);
        String groupId = user.getApproveRole().actGroupId();
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateGroup(groupId).active();
        if (StringUtil.isNotBlank(query.getSubject())) {
            taskQuery.processVariableValueLike(ActConstants.ACT_VAR_SUBJECT, "%" + query.getSubject() + "%");
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
            flow.setTaskId(task.getId());
            flow.setTaskName(task.getName());
            flow.setProcessId(processId);
            flow.setSubject(getSubject(processId));
            ProcessInstance processInstance = getProcessInstance(processId);
            if (processInstance != null) {
                ActBusiness business = ActBusiness.fromBusinessKey(processInstance.getBusinessKey());
                flow.setBusinessNumber(business.getNumber());
                flow.setBusinessSource(business.getSource());
            }
            HistoricProcessInstance historicProcessInstance = getHistoricProcessInstance(processId);
            if (historicProcessInstance != null) {
                flow.setApplyUser(getUserName(historicProcessInstance.getStartUserId()));
                flow.setApplyAt(LocalDateTime.fromDateFields(historicProcessInstance.getStartTime()));
            }
            flow.setTaskCreatedAt(LocalDateTime.fromDateFields(task.getCreateTime()));
            flows.add(flow);
        }
        return result;
    }

    private HistoricProcessInstance getHistoricProcessInstance(String processInstanceId) {
        return historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
    }

    private ProcessInstance getProcessInstance(String processInstanceId) {
        return runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
    }

    private String getUserName(String account) {
        if (account == null) {
            return null;
        }
        return userRepo.getUserNameByAccount(account);
    }


    private Object getProcessVariable(String processId, String variableName) {
        HistoricVariableInstance variable = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processId).variableName(variableName).singleResult();
        if (variable == null) {
            return null;
        }
        return variable.getValue();
    }

    private String getSubject(String processId) {
        return (String) getProcessVariable(processId, ActConstants.ACT_VAR_SUBJECT);
    }

    private ActApprove getHistoricActApprove(String taskId) {
        HistoricVariableInstance variable = historyService.createHistoricVariableInstanceQuery()
                .taskId(taskId).variableName(ActConstants.ACT_VAR_TAK_APPROVE).singleResult();
        if (variable == null) {
            return null;
        }
        return (ActApprove) variable.getValue();
    }

    private String getTaskName(String taskId) {


        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task != null) {
            return task.getName();
        }

        HistoricTaskInstance historicTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        if (historicTask != null) {
            return historicTask.getName();
        }

        return null;
    }
}

