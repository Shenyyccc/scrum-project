<template>
    <div style="display: flex">
<!--      <slot name="work_name"></slot>-->
<!--      <slot name="work_content"></slot>-->
      <el-card shadow="hover" style="width: 400px;border-radius: 20px;" :body-style="{paddingTop:'0px'}">
        <div  class="" style="display: flex;height: 30px;padding-top: 10px" >
          <div style="flex: 10;width: 100px;height: 30px;font-size: 22px;">
            <slot name="work_name" style="height: 50%;padding: 0px;" ></slot>
          </div>
          <div style="margin-right: 0px;flex: 4;display: flex;justify-content: flex-end">
            <el-button type="text" style="font-size: 13px;padding: 8px 2px;" @click="pickUp">Pick Up</el-button>
            <div v-if="manager!='3'">
              <el-divider direction="vertical"></el-divider>
              <el-button type="text" style="font-size: 13px;padding: 8px 2px;" @click="Assign">Assign</el-button>
            </div>
          </div>
<!--          <el-divider direction="vertical"></el-divider>-->
<!--          <div style="margin-right: 0px;width: 30px;flex: 2;height: 3px;padding: 0px">-->
<!--            <el-button round type="text" style="font-size: 13px;padding: 8px" @click="pickUp">Assign</el-button>-->
<!--          </div>-->
        </div>

        <div>
          <el-divider ></el-divider>
        </div>
        <div style="display: flex;height: 40px;font-size: 12px;">
          <slot name="work_content" style="margin-bottom: 18px;"></slot>
        </div>
        <div style="display: flex;height: 10px;font-size: 12px;">
          <div> expiry：<slot name="work_time" style="height: 50%;padding: 0px" ></slot>day</div>
        </div>
      </el-card>


    </div>
</template>

<script>
export default {
  name: "Work",
  props:[
    "id",
    "manager",
    "departId"
  ],
  methods:{
    pickUp(){
      this.$emit("pickUpTask",this.id);
    },
    Assign(){
      this.$emit("Assign",this.departId,this.id);
    }
  }
}
</script>

<style scoped>

/*.el-divider--horizontal是el-divider默认样式*/
.el-divider--horizontal{
  margin: 8px 0;
  border-top: 1px #e8eaec;
}

.el-divider--vertical{
  display:inline-block;
  width:1px;
  /*height:50%;*/
  margin:0 1px;
  position:relative;
}


</style>
