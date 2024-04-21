package com.example.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//扫描Mapper文件夹
//sqlSessionFactoryRef针对多个sqlSessionFactory情况
@MapperScan(basePackages = "com.example.backend.mapper",sqlSessionFactoryRef = "sqlSessionFactory")
@SpringBootApplication
public class BackEndApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackEndApplication.class, args);
    }

}
