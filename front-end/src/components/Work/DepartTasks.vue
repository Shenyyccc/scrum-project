<template>
  <div>
    <el-container>
      <el-header style="width: 1300px;margin-bottom: 10px">
        <h1>某某部门任务领取处</h1>
        <div style="margin-bottom: 20px;margin-right: 60px;text-align: right">
          <el-input v-model="input.name" style="width: 250px;margin-right: 5px" placeholder="请输入查询任务"></el-input>
          <el-input v-model="input.scrib" style="width: 250px;margin-right: 5px" placeholder="请输入查询任务内容"></el-input>
          <el-input v-model="input.prio" style="width: 250px;margin-right: 5px" placeholder="请输入查询优先级"></el-input>
          <el-input v-model="input.time" style="width: 250px;margin-right: 5px" placeholder="请输入期限时间"></el-input>
          <el-button type="warning" style="margin-right: 5px">查询</el-button>
          <el-button type="primary" style="margin-right: 15px">清空</el-button>
        </div>
      </el-header>
      <el-main style="height: 520px">
        <el-divider></el-divider>

      </el-main>
      <el-footer>
        <div class="block">
          <el-pagination
            @size-change=""
            @current-change="handleCurrentChange"
            :current-page.sync="input.pageNum"
            :page-size="input.pageSize"
            layout="total, prev, pager, next"
            :total="total">
          </el-pagination>
        </div>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "DepartTasks"
}
</script>

<style scoped>

</style>
