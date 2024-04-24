<template>
  <el-container class="home-container">

    <!--头部  -->
    <el-header>
      <div style="">
        <img src="" alt="">
        <span>{{this.loginUser.company}}  BackStage</span>
      </div>
      <!-- 退出按钮 -->
      <div  style=" display: flex;margin-left: 620px">
        <span v-if="loginUser.avatar!=null"> <el-avatar :src="'http://localhost:8080/files/'+loginUser.avatar" size="large"></el-avatar></span>
        <span v-else><el-avatar>{{loginUser.name.charAt(0)}}</el-avatar></span>
        <span style="line-height: 40px;margin-left: 10px">{{loginUser.name}}</span>
        <el-button size="small" type="info" style="margin-left: 20px" @click="logout" icon="el-icon-back" >BACK</el-button>
      </div>
    </el-header>


    <!-- 页面主体区  嵌套容器  包裹 Aside与Main -->
    <el-container>
      <!-- 左侧 -->
      <el-aside width="200px">
        <el-menu
          style="overflow-x: hidden;height: 100%"
          class="el-menu-vertical-demo"
          @open=""
          @close=""
          @select="handleSelect"
          background-color="#373D41"
          text-color="#fff"
          active-text-color="#ffd04b">

            <el-menu-item index="1">
              <i class="el-icon-menu"></i>
              <span>Task Pickup</span>
            </el-menu-item>

            <el-menu-item index="2">
              <i class="el-icon-odometer"></i>
              <span>Panel</span>

            </el-menu-item>

<!--          <el-submenu index="1">-->
<!--            <template slot="title">-->
<!--              <i class="el-icon-location"></i>-->
<!--              <span style="font-size: 20px">Panel</span>-->
<!--            </template>-->
<!--            <el-menu-item-group>-->
<!--              <template slot="title">first</template>-->
<!--              <el-menu-item index="1-1">-->
<!--                <router-link to="/WorkSpace/WorkPickup" style="color:#fff;text-decoration: none;">任务认领</router-link>-->
<!--              </el-menu-item>-->
<!--              <el-menu-item index="1-2">-->
<!--                <router-link to="/WorkSpace/WorkList" style="color:#fff;text-decoration: none;">用户列表</router-link>-->
<!--              </el-menu-item>-->
<!--              <el-menu-item index="1-3">-->
<!--                <router-link to="/WorkSpace/Panel" style="color:#fff;text-decoration: none">面板</router-link>-->
<!--              </el-menu-item>-->
<!--            </el-menu-item-group>-->
<!--            <el-menu-item-group title="second">-->
<!--              <el-menu-item index="1-3">选项3</el-menu-item>-->
<!--            </el-menu-item-group>-->
<!--            <el-submenu index="1-4">-->
<!--              <template slot="title">选项4</template>-->
<!--              <el-menu-item index="1-4-1">选项1</el-menu-item>-->
<!--            </el-submenu>-->
<!--          </el-submenu>-->


<!--          <el-menu-item index="2">-->
<!--            <i class="el-icon-menu"></i>-->
<!--            <router-link to="" style="color:#fff;text-decoration: none" >面板</router-link>-->
<!--          </el-menu-item>-->

<!--          <el-menu-item index="3">-->
<!--            <i class="el-icon-document"></i>-->
<!--            <router-link to="/WorkSpace/PersonInformation" style="color:#fff;text-decoration: none" >Person Information</router-link>-->
<!--          </el-menu-item>-->

<!--          <el-menu-item index="4">-->
<!--            <i class="el-icon-setting"></i>-->
<!--            <span slot="title">-->
<!--              <router-link to="/WorkSpace/FormDesign" style="color:#fff;text-decoration: none" >表单设计</router-link>-->
<!--            </span>-->
<!--          </el-menu-item>-->

        </el-menu>




      </el-aside>
      <!-- 主体 -->
      <el-main> <router-view/> </el-main>
    </el-container>


  </el-container>
</template>

<script >

export default{
  name:'WorkSpace',

  data(){
    return{
      loginUser:JSON.parse(sessionStorage.getItem('user')),
    }
  },

  methods:{
    // 退出操作
    logout(){
      //  将登录页面转入到登录界面
      this.$router.push('/')
    },
    handleSelect(index) {
      switch(index) {
        case '1':
          this.$router.push('/WorkSpace/WorkPickup');
          break;
        case '2':
          this.$router.push('/WorkSpace/Panel');
          break;
        case '3':
          this.$router.push('/Backstage/PersonInformation');
          break
        case '4':
          this.$router.push('/Backstage/WorkflowTemplate')
          break
        default:
          console.warn('Unknown index');
      }

    }
  },
  mounted() {
      // this.handleSelect("1");
  }
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
