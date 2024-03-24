package com.example.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.pojo.DTO.UserDTO;
import com.example.backend.pojo.User;
import com.example.backend.service.UserService;
import com.example.backend.utils.UUIDutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @GetMapping("/incr")
    public String inctr(){
        Long hahaha = redisTemplate.opsForValue().increment("hahaha");
        return "增加后的值"+hahaha;
    }



    @GetMapping("/hello")
    public Boolean hello(){

        /*
        * 验证
        * */
        return true;
    }

    @PostMapping("/login")
    public UserDTO Login(@RequestParam("username")String username,@RequestParam("password")String password){
        System.out.println("==================================>"+username);
        System.out.println("==================================>"+password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        UserDTO userDTO;
        try {
            userDTO = userService.login(user);
        } catch (Exception e) {
            return null;
        }

        return userDTO;
    }

    @GetMapping("/checkUsername")
    public String CheckRegister(@RequestParam("username")String username){
        System.out.println("checkList=================================>"+username);
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

    @PostMapping("/register")
    public Integer Register(User user){
        user.setId(UUIDutils.getUUID());
        return this.userMapper.insert(user);
    }

}
