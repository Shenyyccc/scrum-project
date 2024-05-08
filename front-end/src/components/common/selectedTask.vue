<template>
    <div style="width: 300px;margin-left: 15px;height: 200px;margin-top: 10px">
      <el-card class="box-card">
        <div class="clearfix" style="display: flex;line-height: 20px;">
          <span style="font-size: 20px;flex: 1" >
            <slot name="task_name" ref="taskName"></slot>
          </span>
          <span style="margin-left: 40px;flex: 1">
            <el-button style="float: right; padding: 3px 0" type="text" @click="finish">Finish</el-button>
          </span>

        </div>
        <div class="text item" style="margin-top: 10px">
          <slot name="task_content"></slot>
        </div>
        <div style="display: flex">
          <el-statistic
            ref="statistic"
            @finish="hilarity"
            format="DD:HH:mm:ss"
            :value="deadline"
            :value-style="{fontSize:'10px'}"
            title="Reminder:"
            time-indices
          >
          </el-statistic>
        </div>
      </el-card>

    </div>
</template>

<script>
import {unfinishedTask} from "@/api/request";

export default {
  name: "selectedTask",
  props:["deadline","id","taskName"],
  data(){
    return{

    }
  },
  methods:{
    hilarity() {
      console.log(this.deadline)
      this.$notify({
        title: "Tip",
        message: "Time over! You dont finish "+this.taskName+"  in time....",
        duration: 0,
        type:"warning"
      });
      this.changeProgress()
      //因为Progress发生变化，需要重新获取progress
      this.$emit("unfinishedTask")
    },
    finish(){
      console.log(this.id)
      this.$emit("finishTask",this.id);
    },
    changeProgress(){
        unfinishedTask(this.id).then(rsp=>{
          console.log("changeProgress",rsp.data.data)
        })
    }
  }

}
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both
}

.box-card {
  width: 300px;
}

.el-statistic {
  display: flex;
}
</style>
