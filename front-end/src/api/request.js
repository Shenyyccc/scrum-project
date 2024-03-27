import request from "@/utils/request";
import axios from "axios";

export function Login(param) {
  return request({
    url: '/login',
    method: 'post',
    params: param
  })
}

export function Register(param){
  return request({
    url:'/register',
    method:'post',
    params:param,
  })
}

export function CheckRegister(param){
  return request({
    url:'/checkUsername',
    method:'get',
    params: {
      username:param
    },
  })
}


export function getUnstart(){
  return request({
    url:"/WorkList/getprocess1",
    method:"get"
  })
}

export function getStarting(){
  return request({
    url:"/WorkList/getprocess2",
    method:"get"
  })
}
//
export function getFinished(){
  return request({
    url:"/WorkList/getprocess3",
    method:"get"
  })
}

export const ChangWorkList=(param)=>{
  return request({
    url:"/WorkList/Change",
    params:{
      Id:param.paramId,
      ProcessId:param.paramProcessId,
    }
  })
}

/*
* 部门方法
* */
export function createDepart(param){
  return request({
    url:"/department/create",
    method:"get",
    params:{
      departmentName:param
    }
  })
}

export function getDepart(param){
  return request({
    url:"/department/get",
    method:"get",
  })
}

export function getWorks(params){
  return request({
    url:"/WorkList/pickup",
    method:"get",
    params: {
      name:params.name,
      scrib:params.scrib,
      prio:params.prio,
      time:params.time,
      pageSize:params.pageSize,
      pageNum:params.pageNum,
    },
  })
}
