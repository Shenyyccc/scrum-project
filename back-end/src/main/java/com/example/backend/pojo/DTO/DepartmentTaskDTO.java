package com.example.backend.pojo.DTO;

import com.example.backend.pojo.Department;
import com.example.backend.pojo.Tasktemplate;
import com.example.backend.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentTaskDTO extends Department{
    List<Tasktemplate> workList;
    User manager;
}
