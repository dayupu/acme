package com.manage.news.module.activiti.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
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

    public void monthtest(){

    }

    private void praction03(){
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("newsApprove");


    }

    private void practice02(){

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

        try{
            File file = new File("/home/bert/Pictures/flow.png");
            if(!file.exists()){
                file.createNewFile();
            }
            FileUtil.copyStream(is, new FileOutputStream(file));
        }catch (Exception e){

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
}
