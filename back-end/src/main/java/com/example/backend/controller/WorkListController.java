package com.example.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.common.Result;
import com.example.backend.mapper.WorkMapper;
import com.example.backend.pojo.DTO.PageParams;
import com.example.backend.pojo.Work;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/WorkList")
public class WorkListController {

    @Autowired
    WorkMapper workMapper;


    @GetMapping("/pickup")
    public Result getWorks(@RequestParam("name")String name,@RequestParam("scrib")String scrib,@RequestParam("prio")String prio,
                           @RequestParam("time")String time,@RequestParam("pageSize")Integer pageSize,@RequestParam("pageNum")Integer pageNum){
        PageHelper.startPage(pageNum,pageSize);
        //只有紧跟在PageHelper.startPage（）方法后的第一个Mybatis的查询（Select）方法会被分页
        List<Work> works = workMapper.selectList(null);
        PageInfo<Work> of = PageInfo.of(works);
        return Result.success(of);
    }

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
