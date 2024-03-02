package com.example.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.back.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from sys_user")
    List<User> searchAll();

    @Insert("INSERT INTO sys_user(username,password,email,phone,address) VALUE (#{username},#{password},#{email},#{phone},#{address})")
    int insert(User user);

    @Update("update sys_user set username=#{username},password =#{password},email =#{email},phone =#{phone},address =#{address} where id=#{id}")
    int update(User user);

    @Delete("delete from sys_user where id=#{id}")
    Integer deleteByid(@Param("id") Integer id);

    //分页模糊查询 (根据 用户名username 和 地址address )

    @Select("select * from sys_user where username like concat('%',#{username},'%') " +
            "or address LIKE concat('%',#{address},'%') limit #{pageNum},#{pageSize}")
    List<User> searchPage(Integer pageNum,Integer pageSize,String username,String address);

    //模糊查询数量
    @Select("select count(*) from sys_user where username like concat('%',#{username},'%') " +
            "or address LIKE concat('%',#{address},'%')")
    Integer selectTotal(String username,String address);
}
