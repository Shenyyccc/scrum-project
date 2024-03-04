package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.pojo.Work;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Primary;

@Mapper
public interface WorkMapper extends BaseMapper<Work> {

}
