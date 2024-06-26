//package com.example.backend.controller;
//
//import org.flowable.bpmn.model.BpmnModel;
//import org.flowable.engine.*;
//import org.flowable.engine.runtime.Execution;
//import org.flowable.engine.runtime.ProcessInstance;
//import org.flowable.image.ProcessDiagramGenerator;
//
//
//import org.flowable.task.api.Task;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//@RestController
//@RequestMapping("/test")
//public class ProcessController {
//
//    @Autowired
//    private RuntimeService runtimeService;
//    @Autowired
//    private TaskService taskService;
//    @Autowired
//    private RepositoryService repositoryService;
//    @Autowired
//    private ProcessEngine processEngine;
//
//    /**
//     * 通过接收用户的一个请求传入用户的ID和金额以及描述信息来开启一个报销流程，并返回给用户这个流程的Id
//     * 添加报销
//     * @param userId    用户Id
//     * @param money    销金额
//     * @param descption 描述
//     */
//    @GetMapping("/add")
//    public String addExpense(String userId, Integer money, String descption) {
//        //启动流程
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("taskUser", userId);
//        map.put("money", money);
//        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
//        return "提交成功.流程Id为：" + processInstance.getId();
//    }
//
//    /**
//     * 查询流程列表，待办列表，通过代码获取出用户需要处理的流程
//     * 获取审批管理列表
//     */
//    @GetMapping("/list")
//    public Object list(String userId) {
//        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
//        for (Task task : tasks) {
//            System.out.println("===>"+task.toString());
//        }
//        return "task size: " +tasks.size() + " , 第一个：" + tasks.get(0).toString() ;
//    }
//
//    /**
//     * 批准，通过前端传入的任务ID来对此流程进行同意处理
//     * @param taskId 任务ID
//     */
//    @GetMapping("/apply")
//    public String apply(String taskId) {
//        Task task = taskService.createTaskQuery().processInstanceId(taskId).singleResult();
//        System.out.println("processInstance:"+taskId);
//        System.out.println("taskId:"+task.getId());
//        System.out.println(taskId.equals(task.getId()));
//        if (task == null) {
////            throw new RuntimeException("流程不存在");
//            System.out.println(task.toString()+"==>"+taskId);
//            return "找不到流程："+taskId;
//        }
//        //通过审核
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("outcome", "通过");
//        taskService.complete(task.getId(), map);
//        return "processed ok!";
//    }
//
//    /**
//     * 拒绝
//     */
//    @GetMapping("/reject")
//    public String reject(String taskId) {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("outcome", "驳回");
//        taskService.complete(taskId, map);
//        return "reject";
//    }
//
//    /**
//     * 生成当前流程图表
//     * 生成流程图
//     * @param processId 任务ID
//     */
//    @GetMapping("/processDiagram")
//    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
//        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
//               // processInstanceId(processId).singleResult();
//
//        //流程走完的不显示图
//        if (pi == null) {
//            return;
//        }
//        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
//        String InstanceId = task.getProcessInstanceId();
//        List<Execution> executions = runtimeService
//                .createExecutionQuery()
//                .processInstanceId(InstanceId)
//                .list();
//
//        //得到正在执行的Activity的Id
//        List<String> activityIds = new ArrayList<>();
//        List<String> flows = new ArrayList<>();
//        for (Execution exe : executions) {
//            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
//            activityIds.addAll(ids);
//        }
//
//        //图片输出要加上这个不会显示二进制数据，有些浏览器正常，有些浏览器是直接显示二进制数据,因此修改
//        httpServletResponse.setContentType("image/png".concat(";charset=UTF-8"));
//
//        //获取流程图
//        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
//        System.out.println("==============>"+pi.toString());
//        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
//        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
//        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0,true);
//        OutputStream out = null;
//        byte[] buf = new byte[1024];
//        int legth = 0;
//        try {
//            out = httpServletResponse.getOutputStream();
//            while ((legth = in.read(buf)) != -1) {
//                out.write(buf, 0, legth);
//            }
//        } finally {
//            if (in != null) {
//                in.close();
//            }
//            if (out != null) {
//                out.close();
//            }
//        }
//    }
//
//}