package com.example.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.pojo.Company;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CompanyMapper extends BaseMapper<Company> {
}
