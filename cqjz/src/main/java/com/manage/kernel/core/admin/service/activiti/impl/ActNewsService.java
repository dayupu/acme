package com.manage.kernel.core.admin.service.activiti.impl;

import com.manage.base.act.ActHelper;
import com.manage.base.act.ActSource;
import com.manage.base.constant.ActConstants;
import com.manage.base.exception.ActTaskNotFoundException;
import com.manage.base.utils.CoreUtil;
import com.manage.kernel.core.admin.apply.dto.NewsDto;
import com.manage.kernel.core.admin.service.activiti.INewsActivitiService;
import com.manage.kernel.jpa.news.entity.User;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bert on 2017/10/3.
 */
@Service
public class ActNewsService implements INewsActivitiService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private TaskService taskService;

    @Override
    @Transactional
    public void submit(User user, NewsDto news) {

        String applyUser = user.getAccount();
        // 指定流程发起人
        identityService.setAuthenticatedUserId(applyUser);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(ActConstants.APPLY_USER, applyUser);
        String businessKey = ActHelper.businessKeyGen(ActSource.NEWS, news.getId());
        ProcessInstance process = runtimeService.startProcessInstanceByKey(ActConstants.ACT_NEWS_FLOW, businessKey, variables);
        List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).taskAssignee(applyUser).list();
        if (CollectionUtils.isEmpty(taskList)) {
            throw new ActTaskNotFoundException();
        }

        Task task = taskList.get(0);
        taskService.complete(task.getId());
    }
}
