<template>
  <div style="text-align: center;margin-top: 70px">
    <h3>先选择本次登录人员的身份，再进入相应的系统 😅</h3>
    <div style="margin-top: 30px">
      <el-button type="primary" round size="medium" @click="dialogVisible_1=true" icon="el-icon-user">登录</el-button>
      <el-button type="primary" round size="medium"  @click="dialogVisible = true" icon="el-icon-s-check">注册</el-button>
    </div>
    <div class="work-panel">
      <div class="panel">
        <div class="panel-item" @click="to('/WorkSpace')">
          <div>
            <i class="el-icon-s-platform"></i>
            <span>进入工作区</span>
          </div>
          <p>
            您可以发起、处理及查看审批，进行日常工作任务
          </p>
        </div>
        <div class="panel-item" @click="">
          <div>
            <i class="el-icon-s-custom"></i>
            <span>进入管理后台</span>
          </div>
          <p>
            审批工作流创建 、编辑及其他设置操作，均可以在后台进行
          </p>
        </div>
      </div>

    </div>

    <!--登录    -->
    <el-dialog
      title="登陆页面"
      :visible.sync="dialogVisible_1"
      width="30%">

<!--      v-model 主要用于处理表单元素的双向数据绑定，而 v-bind 用于动态地将属性与 Vue 数据绑定-->
      <el-form :model="user" ref="userLoginForm" :rules="rules">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" placeholder="请输入用户名" clearable
                    v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" placeholder="请输入密码" clearable
                    show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="code">
          <div style="display: flex">
            <el-input style="margin: 10px 0;flex: 1;"v-model="user.code" prefix-icon="el-icon-loading"
                      size="medium" placeholder="请输入验证码"></el-input>
            <div style="flex: 1;">
              <valid-code @update-value="getCode"></valid-code>
            </div>
          </div>
        </el-form-item>
        <div style="color: red">{{msg}}</div>
        <el-form-item style="margin: 10px 0;text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="onSubmit('userLoginForm')" style="width: 100%;margin-bottom: 10px">登录</el-button>
          <!--           <el-button type="warning" size="small" autocomplete="off">注册</el-button>-->
        </el-form-item>
        <el-form-item style="margin-bottom: 0;">
          <div style="display: flex">
            <div style="flex:1">还没有账号？<span style="color: #0f9876;cursor: pointer" @click="dialogVisible_1=false;dialogVisible=true">点击注册</span></div>
            <div style="flex: 1;text-align: right"><span style="color: #0f9876;cursor: pointer">忘记密码</span></div>
          </div>
        </el-form-item>
      </el-form>

<!--      <span slot="footer" class="dialog-footer">-->
<!--       </span>-->
    </el-dialog>


    <!--注册    -->
    <el-dialog
      title="注册页面"
      :visible.sync="dialogVisible"
      width="30%">
      <!---注意：model是vue.js中内置的双向数据绑定指令，:model相当于v-bind:model的缩写,而表单的数据对象是model-->
      <el-form ref="newUser"  :model="newUser" :rules="Rules">
          <el-form-item  label="用户名: " prop="username" label-width="95px">
            <el-input placeholder="请输入用户名" v-model="newUser.username"></el-input>
          </el-form-item>

          <el-form-item label="密码: " prop="password" label-width="95px">
            <el-input placeholder="请输入密码" v-model="newUser.password"></el-input>
          </el-form-item>

          <el-form-item label="确认密码: " prop="confirmPassword" label-width="95px">
            <el-input placeholder="请再次输入密码" v-model="newUser.confirmPassword"></el-input>
          </el-form-item>

        <el-form-item label="姓名: " prop="name" label-width="95px">
          <el-input placeholder="请输入姓名" v-model="newUser.name"></el-input>
        </el-form-item>

          <el-form-item label="手机号: " prop="phone" label-width="95px">
            <el-input placeholder="请输入手机号" v-model="newUser.phone"></el-input>
          </el-form-item>

          <el-form-item label="邮箱: " prop="email" label-width="95px">
            <el-input placeholder="请输入你的邮箱" v-model="newUser.email"></el-input>
          </el-form-item>

          <el-form-item label="是否为管理员：" label-width="150px" prop="admin">
            <el-radio-group v-model="newUser.identity">
              <el-radio :label="1">是</el-radio>
              <el-radio  :label="2">否</el-radio>
            </el-radio-group>
          </el-form-item>

        <el-form-item style="margin-bottom: 0;">
          <div style="display: flex"></div>
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="onRegister('newUser')">确 定</el-button>
        </el-form-item>

      </el-form>

    </el-dialog>



  </div>
