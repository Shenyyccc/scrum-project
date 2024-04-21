package com.example.backend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.common.Result;
import com.example.backend.mapper.DepartmentMapper;
import com.example.backend.mapper.TasktemplateMapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.pojo.DTO.DepartmentDTO;
import com.example.backend.pojo.DTO.DepartmentTaskDTO;
import com.example.backend.pojo.Department;
import com.example.backend.pojo.Tasktemplate;
import com.example.backend.pojo.User;
import com.example.backend.utils.UUIDutils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/templateTask")
public class TaskController {

    @Autowired
    TasktemplateMapper tasktemplateMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/create")
    public Result createTaskTemplate(Tasktemplate tasktemplate){
        tasktemplate.setId(UUIDutils.getUUID());
        System.out.println("=======================>"+tasktemplate.getDepartmentid());
        tasktemplateMapper.insert(tasktemplate);
        return Result.success();
    }

    @GetMapping("/get")
    public Result getTaskTemplate(String departmentid, Integer pageSize, Integer pageNum,String name,String content){
        QueryWrapper<Tasktemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("departmentid",departmentid);
        wrapper.like("taskname",name);
        wrapper.like("taskscrib",content);
        PageHelper.startPage(pageNum,pageSize);
        List<Tasktemplate> tasktemplates = tasktemplateMapper.selectList(wrapper);
        PageInfo<Tasktemplate> of = PageInfo.of(tasktemplates);
        return Result.success(of);
    }

    @GetMapping("/getTaskTemplate")
    public Result getTaskTemplates(@RequestParam("companyid") String companyid){
        QueryWrapper<Department> wrapper=new QueryWrapper<>();
        wrapper.eq("companyid",companyid);
        List<Department> list = departmentMapper.selectList(wrapper);
        List<DepartmentTaskDTO> dtoList = new ArrayList<>();
        for(Department depart:list){
            QueryWrapper<Tasktemplate> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("departmentid",depart.getId());
            List<Tasktemplate> tasktemplateList = tasktemplateMapper.selectList(queryWrapper);



            DepartmentTaskDTO taskDTO=new DepartmentTaskDTO();
            BeanUtil.copyProperties(depart,taskDTO);
            taskDTO.setWorkList(tasktemplateList);
            taskDTO.setManager(userMapper.selectById(depart.getManagerid()));

            dtoList.add(taskDTO);
        }

        return Result.success(dtoList);
    }

    @GetMapping("/getByid")
    public Result getTaskTemplateByid(String departmentid){
        QueryWrapper<Tasktemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("departmentid",departmentid);
        List<Tasktemplate> tasktemplates = tasktemplateMapper.selectList(wrapper);
        return Result.success(tasktemplates);
    }

    @GetMapping("/deleteTemplateById")
    public Result deleteTemplateById(@RequestParam("taskId") String taskId){
        return Result.success(tasktemplateMapper.deleteById(taskId));
    }

    @PostMapping("/updateTemplate")
    public Result updateTemplate(Tasktemplate tasktemplate){
        return Result.success(tasktemplateMapper.updateById(tasktemplate));
    }

}
