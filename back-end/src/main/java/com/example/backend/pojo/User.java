package com.example.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//可以通过配置文件注入值
//@Component
//@ConfigurationProperties(prefix = "user1")
public class User implements Serializable{
    private static final long serialVersionUID = 3709225449689040689L;
    private Integer id;
    private String username;
    private String password;

}