</template>


<script>
import register from "../components/common/register.vue";
import {CheckRegister, Login,Register} from "@/api/request";
import validCode from "@/components/common/ValidCode.vue";

export default {
  name: "Main",
  components:{
    register,
    validCode
  },
  data() {

    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'));
      } else if (value.toLowerCase()!==this.code){
        callback(new Error('验证码错误'))
      }else{
        callback();
      }
    };

    const validatePass=(rule, value, callback)=>{
      if (value === '') {
        callback(new Error('请输入密码'));
      } else if (value!==this.newUser.password){
        callback(new Error('两次密码不一致'))
      }else{
        callback();
      }
    }

    const validateUsername=(rule, value, callback)=>{
      // const flag=CheckRegister(value)
      CheckRegister(value).then(rep=>{
        console.log(rep)
        if(rep.data===true){
          callback(new Error('用户名已存在'));
        }else {
          callback();
        }
      })
    }

    return {

      newUser:{
        username:'',
        password:'',
        confirmPassword:'',
        name:'',
        phone:'',
        email:'',
        identity:''
      },

      existUsername:[],

      user:{
        username:'',
        password:'',
        confirmPass:'',
        code:''
      },
      form:{
        username:'',
        password:'',
      },
      rules:{
        username:[{required:true,message:'用户名不能为空',trigger:'blur'}],
        password:[{required:true,message:'密码不能为空',trigger:'blur'}],
        // validator用来自定义验证方式
        code:[{validator:validateCode,trigger:'blur'}],
      },

      Rules:{
        username:[{required:true,message:'用户名不能为空',trigger:'blur'},
                  {validator:validateUsername,trigger: 'blur'}],
        password:[{required:true,message:'密码不能为空',trigger:'blur'}],
        confirmPassword: [{validator:validatePass,trigger:'blur'},
                          {required:true,message:'新密码不能为空',trigger:'blur'}],
        // confirmPassword: [{required:true,message:'新密码不能为空',trigger:'blur'}],
        name: [{required:true,message:'姓名不能为空',trigger:'blur'}],
        phone:[{required:true,message:'手机不能为空',trigger:'blur'}],
        email: [{required:true,message:'邮件不能为空',trigger:'blur'}],
        identity: [{required:true,message:'身份不能为空',trigger:'blur'}]
      },
      msg:'',
      dialogVisible:false,
      dialogVisible_1:false,
      value: '',
    };
  },

  methods:{
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Login(this.user).then(res=>{
            console.log(res.data)
            if (res.data.token!=null) {
              localStorage.setItem("user",JSON.stringify(res.data))   //把用户信息存到浏览器中
              sessionStorage.setItem("user",JSON.stringify(res.data))
              this.dialogVisible_1=false;
              this.$message.success("登陆成功");
            }else{
              this.$message.error("账号或者密码错误")
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    onRegister(formName) {
      console.log(this.newUser)
      this.$refs[formName].validate((valid) => {
        console.log("2.=========="+formName)
        if (valid) {
          console.log(JSON.stringify(this.newUser))
          Register(this.newUser).then(res=>{
            console.log(res)
            this.dialogVisible=false;
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    getCode(code){
      console.log(code)
      this.code=code.toLowerCase()
    },

    to(path){
        this.$router.push(path)
    }
  }
}
</script>

<style lang="less" scoped>
.work-panel {
  text-align: left;
  display: flex;
  justify-content: center;
  position: relative;
  .panel {
    margin-top: 80px;
    max-width: 700px;
    display: flex;
    justify-content: center;

    .panel-item {
      cursor: pointer;
      margin: 0 40px;
      width: 250px;
      padding: 10px;
      display: inline-block;
      background: #ffffff;
      border-radius: 10px;
      border: 1px solid #ffffff;
      box-shadow: 1px 1px 8px 0 #b0b0b1;

      &:hover {
        border: 1px solid #2594ff;
        box-shadow: 1px 1px 13px 0 #a4a4a5;
      }

      div:nth-child(1) {
        color: #7a7a7a;
        font-weight: bold;
        height: 60px;
        line-height: 60px;
        font-size: large;
        border-bottom: 1px solid #cccdcd;

        span {
          margin-left: 30px;
        }
      }

      i {
        padding: 8px;
        color: #ffffff;
        background: #2594ff;
        font-size: 25px;
        border-radius: 5px;
      }

      .el-icon-s-platform {
        background: rgb(255, 148, 62);
      }

      p {
        padding: 10px 0;
        color: #7a7a7a;
        font-size: medium;
      }
    }
  }

}
</style>
