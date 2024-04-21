<template>
  <div style="text-align: center;margin-top: 70px">
    <h3>First select the identity of the person who logs in this time, and then enter the corresponding system 😊</h3>


    <div v-if="show" style="margin-top: 30px;height: 100px">
      <div style="margin-left: 20px">
        <h1 v-if="loginUser.company==''">No Company</h1>
        <h1 v-else>{{loginUser.company}}</h1>
      </div>

      <div  style=" display: flex;margin-left: 620px">
        <span v-if="loginUser.avatar!=null"> <el-avatar :src="'http://localhost:8080/files/'+loginUser.avatar" size="large"></el-avatar></span>
        <span v-else><el-avatar>{{loginUser.name.charAt(0)}}</el-avatar></span>
        <span style="line-height: 40px;margin-left: 10px">Welcome home! Dear {{loginUser.name}}</span>
        <el-button size="small" type="danger" style="margin-left: 20px" @click="logout" icon="el-icon-back"  round>EXIT</el-button>
      </div>
    </div>
    <div v-else style="margin-top: 30px;height: 100px">
      <el-button type="primary" round size="medium" @click="dialogVisible_login=true" icon="el-icon-user">LOG IN</el-button>
      <el-button type="primary" round size="medium"  @click="dialogVisible_register = true" icon="el-icon-s-check">REGISTER</el-button>
    </div>

    <div class="work-panel">
      <div class="panel">
        <div class="panel-item" @click="to('/WorkSpace')">
          <div>
            <i class="el-icon-s-platform"></i>
            <span>Enter the workspace</span>
          </div>
          <p>
            You can initiate, process, and view tasks for daily work tasks
          </p>
        </div>
        <div class="panel-item" @click="to('/Backstage')">
          <div>
            <i class="el-icon-s-custom"></i>
            <span>Enter the backstage</span>
          </div>
          <p>
            Workflow creation, task editing, and other Settings can be done in the background
          </p>
        </div>
      </div>

    </div>


    <!--登录    -->
    <el-dialog
      title="LOG IN"
      :visible.sync="dialogVisible_login"
      width="30%"
      :before-close="handleClose">

<!--      v-model 主要用于处理表单元素的双向数据绑定，而 v-bind 用于动态地将属性与 Vue 数据绑定-->
      <el-form :model="user" ref="userLoginForm" :rules="rules">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" placeholder="Please enter your username" clearable
                    v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" placeholder="Please enter your password" clearable
                    show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="code" v-if="count!=0">
          <div style="display: flex">
            <el-input style="margin: 10px 0;flex:2" v-model="user.code" prefix-icon="el-icon-refresh-right"
                      placeholder="Please enter the verification code"></el-input>
            <div style="flex:1">
              <valid-code @update-value="getCode"></valid-code>
            </div>
          </div>
        </el-form-item>
        <div style="color: red">{{msg}}</div>
        <el-form-item style="margin: 10px 0;text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="onSubmit('userLoginForm')" style="width: 100%;margin-bottom: 10px">LOG IN</el-button>
        </el-form-item>
        <el-form-item style="margin-bottom: 0;" >
          <div style="display: flex;">
            <div style="flex:1">Don't have an account yet？</div>
<!--            <div style="flex: 1;text-align: right"><span style="color: #0f9876;cursor: pointer">Lost Password</span></div>-->
            <div style="color: #0f9876;cursor: pointer;margin-left: 18px;flex: 1" @click="dialogVisible_login=false;dialogVisible_register=true">
              Click to register
            </div>
          </div>
<!--          <div style="display: flex;margin-top: 0px">-->
<!--               <span style="color: #0f9876;cursor: pointer;margin-left: 18px" @click="dialogVisible_login=false;dialogVisible_register=true">Click to register</span>-->
<!--          </div>-->
        </el-form-item>
      </el-form>

<!--      <span slot="footer" class="dialog-footer">-->
<!--       </span>-->
    </el-dialog>


    <!--注册    -->
    <el-dialog
      title="REGISTER PAGE"
      :visible.sync="dialogVisible_register"
      width="30%"
      :before-close="handleClose_register">
      <!---注意：model是vue.js中内置的双向数据绑定指令，:model相当于v-bind:model的缩写,而表单的数据对象是model-->
      <el-form ref="newUser"  :model="newUser" :rules="Rules">
          <el-form-item  label="USERNAME: " prop="username" label-width="150px" style="font-size: 5px">
            <el-input placeholder="Please enter the username" v-model="newUser.username"></el-input>
          </el-form-item>

          <el-form-item label="PASSWORD: " prop="password" label-width="150px">
            <el-input placeholder="Please enter the password" v-model="newUser.password"></el-input>
          </el-form-item>

          <el-form-item label="CONFIRM PASSWORD: " prop="confirmPassword" label-width="150px">

            <el-input placeholder="Please enter the password again" v-model="newUser.confirmPassword"></el-input>
          </el-form-item>

        <el-form-item label="NAME: " prop="name" label-width="150px">
          <el-input placeholder="Please enter the name" v-model="newUser.name"></el-input>
        </el-form-item>

          <el-form-item label="PHONE: " prop="phone" label-width="150px">
            <el-input placeholder="Please enter the phone" v-model="newUser.phone"></el-input>
          </el-form-item>

          <el-form-item label="E-MAIL: " prop="email" label-width="150px">
            <el-input placeholder="Please enter the e-mail" v-model="newUser.email"></el-input>
          </el-form-item>

          <el-form-item label="Administrator or not：" label-width="150px" prop="admin">
            <el-radio-group v-model="newUser.identity">
              <el-radio :label="1">YES</el-radio>
              <el-radio  :label="0">NO</el-radio>
            </el-radio-group>
          </el-form-item>

        <el-form-item label="Company name：" label-width="150px" prop="company" v-if="newUser.identity===1">
          <el-input placeholder="Please enter the e-mail" v-model="newUser.company"></el-input>
        </el-form-item>

        <el-form-item style="margin-bottom: 0;">
          <div style="display: flex"></div>
          <el-button @click="dialogVisible_register = false">CANCEL</el-button>
          <el-button type="primary" @click="onRegister('newUser')">CONFIRM</el-button>
        </el-form-item>

      </el-form>

    </el-dialog>




  </div>
