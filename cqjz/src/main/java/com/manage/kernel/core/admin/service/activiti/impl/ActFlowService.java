package com.manage.kernel.core.admin.service.activiti.impl;

import com.manage.base.act.enums.ActVariable;
import com.manage.base.act.support.ActFlowInfo;
import com.manage.base.act.support.ActFlowSupport;
import com.manage.base.act.vars.ActApproveObj;
import com.manage.base.act.vars.ActParams;
import com.manage.base.constant.Constants;
import com.manage.base.database.enums.ActProcess;
import com.manage.base.database.enums.FlowSource;
import com.manage.base.database.enums.NewsStatus;
import com.manage.base.exception.ActNotSupportException;
import com.manage.base.exception.ActTaskNotFoundException;
import com.manage.base.exception.NewsNotFoundException;
import com.manage.kernel.core.admin.service.activiti.IActBusinessService;
import com.manage.kernel.core.admin.service.activiti.IActFlowService;
import com.manage.kernel.jpa.entity.FlowProcess;
import com.manage.kernel.jpa.entity.News;
import com.manage.kernel.jpa.repository.ActApproveTaskRepo;
import com.manage.kernel.jpa.repository.FlowProcessRepo;
import com.manage.kernel.jpa.repository.NewsRepo;
import com.manage.kernel.spring.comm.Messages;
import com.manage.kernel.spring.comm.SessionHelper;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bert on 17-11-9.
 */
@Service
public class ActFlowService implements IActFlowService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FlowProcessRepo flowProcessRepo;

    @Autowired
    private ActApproveTaskRepo approveTaskRepo;

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private IActBusinessService actBusinessService;

    @Override
    public void actFlowCommit(ActFlowSupport flowSupport) {

        ActFlowInfo flowInfo = flowSupport.actFlowInfo();
        if (flowInfo == null) {
            throw new ActNotSupportException();
        }

        String businessId = flowInfo.actBusinessId();
        FlowProcess flowProcess = flowProcessRepo.findByBusinessId(businessId);
        if (flowProcess == null) {
            flowProcess = new FlowProcess();
            flowProcess.setBusinessId(businessId);
            flowProcess.setCreatedAt(LocalDateTime.now());
            flowProcess.setCreatedUser(SessionHelper.user());
        } else {
            flowProcess.setUpdatedAt(LocalDateTime.now());
            flowProcess.setUpdatedUser(SessionHelper.user());
        }

        flowProcess.setBusinessId(businessId);
        flowProcess.setSubject(flowInfo.getSubject());
        flowProcess.setType(flowInfo.getType());
        flowProcess.setSubType(flowInfo.getSubType());
        flowProcess.setSource(flowInfo.getFlowSource());
        boolean isSupport = false;
        if (flowInfo.getFlowSource() == FlowSource.NEWS) {
            flowProcess.setNews((News) flowSupport);
            isSupport = true;
        }

        if (!isSupport) {
            throw new ActNotSupportException();
        }

        flowProcess = flowProcessRepo.save(flowProcess);
        handleActFlow(flowProcess);
    }

    @Override
    public void handleBusinessStatus(String businessId, ActProcess process, boolean isOver) {

        FlowProcess flowProcess = flowProcessRepo.findByBusinessId(businessId);
        if (flowProcess == null) {
            throw new ActNotSupportException();
        }

        if (flowProcess.getSource() == FlowSource.NEWS) {
            newsProcess(flowProcess.getNews(), process, isOver);
            return;
        }

        throw new ActNotSupportException();
    }

    private void newsProcess(News news, ActProcess process, boolean isOver) {
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
            if (isOver) {
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
            news.setPublishTime(LocalDateTime.now());
        }
        newsRepo.save(news);
    }

    private void handleActFlow(FlowProcess flowProcess) {
        String applyUserId = SessionHelper.user().actUserId();
        String businessId = flowProcess.getBusinessId();

        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
        ProcessInstance process = query.processInstanceBusinessKey(businessId).singleResult();
        if (process == null) {
            identityService.setAuthenticatedUserId(applyUserId);
            ActParams params = ActParams.flowStart(applyUserId, flowProcess.getSubject(), businessId);
            process = runtimeService.startProcessInstanceByKey(Constants.ACT_PROCESS_NEWS, businessId, params.build());
            Task task = getRunningTask(applyUserId, process.getProcessInstanceId());
            if (task == null) {
                throw new ActTaskNotFoundException();
            }
            taskService.complete(task.getId());
            return;
        }

        Task task = getRunningTask(applyUserId, process.getProcessInstanceId());
        if (task == null) {
            throw new ActTaskNotFoundException();
        }
        ActApproveObj approveObj = new ActApproveObj();
        approveObj.setUserId(applyUserId);
        approveObj.setProcess(ActProcess.APPLY);
        approveObj.setComment(Messages.get("text.act.comment.reApply"));
        taskService.setVariableLocal(task.getId(), ActVariable.TASK_APPROVE.varName(), approveObj);
        taskService.addComment(task.getId(), task.getProcessInstanceId(), approveObj.getComment());
        ActParams params = ActParams.flowProcess(ActProcess.APPLY, flowProcess.getSubject(), businessId);
        taskService.complete(task.getId(), params.build());
        actBusinessService.saveApproveTask(task, businessId, approveObj);
    }

    private Task getRunningTask(String applyUserId, String processId) {
        return taskService.createTaskQuery().processInstanceId(processId).taskAssignee(applyUserId).singleResult();
    }
}
