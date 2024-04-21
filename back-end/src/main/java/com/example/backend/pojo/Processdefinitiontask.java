package com.example.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.backend.Handler.ListToStringHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Processdefinitiontask {

    private String id;
    private String taskname;
    private String taskscrib;
    private Integer expiretime;
    private String departmentid;
    private String companyid;
    @Column(name="taskkey")
    private Integer taskkey;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private String definitionid;

    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = ListToStringHandler.class)
    private List<Integer> pretaskkey;


}
