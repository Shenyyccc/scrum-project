package com.example.backend.pojo.DTO;

import lombok.Data;

import java.util.List;

@Data
public class FormTaskDTO {

    private String companyid;
    private String departmentid;
    private Integer expiretime;
    private String id;
    private Integer key;
    private String label;
    private String pid;
    private List<Integer> preTaskKey;
    private List<FormTaskDTO> preworks;
    private String taskname;
    private String taskscrib;


}
