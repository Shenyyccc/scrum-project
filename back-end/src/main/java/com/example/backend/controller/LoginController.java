package com.example.backend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.Constants.WebEnum;
import com.example.backend.Exception.JwtAuthenticationException;
import com.example.backend.common.Result;
import com.example.backend.mapper.CompanyMapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.pojo.Company;
import com.example.backend.pojo.DTO.UserDTO;
import com.example.backend.pojo.User;
import com.example.backend.service.UserService;
import com.example.backend.utils.TokenUtils;
import com.example.backend.utils.UUIDutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @Autowired
    CompanyMapper companyMapper;

    @GetMapping("/incr")
    public String inctr(){
        Long hahaha = redisTemplate.opsForValue().increment("hahaha");
        return "增加后的值"+hahaha;
    }



    @GetMapping("/getUserInfo")
    public Result getUserInfo(String userId){
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("id",userId);
        User user = userMapper.selectOne(wrapper);
        if(user==null){
            return Result.error("不存在该用户");
        }
        return Result.success(user);
    }

    @PostMapping("/login")
    public Result Login(@RequestParam("username")String username,@RequestParam("password")String password){

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        UserDTO userDTO;
        try {
            userDTO = userService.login(user);
        } catch (Exception e) {
            return Result.error(e.getMessage(), WebEnum.CODE_402);
        }

        return Result.success(userDTO);
    }

    @GetMapping("/checkUsername")
    public String CheckRegister(@RequestParam("username")String username){
        System.out.println("=============>check username");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User one = userMapper.selectOne(wrapper);
        if(one!=null){
            System.out.println(one);
            return "true";
        }else {
            return "false";
        }
    }

    @GetMapping("/checkCompany")
    public String CheckCompany(@RequestParam("company")String company){
        System.out.println("=============>check company name");
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        wrapper.eq("name",company);
        Company one = companyMapper.selectOne(wrapper);
        if(one!=null){
            System.out.println(one);
            return "true";
        }else {
            return "false";
        }
    }

    @PostMapping("/register")
    public Integer Register(User user){

        String administerID=UUIDutils.getUUID();

        //administer
        user.setId(administerID);
        if(user.getIdentity()==1){
            String companyID=UUIDutils.getUUID();
            user.setCompanyid(companyID);
            //company
            Company company = new Company();
            company.setId(companyID);
            company.setName(user.getCompany());
            company.setAdministerid(administerID);
            this.companyMapper.insert(company);
        }

        return this.userMapper.insert(user);
    }

    @GetMapping("/getUserById")
    public Result getUserById(@RequestParam("userId")String userId){
        return Result.success(userMapper.selectById(userId));
    }

    @PostMapping("/updateUser")
    public Result updateUser(UserDTO userDTO){
        User user = userMapper.selectById(userDTO.getUserId());
        Company company = companyMapper.selectById(userDTO.getCompanyid());

        user.setAvatar(userDTO.getAvatar());
        user.setName(userDTO.getName());
        userMapper.updateById(user);

        company.setName(userDTO.getCompany());
        companyMapper.updateById(company);

        UserDTO userDTO1 = new UserDTO();
        BeanUtil.copyProperties(user,userDTO1);

        String token = TokenUtils.getToken(user.getId(), user.getPassword());
        userDTO1.setToken(token);
        userDTO1.setIdentity(user.getIdentity());
        userDTO1.setName(user.getName());
        userDTO1.setAvatar(user.getAvatar());
        userDTO1.setUserId(user.getId());
        userDTO1.setUsername(user.getUsername());
        userDTO1.setCompanyid(user.getCompanyid());
        userDTO1.setCompany(companyMapper.selectById(user.getCompanyid()).getName());
        if(user.getCompanyid()!=null){
            Company company1 = companyMapper.selectById(user.getCompanyid());
            userDTO1.setCompany(company1.getName());
        }

        return Result.success(userDTO1);
    }


}
