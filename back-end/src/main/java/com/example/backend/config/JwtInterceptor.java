package com.example.backend.config;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.backend.Constants.WebEnum;
import com.example.backend.Exception.JwtAuthenticationException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.pojo.User;
import com.example.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

//JSON Web Token.
//Cookie储存在客户端，Session储存在服务器，Token是一种身份验证和授权的令牌机制，常见的是JSON Web Token
//Token被服务器生成并发送给客户端，客户端将Token存储，并在后续请求钟携带该Token
public class JwtInterceptor implements HandlerInterceptor {


    @Resource
    UserMapper userMapper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.从 http 请求头中取出 token
        String token = request.getHeader("token");   //前端header传过来的参数  请求头
        Map<String,Object> map=new HashMap<>();
        if (StrUtil.isBlank(token)){
            //2.如果请求头拿不到token，我再给你一次机会，从参数中拿数据   /api
            token=request.getParameter("token");   //url参数  ?token=xxxx
        }

        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
//        3.如果还拿不到token
        if (StringUtils.isBlank(token)) {
            map.put("message","找不到Token，请重新登录");
            String json = new ObjectMapper().writeValueAsString(map);
            System.out.println(json);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);

            throw new JwtAuthenticationException("找不到Token，请重新登录");
        }
        // 4.获取 token中的userId,根据userId查询数据库，检查用户是否存在
        String userId;
        User one;
        try {
            userId = JWT.decode(token).getAudience().get(0);    //JWT.decode(token) 解码
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.eq("id",userId);
            one = userMapper.selectOne(wrapper);
        } catch (JWTDecodeException j) {
            throw new JwtAuthenticationException("用户不存在，请重新登录");
        }
//
//        User user = userService.getById(userId);
//        if (one== null) {
//            throw new ServiceException(WebEnum.CODE_401);
//        }

        // 验证 token
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(one.getPassword())).build();
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
//            throw new ServiceException("Token过期，请重新登录");
        }
        return true;
    }

}
