package com.example.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tasktemplate")
public class Tasktemplate {
    private String id;
    private String taskname;
    private String taskscrib;
    private Integer expiretime;
    private String departmentid;
    private String companyid;
}
