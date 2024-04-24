package com.example.backend.pojo.DTO;

import com.example.backend.pojo.Runningtask;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {
    private Integer totalPages;
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalSize;
    private Integer dataNum;
    private List<Runningtask> list;

}
