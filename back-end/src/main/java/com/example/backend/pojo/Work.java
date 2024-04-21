package com.example.backend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.backend.Handler.ListToStringHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(autoResultMap = true)
public class Work {

    private String id;

    private String workname;

    private String workscrib;

    private int workprio;

    private int workprocess;


    @TableField(jdbcType = JdbcType.VARCHAR, typeHandler = ListToStringHandler.class)
    private List<String> preworks;

}
