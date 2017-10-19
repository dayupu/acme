package com.manage.kernel.core.admin.service.activiti.impl;

import com.manage.base.act.*;
import com.manage.base.act.enums.ActSource;
import com.manage.base.act.enums.ActVariable;
import com.manage.base.act.vars.ActApproveObj;
import com.manage.base.act.vars.ActParams;
import com.manage.base.constant.Constants;
import com.manage.base.database.enums.NewsStatus;
import com.manage.base.database.enums.ActProcess;
import com.manage.base.exception.ActNotSupportException;
import com.manage.base.exception.ActTaskNotFoundException;
import com.manage.base.exception.NewsNotFoundException;
import com.manage.kernel.core.admin.service.activiti.IActBusinessService;
import com.manage.kernel.jpa.entity.ActApproveTask;
import com.manage.kernel.jpa.entity.AdOrganization;
import com.manage.kernel.jpa.entity.AdUser;
import com.manage.kernel.jpa.entity.News;
import com.manage.kernel.jpa.repository.ActApproveTaskRepo;
import com.manage.kernel.jpa.repository.AdUserRepo;
import com.manage.kernel.jpa.repository.NewsRepo;
import com.manage.kernel.spring.comm.Messages;
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

import java.util.List;

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

    @Autowired
    private AdUserRepo userRepo;

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
        switch (process) {
        case APPLY:
            status = NewsStatus.SUBMIT;
            break;
        case AGREE:
            status = NewsStatus.APPROVE;
            if (processEnd) {
                status = NewsStatus.PASS;
            }
            break;
        case REJECT:
            status = NewsStatus.REJECT;
            break;
        case CANCEL:
            status = NewsStatus.CANCEL;
            break;
        }
        news.setStatus(status);
        if (status.isPass()) {
            news.setApprovedTime(LocalDateTime.now());
        }
        newsRepo.save(news);
    }

    @Override
    @Transactional
    public void submit(ActBusiness business) {
        if (business.getSource() != ActSource.NEWS) {
            throw new ActNotSupportException();
        }

        News news = newsRepo.findOne(business.getId());
        if (news == null) {
            throw new NewsNotFoundException();
        }

        String actUserId = SessionHelper.user().actUserId();
        ProcessInstance process = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(business.businessKey()).singleResult();
        if (process == null) {
            // 指定流程发起人
            identityService.setAuthenticatedUserId(actUserId);

            ActParams params = new ActParams();
            params.putFlowApplyUser(actUserId);
            params.putFlowSubject(news.getTitle());
            params.putFlowBusinessType(business.getSource().processTypeGen(news.getType()));
            process = runtimeService.startProcessInstanceByKey(Constants.ACT_PROCESS_NEWS, business.businessKey(),
                    params.buildMap());
            Task task = getRunningTask(actUserId, process.getProcessInstanceId());
            if (task == null) {
                throw new ActTaskNotFoundException();
            }

            taskService.complete(task.getId());
            return;
        }

        Task task = getRunningTask(actUserId, process.getProcessInstanceId());
        if (task == null) {
            throw new ActTaskNotFoundException();
        }
        ActApproveObj approveObj = new ActApproveObj();
        approveObj.setUserId(actUserId);
        approveObj.setProcess(ActProcess.APPLY);
        approveObj.setComment(Messages.get("text.act.comment.reApply"));
        taskService.setVariableLocal(task.getId(), ActVariable.TASK_APPROVE.varName(), approveObj);
        taskService.addComment(task.getId(), task.getProcessInstanceId(), approveObj.getComment());

        ActParams params = new ActParams();
        params.putFlowAction(ActProcess.APPLY.action());
        params.putFlowSubject(news.getTitle());
        params.putFlowBusinessType(business.getSource().processTypeGen(news.getType()));
        taskService.complete(task.getId(), params.buildMap());
        saveApproveTask(task, business, approveObj);

    }

    @Override
    public void saveApproveTask(TaskInfo taskInfo, ActBusiness actBusiness, ActApproveObj approve) {

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
        approveTask.setApplyUser(variable.getApplyUser());
        actApproveTaskRepo.save(approveTask);
    }

    @Override
    public ProcessUser getProcessUser(String actUserId) {
        ProcessUser user = new ProcessUser();
        if (actUserId == null) {
            return user;
        }
        AdUser adUser = userRepo.findUserByAccount(actUserId);
        if (adUser == null) {
            return user;
        }
        user.setUserId(adUser.getId());
        user.setActUserId(adUser.getAccount());
        user.setUserName(adUser.getName());
        if (adUser.getOrganId() != null) {
            AdOrganization organ = adUser.getOrgan();
            if (organ != null) {
                user.setUserOrganId(organ.getId());
                user.setUserOrganName(organ.getName());
            }
        }
        return user;
    }

    @Override
    public ProcessVariable getProcessVaribale(String processId) {
        ProcessVariable processVariable = new ProcessVariable();
        List<HistoricVariableInstance> variables = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processId).list();
        ActVariable actVar;
        for (HistoricVariableInstance variable : variables) {
            actVar = ActVariable.fromVarName(variable.getVariableName());
            if (actVar == null) {
                continue;
            }
            switch (actVar) {
            case FLOW_SUBJECT:
                processVariable.setSubject((String) variable.getValue());
                break;
            case FLOW_APPLY_USER:
                processVariable.setApplyUser((String) variable.getValue());
                break;
            case FLOW_BUSINESS_TYPE:
                String processType = (String) variable.getValue();
                processVariable.setProcessType(processType);
                if (processType != null) {
                    processVariable.setProcessTypeName(ActSource.processTypeName(processType));
                }
                break;
            }
        }
        return processVariable;
    }

    private Task getRunningTask(String applyUser, String processId) {
        return taskService.createTaskQuery().processInstanceId(processId).taskAssignee(applyUser).singleResult();
    }

}
