package com.example.backend.Handler;

import com.example.backend.Exception.JwtAuthenticationException;
import com.example.backend.Exception.LoginException;
import com.example.backend.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseBody
    public Result handleAuthenticationException(JwtAuthenticationException e) {
        // 定义返回的错误信息
        Map<String, Object> data = new HashMap<>();
        data.put("error", "Unauthorized");
        data.put("message", e.getMessage());
        System.out.println(data);
        // 返回一个406未授权的响应
        return Result.error(e.getMessage());
    }


    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public Result handleLoginException(JwtAuthenticationException e) {

        // 返回一个401未授权的响应
        return Result.error(e.getMessage(),"401");
    }

//     可以继续定义其他异常的处理方法......
}
