package com.manage.kernel.core.admin.service.activiti.cmd;

import com.manage.base.act.ActBusiness;
import com.manage.base.constant.ActConstants;
import com.manage.base.supplier.page.PageQuery;
import com.manage.base.supplier.page.PageResult;
import com.manage.base.utils.CoreUtil;
import com.manage.base.utils.StringUtil;
import com.manage.kernel.core.admin.apply.dto.FlowDto;
import com.manage.kernel.core.admin.service.activiti.query.TaskQuery;
import com.manage.kernel.jpa.news.entity.User;
import com.manage.kernel.jpa.news.repository.UserRepo;
import com.manage.kernel.spring.comm.SpringUtils;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.*;
import org.activiti.engine.task.Task;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bert on 2017/10/6.
 */
public class TaskQueryCmd implements Command<PageResult> {


    private UserRepo userRepo;
    private TaskQuery query;
    private PageQuery page;

    public TaskQueryCmd(TaskQuery query, PageQuery page) {
        this.query = query;
        this.page = page;
        this.userRepo = SpringUtils.getBean(UserRepo.class);
    }

    @Override
    public PageResult execute(CommandContext commandContext) {

        PageResult result = new PageResult();
        TaskEntityManager taskManager = commandContext.getTaskEntityManager();
        HistoricProcessInstanceEntityManager ProcessHistoryManager = commandContext.getHistoricProcessInstanceEntityManager();
        TaskQueryImpl taskQuery = new TaskQueryImpl();
        taskQuery.taskCandidateGroup(query.getGroupId());
        taskQuery.setFirstResult(page.offset());
        taskQuery.setMaxResults(page.limit());
        if (StringUtil.isNotBlank(query.getSubject())) {
            taskQuery.processVariableValueEquals(ActConstants.ACT_VAR_SUBJECT, query.getSubject());
        }
        long count = taskManager.findTaskCountByQueryCriteria(taskQuery);
        List<FlowDto> flows = new ArrayList<>();
        result.setTotal(count);
        result.setRows(flows);
        if (count == 0) {
            return result;
        }

        FlowDto flow;
        TaskEntity entity;
        ExecutionEntity execution;
        HistoricProcessInstanceEntity processHistoryEntity;
        List<Task> tasks = taskManager.findTasksByQueryCriteria(taskQuery);
        for (Task task : tasks) {
            flow = new FlowDto();
            entity = (TaskEntity) task;
            execution = entity.getProcessInstance();
            processHistoryEntity = ProcessHistoryManager.findHistoricProcessInstance(entity.getProcessInstanceId());
            User user = userRepo.findUserByAccount(processHistoryEntity.getStartUserId());
            ActBusiness business = ActBusiness.fromBusinessKey(execution.getBusinessKey());
            Object subject = entity.getVariable(ActConstants.ACT_VAR_SUBJECT);
            if (subject != null) {
                flow.setSubject(String.valueOf(subject));
            }
            flow.setTaskName(entity.getName());
            flow.setProcessId(entity.getProcessInstanceId());
            flow.setBusinessNumber(business.getNumber());
            flow.setBusinessSource(business.getSource());
            flow.setApplyUser(user.getName());
            flow.setApplyAt(LocalDateTime.fromDateFields(processHistoryEntity.getStartTime()));
            flow.setTaskCreatedAt(LocalDateTime.fromDateFields(entity.getCreateTime()));
            flows.add(flow);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(CoreUtil.nextRandomID());
    }
}
