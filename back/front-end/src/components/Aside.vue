<template>
   <el-menu style="outline:none;height: 100%;overflow-x: hidden" :default-openeds="['1', '3']"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#e6ff9a"
           :collapse-transition="false"
           :collapse="isCollapse"
            router
  >
    <div style="height: 60px;line-height: 60px;text-align: center">
      <img src="../assets/ye.jpg" alt=""
           style="width: 20px;position: relative;top:5px;margin-right: 5px">
      <b style="color: white;" v-show="logoTextShow">后台管理系统</b>
    </div>
    <el-menu-item index="/">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span slot="title">主页</span>
      </template>

    </el-menu-item>
    <el-submenu index="1" >
<!--      <el-menu-item index="1-3">主页</el-menu-item>-->

      <template slot="title">
        <i class="el-icon-s-platform"></i>
        <span slot="title">用户模块</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/Home/Panel" text-color="#fff">
          <span slot="title">操作面板</span>
        </el-menu-item>
        <el-menu-item index="/Home/WorkList">
          <span slot="title">用户列表</span>
        </el-menu-item>
      </el-menu-item-group>

      <el-menu-item index="/Manage/user">
        <span slot="title">用户管理</span>
      </el-menu-item>


<!--      <el-submenu index="1-4">-->
<!--        <template slot="title">选项4</template>-->
<!--        <el-menu-item index="1-4-1">选项4-1</el-menu-item>-->
<!--      </el-submenu>-->
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-message"></i>
        <span slot="title">系统管理</span>
      </template>
      <el-menu-item-group>
        <template slot="title">分组一</template>
        <el-menu-item index="2-1">选项1</el-menu-item>
        <el-menu-item index="2-2">选项2</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="分组2">
        <el-menu-item index="2-3">选项3</el-menu-item>
      </el-menu-item-group>
<!--      <el-submenu index="2-4">-->
<!--        <template slot="title">选项4</template>-->
<!--        <el-menu-item index="2-4-1">选项4-1</el-menu-item>-->
<!--      </el-submenu>-->
    </el-submenu>
<!--    <el-submenu index="3">-->
<!--      <template slot="title">-->
<!--        <i class="el-icon-menu"></i>-->
<!--        <span slot="title">导航三</span>-->
<!--      </template>-->
<!--      <el-menu-item-group>-->
<!--        <template slot="title">分组一</template>-->
<!--        <el-menu-item index="3-1">选项1</el-menu-item>-->
<!--        <el-menu-item index="3-2">选项2</el-menu-item>-->
<!--      </el-menu-item-group>-->
<!--      <el-menu-item-group title="分组2">-->
<!--        &lt;!&ndash;              <el-menu-item index="3-3">选项3</el-menu-item>&ndash;&gt;-->
<!--      </el-menu-item-group>-->
<!--      <el-submenu index="3-4">-->
<!--        <template slot="title">选项4</template>-->
<!--        <el-menu-item index="3-4-1">选项4-1</el-menu-item>-->
<!--      </el-submenu>-->
<!--    </el-submenu>-->
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse:Boolean,
    logoTextShow:Boolean
  }
}
</script>

<style scoped>

</style>
