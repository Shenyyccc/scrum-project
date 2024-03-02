package com.example.back.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back.entity.User;
import com.example.back.mapper.UserMapper;
import com.example.back.service.UserService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private UserMapper userMapper;

    //增加+更新
    @PostMapping
    public Boolean save(@RequestBody User user){
        return userService.saveUser(user);
    }

    //查询所有数据
    @GetMapping
    public List<User> searchAll(){
        //return userMapper.searchAll();
        return userService.list();
    }

    //分页查询
    //@RequestParam 接受分页数据 ?pageNum=1&pageSize=10
    @GetMapping("/page")
    public IPage<User> searchPage(@RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize,
                                          @RequestParam(defaultValue = "") String username,
                                          @RequestParam(defaultValue = "") String address){
        // mybatis方式
//        pageNum=(pageNum-1) * pageSize;
//        List<User> data = userMapper.searchPage(pageNum, pageSize,username,address);
//        Integer total = userMapper.selectTotal(username,address);
//        Map<String, Object> res = new HashMap<>();
//        res.put("data",data);
//        res.put("total",total);

        // mybatis-plus方式
        //存在bug:被查询字段值为null时，无法查询出这条数据  应优化为<<<<<<<<<LambdaQueryWrapper>>>>>>>>>>
        //暂时修复：给queryWrapper加条件属性
        IPage<User> page = new Page<>(pageNum,pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        if(!"".equals(username)){
            queryWrapper.like(!"".equals(username),"username",username);
//        }
//        if(!"".equals(address)){
            queryWrapper.like(!"".equals(address),"address",address);
//        }
        queryWrapper.orderByDesc("id");
        return userService.page(page,queryWrapper);
    }

    //删除
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id){
//        return userMapper.deleteByid(id);
        return userService.removeById(id);
    }

    //批量删除
    @PostMapping("/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids){
        return userService.removeBatchByIds(ids);
    }
}
