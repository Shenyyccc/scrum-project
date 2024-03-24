<template>
    <div style="display: flex">
<!--      <slot name="work_name"></slot>-->
<!--      <slot name="work_content"></slot>-->
      <el-card shadow="hover" style="width: 400px;border-radius: 20px;" :body-style="{paddingTop:'0px'}">

        <div  class="" style="display: flex;height: 30px;padding-top: 10px" >
          <div style="flex: 10;width: 300px;height: 30px;font-size: 22px;">
            <slot name="work_name" style="height: 50%;padding: 0px" ></slot>
          </div>
          <div style="margin-right: 0px;width: 30px;flex: 2;height: 3px;padding: 0px">
            <el-button round type="primary"  style="font-size: 13px;padding: 8px" @click="">领取任务</el-button>
          </div>
        </div>

        <div>
          <el-divider ></el-divider>
        </div>
        <div style="display: flex;height: 40px">
          <slot name="work_content" style="font-size: 14px;margin-bottom: 18px;text-align: left"></slot>
        </div>
        <div style="display: flex;height: 10px;font-size: 12px;">
          <div> 期限：7天</div>
        </div>
      </el-card>

    </div>
</template>

<script>
export default {
  name: "Work",
}
</script>

<style scoped>

/*.el-divider--horizontal是el-divider默认样式*/
.el-divider--horizontal{
  margin: 8px 0;
  border-top: 1px #e8eaec;
}

</style>
