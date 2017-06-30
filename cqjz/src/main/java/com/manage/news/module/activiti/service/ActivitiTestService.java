package com.manage.news.module.activiti.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import static org.apache.logging.log4j.ThreadContext.clearAll;
import org.apache.logging.log4j.core.util.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivitiTestService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IdentityService identityService;

    public void monthtest() {
        praction03();
    }

    private void praction03() {

        String applyUser = "张三";

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("applyUser", applyUser);
        //        ProcessInstance pi = runtimeService.startProcessInstanceByKey("newsApprove", variables);
        //        System.out.println("流程实例ID：" + pi.getId());//流程实例ID
        //        System.out.println("流程实例ID：" + pi.getProcessInstanceId());//流程实例ID
        //        System.out.println("流程实例ID:" + pi.getProcessDefinitionId());//myMyHelloWorld

        //createUserAndGroup();

        List<Task> tasks =  taskService.createTaskQuery().taskAssignee(applyUser).list();
        for(Task task : tasks){
            System.out.println("任务ID:" + task.getId());
            System.out.println("任务的办理人:" + task.getAssignee());
            System.out.println("任务名称:" + task.getName());
            System.out.println("任务的创建时间:" + task.getCreateTime());
            System.out.println("任务ID:" + task.getId());
            System.out.println("流程实例ID:" + task.getProcessInstanceId());
            System.out.println("#####################################");

            System.out.println("执行任务");
            variables = new HashMap<String, Object>();
            variables.put("action","apply");
            variables.put("group01", "group1");
            taskService.complete(task.getId(), variables);
            System.out.println("完成任务：" + task.getId());
        }

        tasks =  taskService.createTaskQuery().taskCandidateGroup("group1").list();
        for(Task task : tasks){
            System.out.println("任务ID:" + task.getId());
            System.out.println("任务的办理人:" + task.getAssignee());
            System.out.println("任务名称:" + task.getName());
            System.out.println("任务的创建时间:" + task.getCreateTime());
            System.out.println("任务ID:" + task.getId());
            System.out.println("流程实例ID:" + task.getProcessInstanceId());
            System.out.println("#####################################");

            System.out.println("执行任务");
            variables = new HashMap<String, Object>();
            variables.put("action","approve");
            variables.put("group02", "group2");
            task.setAssignee("g1");
            taskService.complete(task.getId(), variables);
            System.out.println("完成任务：" + task.getId());
        }
    }



    private void practice02() {

        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey("sample01")
                .list();
        if (list != null && list.size() > 0) {
            for (ProcessDefinition pd : list) {
                System.out.println("流程定义的ID：" + pd.getId());
                System.out.println("流程定义的名称：" + pd.getName());
                System.out.println("流程定义的Key：" + pd.getKey());
                System.out.println("流程定义的部署ID：" + pd.getDeploymentId());
                System.out.println("流程定义的资源名称：" + pd.getResourceName());
                System.out.println("流程定义的版本：" + pd.getVersion());
                System.out.println("########################################################");
            }
        }

        String resourceName = "";
        List<String> names = repositoryService.getDeploymentResourceNames("15001");
        if (names != null && names.size() > 0) {
            for (String name : names) {
                if (name.indexOf(".png") >= 0) {//返回包含该字符串的第一个字母的索引位置
                    resourceName = name;
                }
            }
        }

        System.out.println(resourceName);

        InputStream is = repositoryService.getResourceAsStream("15001", resourceName);

        try {
            File file = new File("/home/bert/Pictures/flow.png");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileUtil.copyStream(is, new FileOutputStream(file));
        } catch (Exception e) {

        }

    }

    private void practice01() {
        // 启动流程实例
        //        ProcessInstance pi = runtimeService.startProcessInstanceByKey("sample01");
        //        System.out.println("流程实例ID：" + pi.getId());//流程实例ID：101
        //        System.out.println("流程实例ID：" + pi.getProcessInstanceId());//流程实例ID：101
        //        System.out.println("流程实例ID:" + pi.getProcessDefinitionId());//myMyHelloWorld:1:4

        List<Task> tasks = taskService.createTaskQuery().taskAssignee("employee").list();
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务的创建时间:" + task.getCreateTime());
                System.out.println("任务ID:" + task.getId());
                System.out.println("流程实例ID:" + task.getProcessInstanceId());
                System.out.println("#####################################");

                System.out.println("执行任务");
                taskService.complete(task.getId());
                System.out.println("完成任务：" + task.getId());
            }
        }

        tasks = taskService.createTaskQuery().taskAssignee("leader").list();
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务的创建时间:" + task.getCreateTime());
                System.out.println("任务ID:" + task.getId());
                System.out.println("流程实例ID:" + task.getProcessInstanceId());
                System.out.println("#####################################");
                System.out.println("执行任务");
                taskService.complete(task.getId());
                System.out.println("完成任务：" + task.getId());
            }
        }
    }

    private void clearAll() {

        List<ProcessDefinition> pds = repositoryService.createProcessDefinitionQuery().list();
        for (ProcessDefinition pd : pds) {
            System.out.println(pd.getDeploymentId());
            repositoryService.deleteDeployment(pd.getDeploymentId(), true);
        }

    }

    private void createUserAndGroup() {
        String group1 = "group1";
        String group2 = "group2";
        String group3 = "group3";
        Group g1 = identityService.newGroup(group1);
        g1.setType("支队领导");

        Group g2 = identityService.newGroup(group2);
        g2.setType("保密员");

        Group g3 = identityService.newGroup(group3);
        g3.setType("总队领导");

        identityService.saveGroup(g1);
        identityService.saveGroup(g2);
        identityService.saveGroup(g3);

        String user1 = "user1";
        String user2 = "user2";
        String user3 = "user3";
        User u1 = identityService.newUser(user1);
        u1.setFirstName("李");
        u1.setLastName("一");
        User u2 = identityService.newUser(user2);
        u2.setFirstName("李");
        u2.setLastName("二");

        User u3 = identityService.newUser(user3);
        u3.setFirstName("李");
        u3.setLastName("三");

        identityService.saveUser(u1);
        identityService.saveUser(u2);
        identityService.saveUser(u3);

        identityService.createMembership(user1, group1);
        identityService.createMembership(user2, group2);
        identityService.createMembership(user3, group3);
    }
}
