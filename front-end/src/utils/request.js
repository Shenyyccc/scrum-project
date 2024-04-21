import axios from 'axios'
import ElementUI, {MessageBox} from "element-ui";
import router from "@/router";

const request = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 5000
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
  console.log("request========>")
  config.headers['Content-Type'] = 'application/json;charset=utf-8';
  // config.headers['Content-Type'] = 'application/json';
  let user = sessionStorage.getItem("user")?JSON.parse(sessionStorage.getItem("user")):{}
  // console.log(user)
  if(user!={}){
    config.headers['token'] = user.token;  // 设置请求头
  }


  return config
}, error => {
  return Promise.reject(error)
});

// // response 拦截器
// // 可以在接口响应后统一处理结果
request.interceptors.response.use(
  response => {
    let res = response;
    console.log("respond========>")
    // console.log(res);
    //如果是返回的文件
    if (response.config.responseType === 'blob') {
      return res
    }
    // 兼容服务端返回的字符串数据
    if (typeof res === 'string') {
      res = res ? JSON.parse(res) : res
    }

    //token权限不足时给出提示
    if(res.data.code==='401'){
      ElementUI.Message({
        message:res.data.message,
        type:'error'
      });
    }

    if(res.data.code==='406'){
      // ElementUI.Message({
      //   message:res.data.message,
      //   type:'error'
      // });
      MessageBox.alert(response.data.message, "登录失效", {
        confirmButtonText: "跳转登录页面",
        callback: action => {
          // 跳转登录页
          window.location.href = "/";
        }
      });
      // sessionStorage.removeItem('user')
      return null;
    }

    return res;
  },
  error => {
    console.log('err' + error) // for debug
    // sessionStorage.removeItem('user')
    return Promise.reject(error)
  }
)


export default request

