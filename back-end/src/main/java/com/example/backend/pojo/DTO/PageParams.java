package com.example.backend.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {
    String name;
    String scrib;
    String prio;
    String time;
    Integer pageSize;
    Integer pageNum;
    /*
    * input:{
        name:'',
        scrib:'',
        prio:'',
        time:'',
        pageSize:9,
        pageNum:1,
      },
    * */
}
