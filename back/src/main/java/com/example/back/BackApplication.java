package com.example.back;

import com.example.back.mapper.UserMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
@CrossOrigin
public class BackApplication {


    private UserMapper userMapper;

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

}
