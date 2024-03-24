package com.example.backend.controller;


import com.example.backend.mapper.DepartmentMapper;
import com.example.backend.pojo.Department;
import com.example.backend.utils.UUIDutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentMapper departmentMapper;

    @GetMapping("/create")
    public Boolean CreateDepart(@RequestParam("departmentName") String departmentName){
        Department department = new Department();
        department.setId(UUIDutils.getUUID());
        department.setName(departmentName);
        departmentMapper.insert(department);
        return true;
    }


    @GetMapping("/get")
    public List<Department> getDepartment(){
        System.out.println("============>good");
        System.out.println("傻逼wok");
        return departmentMapper.selectList(null);
    }



}
