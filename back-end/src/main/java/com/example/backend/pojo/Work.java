package com.example.backend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Work {

    private String id;

    private String workname;

    private String workscrib;

    private int workprio;

    private int workprocess;

}
