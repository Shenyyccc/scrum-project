package com.example.backend.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.Constants.Progress;
import com.example.backend.common.Result;
import com.example.backend.mapper.ProcessdefinitionMapper;
import com.example.backend.mapper.ProcessdefinitiontaskMapper;
import com.example.backend.mapper.RunningtaskMapper;
import com.example.backend.mapper.RunningworkflowMapper;
import com.example.backend.pojo.*;
import com.example.backend.pojo.DTO.PageParams;
import com.example.backend.pojo.DTO.RunningWorkflowDTO;
import com.example.backend.service.PaginationService;
import com.example.backend.utils.UUIDutils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import liquibase.pro.packaged.G;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RunningworkflowController {
    @Autowired
    RunningworkflowMapper workflowMapper;
    @Autowired
    ProcessdefinitionMapper definitionMapper;
    @Autowired
    ProcessdefinitiontaskMapper definitionTaskMapper;

    @Autowired
    RunningtaskMapper runningtaskMapper;

    @Autowired
    PaginationService paginationService;

    @GetMapping("/deploy")
    public Result deployDefinition(String id){
        DateTime date = DateUtil.date();
        String workflowId = UUIDutils.getUUID();

        //1.定义模板创建实例
        Processdefinition processdefinition = definitionMapper.selectById(id);
        Runningworkflow workflow=new Runningworkflow();
        BeanUtil.copyProperties(processdefinition,workflow);
       workflow.setId(workflowId);
       workflow.setCreatetime(date);
       workflow.setDefinitionid(id);

       //2.定义模板的任务创建实例
        QueryWrapper<Processdefinitiontask> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("definitionid",id);
        List<Processdefinitiontask> definitiontaskList = definitionTaskMapper.selectList(queryWrapper);
        for(Processdefinitiontask task:definitiontaskList){
            Runningtask runningtask=new Runningtask();
            BeanUtil.copyProperties(task,runningtask);
            runningtask.setId(UUIDutils.getUUID());
            runningtask.setCreatetime(date);
            runningtask.setRunningworkflowid(workflowId);
            runningtask.setProgress(Progress.unSelected);
            runningtask.setPretaskkey(task.getPretaskkey());
            runningtaskMapper.insert(runningtask);
        }

        return Result.success(workflowMapper.insert(workflow));
    }

    @GetMapping("/getDepartTask")
    public Result getDepartTask(@RequestParam("name")String name, @RequestParam("scrib")String scrib, @RequestParam("time")String time,
                                @RequestParam("departmentid")String departmentid,@RequestParam("pageSize")Integer pageSize,
                                @RequestParam("pageNum")Integer pageNum,@RequestParam("companyId")String companyId){
        //1.查询某部门的未领取任务
        QueryWrapper<Runningtask> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("companyid",companyId);
        if(StrUtil.isNotEmpty(departmentid)){
            queryWrapper.eq("departmentid",departmentid);
        }
        queryWrapper.and(wapper->wapper.isNull("userid").or().eq("userid",""));

        queryWrapper.eq("progress",0);
        if(!StrUtil.isEmpty(name)){
            queryWrapper.like("taskname",name);
        }
        if(!StrUtil.isEmpty(scrib)){
            queryWrapper.like("taskscrib",scrib);
        }
        if(time!=null&&time!=""){
            queryWrapper.eq("expiretime",time);
        }
//        PageHelper.startPage(pageNum,pageSize);
        List<Runningtask>list = runningtaskMapper.selectList(queryWrapper);
        Iterator<Runningtask> iterator = list.iterator();
        List<Runningtask> toRemove=new ArrayList<>();
        while (iterator.hasNext()) {
            Runningtask task = iterator.next();
            List<Integer> pretaskkey = task.getPretaskkey();
            Boolean flag=true;
            for(Integer key:pretaskkey){
                QueryWrapper<Runningtask> wrapper=new QueryWrapper<>();
                wrapper.eq("runningworkflowid",task.getRunningworkflowid());
                wrapper.eq("taskkey",key);
                Runningtask runningtask = runningtaskMapper.selectOne(wrapper);
                //判断前置任务是否完成
                if(runningtask.getProgress()!=Progress.Finished){
                    flag=false;
                }
            }
            if(!flag){
                toRemove.add(task);
            }
        }

        list.removeAll(toRemove);
        PageParams page = paginationService.getPage(list, pageNum, pageSize);
        System.out.println(page);

//        PageInfo<Runningtask> of = PageInfo.of(list);
        return Result.success(page);
    }

    @GetMapping("/pickUp")
    public Result pickUpTask(String taskid,String userid){
        DateTime startTime=DateUtil.date();
        Runningtask task=runningtaskMapper.selectById(taskid);
        if(task==null){
            return Result.error("Cannot find this task");
        }
        DateTime endTime=DateUtil.offsetDay(startTime,task.getExpiretime());
        task.setUserid(userid);
        task.setStarttime(startTime);
        task.setEndtime(endTime);
        task.setProgress(Progress.inProgress);
        return Result.success(runningtaskMapper.updateById(task));
    }


    @GetMapping("/getRunningTask")
    public Result getRunningTask(String userid){
        QueryWrapper<Runningtask> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userid",userid);
        queryWrapper.eq("progress",Progress.inProgress);
        queryWrapper.orderByAsc("endtime");
        List<Runningtask> list = runningtaskMapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @GetMapping("/getRunningTaskByWorkflowId")
    public Result getRunningTaskByWorkflowId(String workflowId){
        QueryWrapper<Runningtask> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("runningworkflowid",workflowId);
        List<Runningtask> list = runningtaskMapper.selectList(queryWrapper);
        return Result.success(list);
    }

    @GetMapping("/finishTask")
    public Result finishTask(String id){
        Runningtask task = runningtaskMapper.selectById(id);
        if(task==null){
            return Result.error("Dont have this Task!");
        }
        task.setProgress(Progress.Finished);
        return Result.success(runningtaskMapper.updateById(task));
    }

    @GetMapping("/getRunningWorkflow")
    public Result getRunningWorkflow(String search,Integer pageSize, Integer pageNum,String companyid){
        List<RunningWorkflowDTO> data=new ArrayList<>();
        QueryWrapper<Runningworkflow> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("companyid",companyid);
        queryWrapper.like("name",search);
        PageHelper.startPage(pageNum,pageSize);
        List<Runningworkflow> workflows = workflowMapper.selectList(queryWrapper);
        PageInfo<Runningworkflow> of=PageInfo.of(workflows);

        for(Runningworkflow workflow:workflows){
            QueryWrapper<Runningtask> wrapper=new QueryWrapper<>();
            wrapper.eq("runningworkflowid",workflow.getId());
            List<Runningtask> runningtasks = runningtaskMapper.selectList(wrapper);
            Boolean flag=true;
            for(Runningtask task:runningtasks){
                if(task.getProgress()!=Progress.Finished){
                    flag=false;
                    break;
                }
            }
            RunningWorkflowDTO dto=new RunningWorkflowDTO();
            BeanUtil.copyProperties(workflow,dto);
            if(flag){
                dto.setProgress("Finished");
            }else {
                dto.setProgress("in Progress");
            }
            data.add(dto);
        }
        Map<String,Object> map=new HashMap<>();
        map.put("data",data);
        map.put("total",of.getTotal());

        return Result.success(map);
    }



}
