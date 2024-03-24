package com.example.backend.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.pojo.DTO.UserDTO;
import com.example.backend.pojo.User;
import com.example.backend.utils.TokenUtils;
import com.google.protobuf.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;


    public UserDTO login(User user) throws Exception {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User one;
        try{
            one = userMapper.selectOne(queryWrapper); //从数据库查询用户信息
        }catch (Exception e){
            throw new Exception("不存在该用户名");
        }
        if(!user.getPassword().equals(one.getPassword())){
            throw new Exception("密码错误");
        }

        UserDTO userDTO = new UserDTO();
        if(one!=null){
            String token = TokenUtils.getToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);
            return userDTO;
        }
        return userDTO;

    }


}