</template>


<script>
import register from "../components/common/register.vue";
import {CheckCompany, CheckRegister, Login, Register} from "@/api/request";
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
        callback(new Error('Please enter the verification code'));
      } else if (value.toLowerCase()!==this.code){
        callback(new Error('Verification code error'))
      }else{
        callback();
      }
    };

    const validatePass=(rule, value, callback)=>{
      if (value === '') {
        callback(new Error('Please enter your password'));
      } else if (value!==this.newUser.password){
        callback(new Error('Two passwords do not match'))
      }else{
        callback();
      }
    }

    const validateUsername=(rule, value, callback)=>{
        // const flag=CheckRegister(value)
        CheckRegister(value).then(rep=>{
        console.log(rep)
        if(rep.data===true){
          callback(new Error('The username already exists'));
        }else {
          callback();
        }
      })
    }


    const validateCompany=(rule, value, callback)=>{
        // const flag=CheckRegister(value)
        CheckCompany(value).then(rep=>{
        console.log(rep)
        if(rep.data===true){
          callback(new Error('The Company already exists'));
        }else {
          callback();
        }
      })
    }

    return {

      loginUser:sessionStorage.getItem('user')?JSON.parse(sessionStorage.getItem('user')):{},

      newUser:{
        username:'',
        password:'',
        confirmPassword:'',
        name:'',
        phone:'',
        email:'',
        identity:'0',
        company:'',
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
        username:[{required:true,message:'The username cannot be empty',trigger:'blur'}],
        password:[{required:true,message:'The password cannot be empty',trigger:'blur'}],
        // validator用来自定义验证方式
        code:[{validator:validateCode,trigger:'blur'}],
      },

      Rules:{
        username:[{required:true,message:'The username cannot be empty',trigger:'blur'},
                  {validator:validateUsername,trigger: 'blur'}],
        password:[{required:true,message:'The password cannot be empty',trigger:'blur'}],
        confirmPassword: [{validator:validatePass,trigger:'blur'},
                          {required:true,message:'The new password cannot be empty',trigger:'blur'}],
        // confirmPassword: [{required:true,message:'新密码不能为空',trigger:'blur'}],
        name: [{required:true,message:'The name cannot be empty',trigger:'blur'}],
        phone:[{required:true,message:'The phone cannot be empty',trigger:'blur'}],
        email: [{required:true,message:'The e-mail cannot be empty',trigger:'blur'}],
        identity: [{required:true,message:'The identity cannot be empty',trigger:'blur'}],
        company:[{required:true,message:'The company cannot be empty',trigger:'blur'},
                 {validator:validateCompany,trigger: 'blur'}],
      },
      msg:'',

      //登录和注册弹窗
      dialogVisible_register:false,
      dialogVisible_login:false,
      value: '',

      //是否显示登录页面
      show:sessionStorage.getItem('user')?true:false,

      //头像
      avatar:'',

      //输入密码错误次数
      count:0,

    };
  },

  methods:{
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          Login(this.user).then(res=>{
            console.log(res.data)
            if (res.data.code==='200') {
              // localStorage.setItem("user",JSON.stringify(res.data.data))   //把用户信息存到浏览器中
              sessionStorage.setItem("user",JSON.stringify(res.data.data))
              this.loginUser=res.data.data
              console.log(this.loginUser)
              this.dialogVisible_login=false;
              this.$message.success("Login successfully");
              this.show=true;
            }else{
              this.$message.error(res.data.message)
              this.msg=res.data.message
              this.count++;
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
              this.$message.success("Register successfully");
              this.dialogVisible_register=false;
              this.newUser={};
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
      if(!sessionStorage.getItem('user')){
        this.$message.warning("Please login your account first!")
      }else {
        let loginUser=JSON.parse(sessionStorage.getItem('user'))
        console.log(loginUser);
        if(loginUser.identity===0){
          this.$message.warning("Please contact the administrator first and then let you join the company")
        } else{
          if(path==='/Backstage'){
              if(loginUser.identity!=1&&loginUser.identity!=2){
                this.$message.warning("You're not a manager or an administrator. You don't have access to the backstage")
              }else {
                this.$router.push(path)
              }
          }else{
            this.$router.push(path)
          }
        }
      }
    },
    handleClose(){
      this.user={};
      this.dialogVisible_login=false;
      this.msg='';
      //clear验证信息
      this.$nextTick(() => {
        this.$refs['userLoginForm'].clearValidate();
      })
    },
    handleClose_register(){
      this.newUser={};
      this.dialogVisible_register=false
      //clear验证信息
      this.$nextTick(() => {
        this.$refs['newUser'].clearValidate();
      })
    },

    logout(){
      sessionStorage.removeItem('user');
      this.loginUser={};
      this.show=false
      //刷新页面
      this.$router.go(0);
    }
  }
}
</script>

<style lang="less" scoped>

.el-input{
    height: 18px;
}

.el-form-item{
  height: 35px;
  margin-bottom: 27px;
}


/deep/ .el-form-item__label{
  font-size: 10px;

}

.el-radio /deep/ .el-radio__label {
  font-size: 10px;
  line-height: 2;
}

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
