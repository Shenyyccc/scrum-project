package com.example.backend.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.common.Result;
import com.example.backend.mapper.ProcessdefinitionMapper;
import com.example.backend.mapper.ProcessdefinitiontaskMapper;
import com.example.backend.pojo.DTO.FormDefinitionDTO;
import com.example.backend.pojo.DTO.FormTaskDTO;
import com.example.backend.pojo.Processdefinition;
import com.example.backend.pojo.Processdefinitiontask;
import com.example.backend.pojo.Tasktemplate;
import com.example.backend.utils.UUIDutils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/taskflowDefinition")
public class ProcessdefinitionController {

    @Autowired
    ProcessdefinitionMapper definitionMapper;

    @Autowired
    ProcessdefinitiontaskMapper definitiontaskMapper;

    @PostMapping("/create")
    public Result createDefinition(@RequestBody FormDefinitionDTO formDefinitionDTO){
        DateTime date = DateUtil.date();
        //1.创建流程设实例
        Processdefinition definition=new Processdefinition();
        String definitionId=UUIDutils.getUUID();
        definition.setId(definitionId);
        definition.setName(formDefinitionDTO.getProcessname());
        definition.setCreatetime(date);
        definition.setCompanyid(formDefinitionDTO.getCompanyid());
//        definitionMapper.insert(definition);


        //2.创建模板任务实例
        for(FormTaskDTO formTaskDTO:formDefinitionDTO.getWorkflowData()){
            Processdefinitiontask processdefinitiontask = new Processdefinitiontask();
            BeanUtil.copyProperties(formTaskDTO,processdefinitiontask);
            processdefinitiontask.setId(UUIDutils.getUUID());

            processdefinitiontask.setTaskkey(formTaskDTO.getKey());
            processdefinitiontask.setDefinitionid(definitionId);
            processdefinitiontask.setCreatetime(date);
            processdefinitiontask.setPretaskkey(formTaskDTO.getPreTaskKey());

            definitiontaskMapper.insert(processdefinitiontask);
        }
        return Result.success(definitionMapper.insert(definition));
    }

    @GetMapping("/getWorkflowTemplate")
    public Result getWorkflowTemplate( String search,Integer pageSize, Integer pageNum,String companyid){
        QueryWrapper<Processdefinition> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("companyid",companyid);
        queryWrapper.like("name",search);
        PageHelper.startPage(pageNum,pageSize);
        List<Processdefinition> list = definitionMapper.selectList(queryWrapper);
        PageInfo<Processdefinition> of=PageInfo.of(list);
        return Result.success(of);
    }

    @GetMapping("/deleteWorkflowTemplate")
    public Result deleteWorkflowTemplate(String id){
        return Result.success(definitionMapper.deleteById(id));
    }

}
