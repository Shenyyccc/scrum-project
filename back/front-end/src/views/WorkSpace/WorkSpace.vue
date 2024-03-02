<template>
  <div>
  <el-container class="home-container" style="height: 100%; border:1px ">

    <!--头部  -->
    <el-header>
      <div>
        <img src="" alt="">
        <span>后台管理系统</span>
      </div>
      <!-- 退出按钮 -->
      <el-button type="info" @click="logout()">退出</el-button>
    </el-header>


    <!-- 页面主体区  嵌套容器  包裹 Aside与Main -->
    <el-container >
      <!-- 左侧 -->
      <el-aside width="200px">
        <el-menu
          default-active="1-1"
          class="el-menu-vertical-demo"
          @open=""
          @close=""
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1">
                <router-link to="/WorkSpace/WorkList">用户列表</router-link>
              </el-menu-item>
              <el-menu-item index="1-2">
                <router-link to="/WorkSpace/Panel">面板</router-link>
              </el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-menu-item index="2">
            <i class="el-icon-menu"></i>
            <span slot="title">导航二</span>
          </el-menu-item>
          <el-menu-item index="3" disabled>
            <i class="el-icon-document"></i>
            <span slot="title">导航三</span>
          </el-menu-item>
          <el-menu-item index="4">
            <i class="el-icon-setting"></i>
            <span slot="title">导航四</span>
          </el-menu-item>
        </el-menu>

      </el-aside>
      <!-- 主体 -->
      <el-main> <router-view/> </el-main>
    </el-container>


  </el-container>
  </div>
</template>

<script >

export default{
  name:'WorkSpace',

  methods:{
    // 退出操作
    logout(){
      // 清空token
      window.sessionStorage.clear()
      //  将登录页面转入到登录界面
      this.$router.push('/')
    }
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
