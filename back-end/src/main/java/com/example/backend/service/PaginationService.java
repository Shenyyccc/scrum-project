package com.example.backend.service;

import com.example.backend.pojo.DTO.PageParams;
import com.example.backend.pojo.Runningtask;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaginationService {

    public PageParams getPage(List<Runningtask> list, int pageNo, int pageSize) {
        // 使用 PageHelper 进行简单的分页计算
//        PageHelper.startPage(pageNo, pageSize);
        PageParams pageParams=new PageParams();
        pageParams.setTotalSize(list.size());
        pageParams.setPageSize(pageSize);
        pageParams.setPageNo(pageNo);
        pageParams.setTotalPages((int) Math.ceil((double) list.size() / pageSize));


        // 计算起始索引和结束索引
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());

        // 获取当前页的数据
        List<Runningtask> pageList = new ArrayList<>(list.subList(startIndex, endIndex));
        pageParams.setList(pageList);
        pageParams.setDataNum(pageList.size());


        return pageParams;
    }
}


