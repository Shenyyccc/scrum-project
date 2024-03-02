<template>
  <div style="line-height: 60px;display: flex">
  <div style="flex: 1;font-size: 22px">
    <span :class="collapseBtnClass" style="cursor: pointer;font-size: 18px" @click="collapse"></span>

    <el-breadcrumb separator-class="el-icon-arrow-right" separator="/" style="display: inline-block; margin-left: 10px">
      <el-breadcrumb-item :to="'/'" >首页</el-breadcrumb-item>
      <el-breadcrumb-item >首页</el-breadcrumb-item>
<!--      <el-breadcrumb-item :to="{path:'/manage/user'}">用户管理</el-breadcrumb-item>-->
      <!--              <el-breadcrumb-item>活动列表</el-breadcrumb-item>-->
      <!--              <el-breadcrumb-item>活动详情</el-breadcrumb-item>-->
    </el-breadcrumb>

  </div>
  <el-dropdown style="width: 70px;cursor: pointer">
    <span>Admin</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
    <el-dropdown-menu slot="dropdown" style="text-align: center">
      <el-dropdown-item>个人信息</el-dropdown-item>
      <el-dropdown-item>《待定》</el-dropdown-item>
      <el-dropdown-item>退出</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass:String,
    collapse:Function
  },
  data(){
    return{
      paths:[]
    }
  },


}
</script>

<style scoped>

</style>
