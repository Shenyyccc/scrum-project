// /* eslint-disable*/
// import axios from 'axios'
// import {data} from "autoprefixer";
//
//
// axios.defaults.timeout = 5000 // 超时时间设置
// axios.defaults.withCredentials = true // 是否跨域
// axios.defaults.baseURL = '/url' // 域名或ip 【注意/url和后面的vue.config.js的/url相匹配】
//
// //axios.create创建了一个自定义配置的Axios组件，然后在整个应用程序中重复使用
// const request = axios.create({
//   baseURL: 'http://localhost:8080',  // 注意！！ 这里是全局统一加上了 '/api' 前缀，也就是说所有接口都会加上'/api'前缀在，页面里面写接口的时候就不要加 '/api'了，否则会出现2个'/api'，类似 '/api/api/user'这样的报错，切记！！！
//   timeout: 5000
// });
// //  export default request;
//
// // Content-Type 响应头
// axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=UTF-8";
//
// // 先简单弄下小例子  /statis/api/all 获取所有api请求信息
// export const getMain= (param)=> {
//   // return axios.get(`/login`,{
//   //   params:{
//   //     username:param.username,
//   //     password:param.password,
//   //   }
//   // })
//   request.get(`/login`,{
//
//   })
// }
//
// export const ChangWorkList=(param)=>{
//   return axios.get("/WorkList/Change",{
//     params:{
//       Id:param.paramId,
//       ProcessId:param.paramProcessId,
//     }
//   })
// }
//
// export const getUnstart=()=>{
//   return axios.get("/WorkList/getprocess1");
// }
//
// export const getStarting=()=>{
//   return axios.get("/WorkList/getprocess2");
// }
//
// export const getFinished=()=>{
//   return axios.get("/WorkList/getprocess3");
// }
//
//
