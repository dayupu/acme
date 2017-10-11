package com.manage.kernel.core.admin.service.activiti.impl;

import com.manage.base.act.ActApprove;
import com.manage.base.act.ActBusiness;
import com.manage.base.act.ActSource;
import com.manage.base.act.ProcessVariable;
import com.manage.base.constant.ActConstants;
import com.manage.base.database.enums.NewsStatus;
import com.manage.base.database.enums.ActProcess;
import com.manage.base.exception.ActNotSupportException;
import com.manage.base.exception.ActTaskNotFoundException;
import com.manage.base.exception.NewsNotFoundException;
import com.manage.kernel.core.admin.service.activiti.IActBusinessService;
import com.manage.kernel.jpa.entity.ActApproveTask;
import com.manage.kernel.jpa.entity.News;
import com.manage.kernel.jpa.repository.ActApproveTaskRepo;
import com.manage.kernel.jpa.repository.NewsRepo;
import com.manage.kernel.spring.comm.SessionHelper;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bert on 2017/10/3.
 */
@Service
public class ActBusinessService implements IActBusinessService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ActApproveTaskRepo actApproveTaskRepo;

    @Override
    @Transactional
    public void changeStatus(ActBusiness actBusiness, ActProcess process, boolean processEnd) {
        if (actBusiness.getSource() != ActSource.NEWS) {
            throw new ActNotSupportException();
        }
        News news = newsRepo.findOne(actBusiness.getId());
        if (news == null) {
            throw new NewsNotFoundException();
        }

        NewsStatus status = null;
        if (process == ActProcess.CANCEL) {
            status = NewsStatus.DRAFT;
        } else if (processEnd) {
            status = NewsStatus.PASS;
        } else {
            switch (process) {
            case APPLY:
                status = NewsStatus.SUBMIT;
                break;
            case AGREE:
                status = NewsStatus.APPROVE;
                break;
            case REJECT:
                status = NewsStatus.REJECT;
                break;
            }
        }
        news.setStatus(status);
        newsRepo.save(news);
    }

    @Override
    @Transactional
    public void submit(ActBusiness actBusiness) {
        if (actBusiness.getSource() != ActSource.NEWS) {
            throw new ActNotSupportException();
        }

        News news = newsRepo.findOne(actBusiness.getId());
        if (news == null) {
            throw new NewsNotFoundException();
        }

        String applyUser = SessionHelper.user().getAccount();
        ProcessInstance process = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(actBusiness.businessKey()).singleResult();
        if (process == null) {
            // 指定流程发起人
            identityService.setAuthenticatedUserId(applyUser);
            Map<String, Object> variables = new HashMap<>();
            variables.put(ActConstants.PROCESS_APPLY_USER, applyUser);
            variables.put(ActConstants.PROCESS_SUBJECT, news.getTitle());
            variables.put(ActConstants.PROCESS_TYPE, actBusiness.getSource().genProcessType(news.getType()));
            process = runtimeService
                    .startProcessInstanceByKey(ActConstants.FLOW_NEWS, actBusiness.businessKey(), variables);
            Task task = getRunningTask(applyUser, process.getProcessInstanceId());
            if (task == null) {
                throw new ActTaskNotFoundException();
            }
            taskService.complete(task.getId());
        } else {
            Task task = getRunningTask(applyUser, process.getProcessInstanceId());
            if (task == null) {
                throw new ActTaskNotFoundException();
            }
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put(ActConstants.TEMP_ACTION, ActProcess.APPLY.action());
            variables.put(ActConstants.PROCESS_SUBJECT, news.getTitle());
            variables.put(ActConstants.PROCESS_TYPE, actBusiness.getSource().genProcessType(news.getType()));

            ActApprove approve = new ActApprove();
            approve.setUserId(applyUser);
            approve.setProcess(ActProcess.APPLY);
            approve.setComment("重新申请");
            taskService.setVariableLocal(task.getId(), ActConstants.TASK_APPROVE, approve);
            taskService.addComment(task.getId(), task.getProcessInstanceId(), approve.getComment());
            taskService.complete(task.getId(), variables);
            saveApproveTask(task, actBusiness, approve);
        }
    }

    @Override
    public void saveApproveTask(TaskInfo taskInfo, ActBusiness actBusiness, ActApprove approve) {

        ProcessVariable variable = getProcessVaribale(taskInfo.getProcessInstanceId());
        ActApproveTask approveTask = new ActApproveTask();
        approveTask.setApproveComment(approve.getComment());
        approveTask.setApproveUser(SessionHelper.user().getAccount());
        approveTask.setApproveResult(approve.getProcess());
        approveTask.setApproveTime(LocalDateTime.now());
        approveTask.setTaskId(taskInfo.getId());
        approveTask.setTaskName(taskInfo.getName());
        approveTask.setProcInstId(taskInfo.getProcessInstanceId());
        approveTask.setBusinessKey(actBusiness.businessKey());
        approveTask.setSubject(variable.getSubject());
        approveTask.setProcessType(variable.getProcessType());
        actApproveTaskRepo.save(approveTask);
    }

    @Override
    public ProcessVariable getProcessVaribale(String processId) {
        ProcessVariable processVariable = new ProcessVariable();
        List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processId).list();
        for (HistoricVariableInstance variable : variables) {
            if (ActConstants.PROCESS_SUBJECT.equals(variable.getVariableName())) {
                processVariable.setSubject((String) variable.getValue());
            } else if (ActConstants.PROCESS_TYPE.equals(variable.getVariableName())) {
                String processType = (String) variable.getValue();
                processVariable.setProcessType(processType);
                if (processType != null) {
                    processVariable.setProcessTypeName(ActSource.processTypeName(processType));
                }
            } else if (ActConstants.PROCESS_APPLY_USER.equals(variable.getVariableName())) {
                processVariable.setApplyUser((String) variable.getValue());
            }
        }
        return processVariable;
    }

    private Task getRunningTask(String applyUser, String processId) {
        return taskService.createTaskQuery().processInstanceId(processId).taskAssignee(applyUser).singleResult();
    }

}
