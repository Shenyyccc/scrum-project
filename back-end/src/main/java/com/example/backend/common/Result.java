package com.example.backend.common;

import lombok.Data;

/*
* 封装，统一的返回数据结构
* */
@Data
public class Result {

    private String code;

    private String message;

    private Object data;

    public static Result success(){
        Result result = new Result();
        result.setCode("200");
        return result;
    }

    public static Result success(Object obj){
        Result result = new Result();
        result.setCode("200");
        result.setData(obj);
        return result;
    }

    public static Result error(String msg){
        Result result = new Result();
        result.setCode("406");
        result.setMessage(msg);
        return result;


    }
}
