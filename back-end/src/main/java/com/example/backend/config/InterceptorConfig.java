package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry ) {
        registry.addInterceptor(jwtInterceptor())   //配置jwt拦截器规则
                .addPathPatterns("/**")   //拦截所有请求路径
                .excludePathPatterns("/login","/checkUsername","/register","/files/**","/modifyPass","/sendSms","/checkCompany");
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

}
