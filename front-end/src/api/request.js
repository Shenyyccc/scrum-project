import request from "@/utils/request";
import axios from "axios";
import qs from 'qs'

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
    params: param
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

export function CheckCompany(param){
  return request({
    url:'/checkCompany',
    method:'get',
    params: {
      company:param
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
      departmentName:param.value,
      companyid:param.companyId
    }
  })
}

export function editDepart(param){
  return request({
    url:"/department/edit",
    method:"get",
    params:{
      departmentName:param.value,
      departmentid:param.departmentid
    }
  })
}

export function deleteDepart(param){
  return request({
    url:"/department/delete",
    method:"get",
    params: {
      departmentid: param
    }
  })
}

export function getDepart(param){
  return request({
    url:"/department/get",
    method:"get",
    params:{
      companyId:param
    }
  })
}

export function getIndividualDepart(param){
  return request({
    url:"/department/getIndividualDepart",
    method:"get",
    params:{
      id:param,
    }
  })
}

export function getNoDepart(params){
  return request({
    url:"/department/getNoDepart",
    method:'get',
    params:{
      companyId:params.comId,
      research:params.rese,
    }
  })
}

export function joinDepart(params){
  return request({
    url:"/department/joinDepart",
    method:'get',
    params:{
      userId:params.userId,
      departId:params.departId,
      companyId:params.companyId,
    }
  })
}

export function removeDepart(params){
  return request({
    url:"/department/removeDepart",
    method:'get',
    params:{
      userId:params.userId,
      departId:params.departId
    }
  })
}

export function getDepartMember(params){
  return request({
    url:"/department/getDepartMember",
    method:'get',
    params:{
      departId:params.departId,
      input:params.input,
    }
  })
}

export function setManager(params){
  return request({
    url:"/department/setManager",
    method:'get',
    params:{
      departId:params.departId,
      userId:params.userId,
    }
  })
}

export function getTasks(params){
  return request({
    url:"/getDepartTask",
    method:"get",
    params: {
      name:params.name,
      scrib:params.scrib,
      time:params.time,
      departmentid:params.departmentid,
      pageSize:params.pageSize,
      pageNum:params.pageNum,
    },
  })
}

export function createTempalte(params){
  return request({
    url:"/templateTask/create",
    method:"get",
    params: {
      taskname:params.taskname,
      taskscrib:params.taskscrib,
      expiretime:params.expiretime,
      departmentid:params.departmentid,
      companyid:params.companyid
    },
  })
}

export function getTempalte(params){
  return request({
    url:"/templateTask/get",
    method:"get",
    params: {
      name:params.name,
      content:params.content,
      pageSize:params.pageSize,
      pageNum:params.pageNum,
      departmentid:params.departmentid
    },
  })
}


export function getTempalteByid(params){
  return request({
    url:"/templateTask/getByid",
    method:"get",
    params: {
      departmentid:params
    },
  })
}

export function deleteTemplateById(param){
  return request({
    url:"/templateTask/deleteTemplateById",
    method:"get",
    params:{
      taskId:param
    }
  })
}


export function updateTemplate(params){
  return request({
    url:"/templateTask/updateTemplate",
    method:"post",
    params:params
  })
}


export function getUserById(params){
  return request({
    url:"/getUserById",
    method:'get',
    params: {
      // companyid:params.companyid,
      // departmentid:params.departmentid,
      // expiretime:params.expiretime,
      userId:params,
      // taskname:params.taskname,
      // taskscrib:params.taskscrib
    }
  })
}

export function updateUser(params){
  return request({
    url:"/updateUser",
    method:'post',
    params:params,
  })
}

export function updatePass(params){
  return request({
    url:"/updatePass",
    method:'post',
    data:params,
  })
}

export function getTaskTemplates(param){
  return request({
    url:'/templateTask/getTaskTemplate',
    method:'get',
    params:{
      companyid:param
    }
  })
}

export function getUserInfo(param){
  return request({
    url:'/getUserInfo',
    method:'get',
    params:{
      userId:param
    }
  })
}

export function createDefinition(params){
  return request({
    url:'/taskflowDefinition/create',
    method:'post',
    data:params,
  })
}

export function getWorkflowTemplate(params){
  return request({
    url:'/taskflowDefinition/getWorkflowTemplate',
    method:'get',
    params: {
      search:params.search,
      pageSize:params.pageSize,
      pageNum:params.pageNum,
      companyid:params.companyid
    },
  })
}

export function deleteWorkflowTemplate(params){
  return request({
    url:'/taskflowDefinition/deleteWorkflowTemplate',
    method:'get',
    params: {
      id:params,
    },
  })
}


export function deployDefinition(params){
  return request({
    url:'/deploy',
    method:'get',
    params: {
      id:params,
    },
  })
}

export function pickUpTask(params){
  return request({
    url:'/pickUp',
    method:'get',
    params:{
      taskid:params.taskid,
      userid:params.userid,
    }
  })
}

export function getRunningTask(params){
  return request({
    url:'/getRunningTask',
    method:'get',
    params:{
      userid:params
    }
  })
}

export function finishTask(params){
  return request({
    url:'/finishTask',
    method:'get',
    params:{
      id:params
    }
  })
}

export function sendSms(params){
  return request({
    url:'/sendSms',
    method:'get',
    params: {
      phone:params
    }
  })
}

export function modifyPass(params){
  return request({
    url:'/modifyPass',
    method:'post',
    data:params
  })
}

export function getRunningTaskByWorkflowId(param){
  return request({
    url:'/getRunningTaskByWorkflowId',
    method:'get',
    params:{
      workflowId:param
    }
  })
}


export function getRunningWorkflow(params){
  return request({
    url:'/getRunningWorkflow',
    method:'get',
    params:{
      search:params.search,
      pageSize:params.pageSize,
      pageNum:params.pageNum,
      companyid:params.companyid
    }
  })
}

