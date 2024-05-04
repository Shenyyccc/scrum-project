<template>
  <el-container class="home-container">

    <!--头部  -->
    <el-header style="">
      <div style="">
        <img src="" alt="">
        <span>{{this.loginUser.company}}  BackStage</span>
      </div>
      <!-- 退出按钮 -->
      <div  style=" display: flex;margin-left: 620px">
        <span v-if="loginUser.avatar!=null"> <el-avatar :src="'http://localhost:8080/files/'+loginUser.avatar" size="large"></el-avatar></span>
        <span v-else><el-avatar>{{loginUser.name.charAt(0)}}</el-avatar></span>
        <el-dropdown>
          <span style="color:white;margin: 20px ">
            {{loginUser.name}}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item >
              <router-link to="/Backstage/ModifyPass" style="text-decoration: none;color: black">Modify Password</router-link>
            </el-dropdown-item>
            <el-dropdown-item >
              <router-link to="/" style="text-decoration: none;color: red">Exit</router-link>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
<!--        <span style="line-height: 40px;margin-left: 10px">{{loginUser.name}}</span>-->
<!--        <el-button size="small" type="info" style="margin-left: 20px" @click="logout" icon="el-icon-back" >BACK</el-button>-->
      </div>
    </el-header>


    <!-- 页面主体区  嵌套容器  包裹 Aside与Main -->
    <el-container>
      <!-- 左侧 -->
      <el-aside width="200px">
        <el-menu
          router
          :default-active="$route.path"
          style="overflow-x: hidden;height: 100%"
          class="el-menu-vertical-demo"
          @open=""
          @close=""
          background-color="#373D41"
          text-color="#fff"
          active-text-color="#ffd04b">

          <el-menu-item index="/Backstage/CreateDepartment">
            <i class="el-icon-menu"></i>
            <span slot="title">Create Department</span>
          </el-menu-item>

          <el-menu-item index="/Backstage/FormDesign">
            <i class="el-icon-setting"></i>
            <span slot="title">Process Definition</span>
          </el-menu-item>

          <el-menu-item index="/Backstage/WorkflowTemplate">
            <i class="el-icon-document"></i>
            <span>Workflow Template</span>
          </el-menu-item>

          <el-menu-item index="/Backstage/PersonInformation">
            <i class="el-icon-user"></i>
            <span>Person Information</span>
          </el-menu-item>

          <el-menu-item index="/Backstage/workflowGraph">
            <i class="el-icon-user"></i>
            <span>workflowGraph</span>
          </el-menu-item>

        </el-menu>


      </el-aside>
      <!-- 主体 -->
      <el-main> <router-view @updateInfo="updateUser"/> </el-main>
    </el-container>


  </el-container>
</template>

<script >

export default{
  name:'Backstage',

  data(){
    return{
      loginUser:JSON.parse(sessionStorage.getItem('user')),
    }
  },

  methods:{
    updateUser(param){
      console.log(param)
      this.loginUser=JSON.parse(JSON.stringify(param))
    },


    // 退出操作
    logout(){
      this.$router.push('/')
    },
  },

}
</script>


<style scoped lang="less">
// 标签的名字就是类的名字
.home-container{
  height: 100%;
}
.el-header{
  background-color: #373D41;
  display: flex;
  justify-content: space-between;
  padding-left: 0px;
  //  居中
  align-items:center;
  //  文本颜色
  color:#fff;
  // 文字大小
  font-size: 20px;

  // 给header中嵌套的div进行样式改造
  // 子代选择器
  >div{
    display:flex;
    //   纵向居中
    align-items:center;

    //   在给div中的span改造样式
    >span{
      margin-left: 20px;
    }
  }
}

.el-aside{
  background-color: #333744;
}

.el-main{
  background-color: #EAEDF1;
}



</style>
