package com.example.backend.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.backend.common.Result;
import com.example.backend.mapper.UserMapper;
import com.example.backend.pojo.User;
import com.example.backend.utils.SMSutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SmsController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/sendSms")
    public Result sendSms(String phone){
        String sms = SMSutil.generateSMS(phone);
        if(StrUtil.isNotEmpty(phone)){
            /*查找这个机主的手机号*/
            QueryWrapper<User> wrapper=new QueryWrapper<>();
            wrapper.eq("phone",phone);
            User one = userMapper.selectOne(wrapper);
            Map<String,String>map=new HashMap<>();
            map.put("code",sms);
            map.put("userId",one.getId());
            return Result.success(map);
        }
        return Result.error("Can't get code");
    }

    @PostMapping("/modifyPass")
    public Result modifyPass(@RequestBody Map<String,String> map){
        System.out.println(map);
        User user= userMapper.selectById(map.get("userId"));
        user.setPassword(map.get("newPass"));
        return Result.success(userMapper.updateById(user));
    }
}
