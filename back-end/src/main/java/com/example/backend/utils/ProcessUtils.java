//package com.example.backend.utils;
//
//
//import org.flowable.engine.*;
//import org.flowable.engine.history.HistoricActivityInstance;
//import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
//import org.flowable.engine.repository.Deployment;
//import org.flowable.engine.repository.ProcessDefinition;
//import org.flowable.engine.runtime.ProcessInstance;
//import org.flowable.task.api.Task;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class ProcessUtils {
//
//    public static void main(String[] args) {
//        //1. 流程引擎配置
//        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
//                .setJdbcUrl("jdbc:mysql://localhost:3306/graduation?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=utf-8&useUnicode=true")
//                .setJdbcUsername("root")
//                .setJdbcPassword("123456")
//                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
//                // 初始化基础表，不需要的可以改为 DB_SCHEMA_UPDATE_FALSE
//                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//        //2. 初始化流程引擎对象
//        ProcessEngine processEngine = cfg.buildProcessEngine();
//
//        //3.将流程定义xml文件部署至Flowable引擎
//        RepositoryService repositoryService = processEngine.getRepositoryService();
//        Deployment deployment = repositoryService.createDeployment()
//                .addClasspathResource("holiday-request.bpmn20.xml")
//                .deploy();
//
//        //4.查询流程定义
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//                .deploymentId(deployment.getId())
//                .singleResult();
//        System.out.println("Found process definition : " + processDefinition.getName());
//
//        //5.启动流程实例
//        RuntimeService runtimeService = processEngine.getRuntimeService();
//        Map<String, Object> variables = new HashMap<String, Object>();
//        variables.put("employee", "Shenyc");
//        variables.put("nrOfHolidays", 13);
//        variables.put("description", "description Syc");
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);
//
//        //6.获取经理的任务数量
//        TaskService taskService = processEngine.getTaskService();
//        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
//        System.out.println("You have " + tasks.size() + " tasks:");
//        for (int i=0; i<tasks.size(); i++) {
//            System.out.println((i+1) + ") " + tasks.get(i).getName());
//        }
//
//        //7.获取任务
//        int taskIndex = 1;
//        Task task = tasks.get(taskIndex - 1);
//        Map<String, Object> processVariables = taskService.getVariables(task.getId());
//        System.out.println(processVariables.get("employee") + " wants " +
//                processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");
//
//
//        //8.完成审批
//        boolean approved =true;
//        variables = new HashMap<String, Object>();
//        variables.put("approved", approved);
//        taskService.complete(task.getId(), variables);
//
//        //9.查看历史
//        HistoryService historyService = processEngine.getHistoryService();
//        List<HistoricActivityInstance> activities =
//                historyService.createHistoricActivityInstanceQuery()
//                        .processInstanceId(processInstance.getId())
//                        .finished()
//                        .orderByHistoricActivityInstanceEndTime().asc()
//                        .list();
//
//        for (HistoricActivityInstance activity : activities) {
//            System.out.println(activity.getActivityId() + " took "
//                    + activity.getDurationInMillis() + " milliseconds");
//        }
//
//    }
//}
