package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("/incr")
    public String inctr(){
        Long hahaha = redisTemplate.opsForValue().increment("hahaha");
        return "增加后的值"+hahaha;
    }


    @GetMapping("/hello")
    public String hello(){
        return "Hello,Spring Boot 3";
    }

}
