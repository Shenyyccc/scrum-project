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
public class User{
    private String id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private Integer identity=0;
    private String avatar;
    private String companyid;

    private String company;
    private String departmentid;

    //@Transient：表示某个属性不应当被持久化到数据库中。换句话说，标记了@Transient的字段在数据库的表结构中不会有对应的列。相当于VO


}
