package com.example.backend.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.Constants.Identity;
import com.example.backend.common.Result;
import com.example.backend.mapper.DepartmentMapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.pojo.DTO.DepartmentDTO;
import com.example.backend.pojo.Department;
import com.example.backend.pojo.User;
import com.example.backend.utils.UUIDutils;
import liquibase.pro.packaged.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//Controller使用@CrossOrigin注解也可以实现跨域问题
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/create")
    public Boolean CreateDepart(@RequestParam("departmentName") String departmentName,@RequestParam("companyid") String companyId){
        Department department = new Department();
        department.setId(UUIDutils.getUUID());
        department.setName(departmentName);
        department.setCompanyid(companyId);
        department.setNum(0);
        departmentMapper.insert(department);
        return true;
    }

    @GetMapping("/edit")
    public Boolean editDepart(@RequestParam("departmentName") String departmentName,@RequestParam("departmentid") String departmentId){
        Department department=departmentMapper.selectById(departmentId);
        department.setName(departmentName);
        departmentMapper.updateById(department);
        return true;
    }

    @GetMapping("/delete")
    public Result deleteDepart(@RequestParam("departmentid") String departmentId){
        Integer count=departmentMapper.deleteById(departmentId);
        return Result.success(count);
    }


    @GetMapping("/get")
    public Result getDepartment(@RequestParam("companyId") String companyid){
        QueryWrapper<Department> wrapper=new QueryWrapper<>();
        wrapper.eq("companyid",companyid);
        List<Department> list=departmentMapper.selectList(wrapper);
        List<DepartmentDTO> listDTO=new ArrayList<>();
        for(Department department:list){
            QueryWrapper<User> userQueryWrapper=new QueryWrapper<>();
            userQueryWrapper.eq("departmentid",department.getId());

            DepartmentDTO dto=new DepartmentDTO();
            BeanUtil.copyProperties(department,dto);
            dto.setUserList(userMapper.selectList(userQueryWrapper));
            listDTO.add(dto);
        }

        return Result.success(listDTO);
    }

    @GetMapping("/getIndividualDepart")
    public Result getIndividualDepart(String id){
        return Result.success(departmentMapper.selectById(id));
    }

    @GetMapping("/getNoDepart")
    public Result getNoDepart(@RequestParam("companyId")String companyid,@RequestParam("research")String research){

        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.and(querrywapper->querrywapper.isNull("departmentid").or().eq("departmentid",""));
        wrapper.and(querrywapper->querrywapper.isNull("companyid").or().eq("companyid","").or().eq("companyid",companyid));
        wrapper.ne("identity",1);
        if(research!=null&&research!=""){
            wrapper.like("username",research);
        }

        List<User>userList=userMapper.selectList(wrapper);

        return Result.success(userList);
    }

    @GetMapping("/joinDepart")
    public Result joinDepart(@RequestParam("userId")String userId,@RequestParam("departId")String departId,@RequestParam("companyId")String companyId){
        User one=userMapper.selectById(userId);
        if(one==null){
            return Result.error("Don't have this User");
        }
        one.setDepartmentid(departId);
        one.setCompanyid(companyId);
        one.setIdentity(3);

        Department department=departmentMapper.selectById(departId);
        department.setNum(department.getNum()+1);
        departmentMapper.updateById(department);

        return Result.success(userMapper.updateById(one));
    }

    @GetMapping("/removeDepart")
    public Result removeDepart(@RequestParam("userId")String userId,@RequestParam("departId")String departId){
        //用来判断
        Boolean flag=false;

        User one=userMapper.selectById(userId);
        one.setDepartmentid("");
        if(one.getIdentity()==Identity.DEPARTMENT_MANAGER){
            flag=true;
        }
        one.setIdentity(0);

        Department department=departmentMapper.selectById(departId);
        department.setNum(department.getNum()-1);
        if(flag==true){
            department.setManagerid("");
        }
        departmentMapper.updateById(department);

        return Result.success(userMapper.updateById(one));
    }

    @GetMapping("/getDepartMember")
    public Result getDepartMember(@RequestParam("departId")String departId,@RequestParam("input")String input){
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("departmentid",departId);
        wrapper.eq("identity",3);
        wrapper.like("username",input);
        List<User> list = userMapper.selectList(wrapper);

        return Result.success(list);
    }

    @GetMapping("/setManager")
    public Result setManager(@RequestParam("departId")String departId,@RequestParam("userId")String userId){
        Department department = departmentMapper.selectById(departId);
        String managerid = department.getManagerid();
        if(managerid!=null&&!StrUtil.isEmpty(managerid)){
            User user = userMapper.selectById(managerid);
            user.setIdentity(Identity.DEPARTMENT_EMPLOYEE);
            userMapper.updateById(user);
        }
        User user = userMapper.selectById(userId);
        user.setIdentity(Identity.DEPARTMENT_MANAGER);
        userMapper.updateById(user);
        department.setManagerid(userId);
        return Result.success(departmentMapper.updateById(department));
    }



}
