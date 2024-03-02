<template>
    <el-container style="height: 100vh;">
      <el-aside width="DynamicWidth+'px'" style="background-color: rgb(48,65,86);box-shadow:2px 0 6px rgba(0 24 21/35%);height: 100%;overflow: hidden;min-width: 8vh">
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"></Aside>
      </el-aside>

      <el-container>
        <el-header style="border-bottom: 1px solid #ccc;">
          <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"></Header>
        </el-header>

        <el-main>
          <router-view/>
        </el-main>

      </el-container>
    </el-container>
</template>

<script>


// import request from "@/utils/request";
import Aside from "@/components/Aside.vue";
import Header from "@/components/Header.vue";

export default {
  components: {
    Aside,
    Header
  },

  name: "Manage",

  data() {

    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      msg: "毕业设计",
      DynamicWidth: 200,
      logoTextShow: true,
    }
  },

  methods: {
    //收缩导航栏
    collapse() {//点击收缩isCollapse=true
      this.isCollapse = !this.isCollapse
      if (this.isCollapse) {
        this.DynamicWidth = 64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {
        this.DynamicWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    }
  }
}
</script>
<style scoped>
.router-link-active{
text-decoration: none;
color: #e6ff9a;
}
</style>
<style>
a{
  text-decoration: none;
  color: white;
}
</style>
