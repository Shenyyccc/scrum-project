package com.example.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.mapper.WorkMapper;
import com.example.backend.pojo.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/WorkList")
public class WorkListController {

    @Autowired
    WorkMapper workMapper;

    @GetMapping("/getprocess1")
    public List<Work> getProcess1(){
        QueryWrapper<Work> wrapper=new QueryWrapper<>();
        wrapper.eq("workprocess",1);
        List<Work> works = workMapper.selectList(wrapper);
        for (Work work : works) {
            System.out.println(work);
        }
        return works;
    }

    @GetMapping("/getprocess2")
    public List<Work> getProcess2(){
        QueryWrapper<Work> wrapper=new QueryWrapper<>();
        wrapper.eq("workprocess",2);
        List<Work> works = workMapper.selectList(wrapper);
        for (Work work : works) {
            System.out.println(work);
        }
        return works;
    }


    @GetMapping("/getprocess3")
    public List<Work> getProcess3(){
        QueryWrapper<Work> wrapper=new QueryWrapper<>();
        wrapper.eq("workprocess",3);
        List<Work> works = workMapper.selectList(wrapper);
        for (Work work : works) {
            System.out.println(work);
        }
        return works;
    }

    @GetMapping("/Change")
    public void ChangeWorkList(@RequestParam("Id") int Id,@RequestParam("ProcessId") int processId){
        System.out.println("=============================================================ID:"+Id);
        System.out.println("=============================================================ProcessID:"+processId);
        System.out.println("=============================================================");
        Work work = workMapper.selectById(Id);
        System.out.println("==========================>"+work.getId()+"  "+work.getWorkname()+"  "+work.getWorkprocess()+"  ");
        UpdateWrapper<Work> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",Id);
        work.setWorkprocess(processId);
        workMapper.update(work,wrapper);



    }

}
