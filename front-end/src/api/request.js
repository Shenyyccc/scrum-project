/* eslint-disable*/
import axios from 'axios'

axios.defaults.timeout = 5000 // 超时时间设置
axios.defaults.withCredentials = true // 是否跨域
axios.defaults.baseURL = '/url' // 域名或ip 【注意/url和后面的vue.config.js的/url相匹配】

// Content-Type 响应头
axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=UTF-8";

// 先简单弄下小例子  /statis/api/all 获取所有api请求信息
export const getMain= ()=> {
  return axios.get(`/hello`)
}

export const ChangWorkList=(param)=>{
  return axios.get("/WorkList/Change",{
    params:{
      Id:param.paramId,
      ProcessId:param.paramProcessId,
    }
  })
}

export const getUnstart=()=>{
  return axios.get("/WorkList/getprocess1");
}

export const getStarting=()=>{
  return axios.get("/WorkList/getprocess2");
}

export const getFinished=()=>{
  return axios.get("/WorkList/getprocess3");
}


