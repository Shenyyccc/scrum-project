package com.example.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_user")
public class User {
    @TableId(type= IdType.AUTO) //主键
    private Integer id;
    private String username;
//    @JsonIgnore //前端无法查看密码   同样会导致前端无法接收到这个实体类的属性值 改使用下面注解
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private String phone;
    private String address;

    @TableField(value = "avatar_url")//指定数据库的字段名称
    //或者使用驼峰命名：avatarUrl   数据库中字段为：avatar_url
    private String avatar;
}
