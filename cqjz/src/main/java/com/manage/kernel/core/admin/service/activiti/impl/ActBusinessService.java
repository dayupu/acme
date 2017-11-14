package com.manage.kernel.core.admin.service.activiti.impl;

import com.manage.base.act.*;
import com.manage.base.act.enums.ActVariable;
import com.manage.base.act.vars.ActApproveObj;
import com.manage.kernel.core.admin.service.activiti.IActBusinessService;
import com.manage.kernel.jpa.entity.ActApproveTask;
import com.manage.kernel.jpa.entity.AdOrganization;
import com.manage.kernel.jpa.entity.AdUser;
import com.manage.kernel.jpa.repository.ActApproveTaskRepo;
import com.manage.kernel.jpa.repository.AdUserRepo;
import com.manage.kernel.jpa.repository.NewsRepo;
import com.manage.kernel.spring.comm.SessionHelper;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.task.TaskInfo;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ProcessUser getProcessUser(String actUserId) {
        ProcessUser user = new ProcessUser();
        if (actUserId == null) {
            return user;
        }
        AdUser adUser = userRepo.findUserByAccount(actUserId);
        if (adUser == null) {
            return user;
        }
        user.setId(adUser.getId());
        user.setActId(adUser.getAccount());
        user.setName(adUser.getName());
        if (adUser.getOrganCode() != null) {
            AdOrganization organ = adUser.getOrgan();
            if (organ != null) {
                user.setOrganCode(organ.getCode());
                user.setOrganName(organ.getName());
            }
        }
        return user;
    }

    @Override
    public void saveApproveTask(TaskInfo taskInfo, String businessId, ActApproveObj approve) {

        ProcessVariable variable = getProcessVaribale(taskInfo.getProcessInstanceId());
        ActApproveTask approveTask = new ActApproveTask();
        approveTask.setApproveComment(approve.getComment());
        approveTask.setApproveUser(SessionHelper.user().getAccount());
        approveTask.setApproveResult(approve.getProcess());
        approveTask.setApproveTime(LocalDateTime.now());
        approveTask.setTaskId(taskInfo.getId());
        approveTask.setTaskName(taskInfo.getName());
        approveTask.setProcInstId(taskInfo.getProcessInstanceId());
        approveTask.setBusinessKey(businessId);
        approveTask.setSubject(variable.getSubject());
        approveTask.setApplyUser(variable.getApplyUser());
        actApproveTaskRepo.save(approveTask);
    }

    @Override
    public ProcessVariable getProcessVaribale(String processId) {
        ProcessVariable processVariable = new ProcessVariable();

        HistoricVariableInstanceQuery historicVariableQuery = historyService.createHistoricVariableInstanceQuery();
        List<HistoricVariableInstance> variables = historicVariableQuery.processInstanceId(processId).list();
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
            }
        }
        return processVariable;
    }

}
