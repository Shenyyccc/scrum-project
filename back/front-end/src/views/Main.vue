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
        <div class="panel-item" @click="to('/Manage/user')">
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
      title="提示"
      :visible.sync="dialogVisible_1"
      width="30%">

       <el-form ref="loginForm" :rules="rules" :model="form">
            <el-form-item label="用户名"  prop="username">
              <el-input type="text" placeholder="请输入用户名" v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" placeholder="请输入密码" v-model="form.password"></el-input>
            </el-form-item>
         {{msg}}

         <el-button @click="dialogVisible_1 = false">取 消</el-button>
         <el-button type="primary" @click="onSubmit('loginForm')" >登录</el-button>
       </el-form>

<!--      <span slot="footer" class="dialog-footer">-->
<!--       </span>-->
    </el-dialog>



    <!--注册    -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-tabs v-model="activeName" type="card" @tab-click="">
        <el-tab-pane label="管理员" name="first">
            <el-form>
            <el-form-item label="用户名" prop="username">
              <el-input placeholder="请输入用户名" v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input placeholder="请输入密码" v-model="form.password"></el-input>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="用户" name="second">配置管理</el-tab-pane>
      </el-tabs>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
    </el-dialog>


  </div>
</template>

<script>
import register from "../components/common/register.vue";
import {getMain} from "../api/request";

export default {
  name: "Main",
  components:{
    register
  },
  data() {
    return {
      form:{
        username:'',
        password:'',
      },
      rules:{
        username:[{required:true,message:'用户名不能为空',trigger:'blur'}],
        password:[{required:true,message:'密码不能为空',trigger:'blur'}],
      },
      msg:'',
      dialogVisible:false,
      dialogVisible_1:false,
      value: '',
      activeName:'first',
    };
  },
  methods:{
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //this.$http.get("127.0.0.1:8080/hello");
          //return true;
          console.log("======>");
          if(getMain()){
            this.$router.push('/register');
          };
        } else {
          console.log('error submit!!');
          return false;
        }
      });
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
