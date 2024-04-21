package com.example.backend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.mapper.UserMapper;
import com.example.backend.mapper.WorkMapper;
import com.example.backend.pojo.User;
import com.example.backend.pojo.Work;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BackEndApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WorkMapper workMapper;
    @Test
    void contextLoads() {
        //用户查询
//        List<User> users = userMapper.selectList(null);
//        for (User user : users) {
//            System.out.println(user);
//        }
        //任务查询
//        QueryWrapper<Work> wrapper=new QueryWrapper<>();
//        wrapper.eq("workprocess",2);
//        List<Work> works = workMapper.selectList(wrapper);
//        for (Work work : works) {
//            System.out.println(work);
//        }

//        Work work = workMapper.selectById(1);
//        System.out.println(work);
//        List<String> prework=new ArrayList<>();
//        prework.add("2");
//        prework.add("3");
//        prework.add("4");
//        prework.add("5");
        Work work = workMapper.selectById(1);
        System.out.println("===============>"+work.getWorkname()+"   "+work.getWorkscrib());
        List<String> preworks = work.getPreworks();
        for (String prework : preworks) {
            System.out.println("===============>"+prework);
        }
//        UpdateWrapper<Work> wrapper = new UpdateWrapper<>();
//        wrapper.eq("id",1);
//        work.setWorkprocess(2);
//        work.setPreworks(prework);

//        workMapper.update(work,wrapper);

    }

}
