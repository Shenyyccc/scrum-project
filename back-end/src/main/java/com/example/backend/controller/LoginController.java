package com.example.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoginController {
    @RequestMapping("/user/login")
    @ResponseBody
    public String login(){
        return "OK";
    }
}
