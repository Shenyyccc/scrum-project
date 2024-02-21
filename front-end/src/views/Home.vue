<template>
    <el-container style="height: 100vh;">
      <el-aside width="DynamicWidth+'px'" style="background-color: rgb(48,65,86);box-shadow:2px 0 6px rgba(0 24 21/35%);height: 100%;overflow: hidden;min-width: 25px">
        <el-menu style="outline:none;height: 100%;overflow-x: hidden" :default-openeds="['1', '3']"
                 background-color="rgb(48,65,86)"
                 text-color="#fff"
                 active-text-color="#e6ff9a"
                 :collapse-transition="false"
                 :collapse="isCollapse"
        >
          <div style="height: 60px;line-height: 60px;text-align: center">
            <img src="../assets/ye.jpg" alt=""
                 style="width: 20px;position: relative;top:5px;margin-right: 5px">
            <b style="color: white;" v-show="logoTextShow">后台管理系统</b>
          </div>

          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-s-platform"></i>
              <span slot="title">导航一</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="1-1" text-color="#fff">
                <router-link to="/Home/Panel">面板</router-link>
              </el-menu-item>
              <el-menu-item index="1-2">
                <router-link to="/Home/WorkList">用户列表</router-link>
              </el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="1-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-message"></i>
              <span slot="title">导航二</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="2-1">选项1</el-menu-item>
              <el-menu-item index="2-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="2-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span slot="title">导航三</span>
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="3-1">选项1</el-menu-item>
              <el-menu-item index="3-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="3-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="font-size: 12px;border-bottom: 1px solid #ccc;line-height: 60px;display: flex">
          <div style="flex: 1;font-size: 18px">
            <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse"></span>
          </div>
          <el-dropdown style="width: 70px;cursor: pointer">
<!--            <i class="el-icon-setting" style="margin-right: 15px"></i>-->
            <span>Admin</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            <el-dropdown-menu slot="dropdown" style="text-align: center">
              <el-dropdown-item>个人信息</el-dropdown-item>
              <el-dropdown-item>《待定》</el-dropdown-item>
              <el-dropdown-item>退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>

        <el-main>
          <div style="margin-bottom: 30px">
            <el-breadcrumb separator-class="el-icon-arrow-right">
              <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>用户管理</el-breadcrumb-item>
<!--              <el-breadcrumb-item>活动列表</el-breadcrumb-item>-->
<!--              <el-breadcrumb-item>活动详情</el-breadcrumb-item>-->
            </el-breadcrumb>
          </div>
          <div style="margin: 10px 0">
              <el-input type="text" suffix-icon="el-icon-search" style="width: 200px" placeholder="请输入内容"></el-input><el-button class="ml-5" type="primary">搜索</el-button>
<!--            <el-input type="text" suffix-icon="el-icon-search" style="width: 200px" placeholder="请输入内容"></el-input><el-button class="ml-5" type="primary">搜索</el-button>-->
<!--            <el-input type="text" suffix-icon="el-icon-search" style="width: 200px" placeholder="请输入内容"></el-input><el-button class="ml-5" type="primary">搜索</el-button>-->
          </div>
          <div style="margin: 10px 0">
            <el-button type="primary">添加 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="danger">删除 <i class="el-icon-remove-outline"></i></el-button>
            <el-button type="info">导入 <i class="el-icon-upload2"></i></el-button>
            <el-button type="warning">导出 <i class="el-icon-download"></i></el-button>
          </div>
          <el-table :data="tableData" :header-cell-style="{background:'#eef1f6',color:'#606266'}" >
            <el-table-column prop="date" label="日期" width="140">
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="120">
            </el-table-column>
            <el-table-column prop="address" label="地址">
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                  <el-button type="success">编辑 <i class="el-icon-edit"></i></el-button>
                  <el-button type="danger">删除 <i class="el-icon-circle-close"></i></el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="pd-10">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage4"
              :page-sizes="[10, 20, 30, 40]"
              :page-size="10"
              layout="total, sizes, prev, pager, next, jumper"
              :total="40">
            </el-pagination>
          </div>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>

</template>

<script>


export default {
  methods: {
    collapse() {//点击收缩isCollapse=true
      this.isCollapse=!this.isCollapse
      if(this.isCollapse){
        this.DynamicWidth= 64,
        this.collapseBtnClass='el-icon-s-unfold',
        this.logoTextShow=false
      }else{
        this.DynamicWidth= 200,
        this.collapseBtnClass='el-icon-s-fold',
        this.logoTextShow=true
      }
    }
  },
  data(){
    const item = {
      date: '2016-05-02',
      name: 'dsadsadas',
      address: 'asdasdasdasd 1518 弄'
    };
    return {
      tableData: Array(10).fill(item),
      msg:"毕业设计",
      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      DynamicWidth:200,
      logoTextShow:true
    }
  },
  name: "Home"
}
</script>

<style scoped>
.router-link-active{
  text-decoration: none;
  color: #e6ff9a;
}
a{
  text-decoration: none;
  color: white;
}
</style>
