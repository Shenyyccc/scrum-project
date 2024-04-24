package com.example.backend.pojo.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    String userId;
    String username;
    String name;
    String avatar;
    Integer identity;
    String token;
    String company;
    String companyid;

    String departmentid;
}
