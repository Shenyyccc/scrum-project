package com.example.back.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.back.entity.User;
import com.example.back.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper,User> {
    public boolean saveUser(User user) {
//        if(user.getId()==null){
//            //mybatis-plus提供的方法save,表示插入数据
//            return save(user);
//        }else {
//            return updateById(user);
//        }
        return saveOrUpdate(user);
    }

    //原mybatis写法
//    @Autowired
//    private UserMapper userMapper;

//    public int save(User user){
//        if(user.getId()==null){//user id为空 新增数据
//            return userMapper.insert(user);
//        }else{//user id不为空 更新原有数据
//            return userMapper.update(user);
//        }
//    }
}
