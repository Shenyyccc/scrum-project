package com.example.backend.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.Exception.LoginException;
import com.example.backend.mapper.CompanyMapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.pojo.Company;
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

    @Autowired
    CompanyMapper companyMapper;


    public UserDTO login(User user) throws Exception {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User one;
        one = userMapper.selectOne(queryWrapper); //从数据库查询用户信息
       if(one==null){
           throw new LoginException("The username or password is incorrect");
       }

        if(!user.getPassword().equals(one.getPassword())){
            throw new LoginException("The username or password is incorrect");
        }

        UserDTO userDTO = new UserDTO();
        if(one!=null){

            String token = TokenUtils.getToken(one.getId(), one.getPassword());
            userDTO.setToken(token);
            userDTO.setIdentity(one.getIdentity());
            userDTO.setName(one.getName());
            userDTO.setAvatar(one.getAvatar());
            userDTO.setUserId(one.getId());
            userDTO.setUsername(one.getUsername());
            userDTO.setCompanyid(one.getCompanyid());
            userDTO.setCompany(companyMapper.selectById(one.getCompanyid()).getName());
            userDTO.setDepartmentid(one.getDepartmentid());
            if(one.getCompanyid()!=null){
                Company company = companyMapper.selectById(one.getCompanyid());
                userDTO.setCompany(company.getName());
            }

            return userDTO;
        }
        return userDTO;

    }


}
