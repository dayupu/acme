package com.manage.kernel.core.admin.service.activiti.impl;

import com.manage.base.act.ActBusiness;
import com.manage.base.act.ActSource;
import com.manage.base.constant.ActConstants;
import com.manage.base.database.enums.NewsStatus;
import com.manage.base.enums.ActProcess;
import com.manage.base.exception.ActNotSupportException;
import com.manage.base.exception.ActTaskNotFoundException;
import com.manage.base.exception.NewsNotFoundException;
import com.manage.kernel.core.admin.service.activiti.IActBusinessService;
import com.manage.kernel.jpa.news.entity.News;
import com.manage.kernel.jpa.news.repository.NewsRepo;
import com.manage.kernel.spring.comm.SessionHelper;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put(ActConstants.APPLY_USER, applyUser);
            variables.put(ActConstants.ACT_VAR_SUBJECT, news.getTitle());
            process = runtimeService.startProcessInstanceByKey(ActConstants.ACT_NEWS_FLOW, actBusiness.businessKey(), variables);
            Task task = getTask(applyUser, process.getProcessInstanceId());
            if (task == null) {
                throw new ActTaskNotFoundException();
            }
            taskService.complete(task.getId());
        } else {
            Task task = getTask(applyUser, process.getProcessInstanceId());
            if (task == null) {
                throw new ActTaskNotFoundException();
            }
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put(ActConstants.ACT_VAR_ACTION, ActProcess.APPLY.action());
            variables.put(ActConstants.ACT_VAR_SUBJECT, news.getTitle());
            taskService.complete(task.getId(), variables);
        }

    }

    private Task getTask(String applyUser, String processId) {
        return taskService.createTaskQuery().processInstanceId(processId).taskAssignee(applyUser).singleResult();
    }


}
