package com.example.backend.pojo.DTO;

import lombok.Data;

import java.util.List;

@Data
public class FormDefinitionDTO {
    private String processname;

    private String companyid;
    private List<FormTaskDTO> workflowData;
}
