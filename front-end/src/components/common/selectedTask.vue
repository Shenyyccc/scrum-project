<template>
    <div style="width: 300px;margin-left: 15px;height: 200px;margin-top: 10px">
      <el-card class="box-card">
        <div class="clearfix" style="display: flex;line-height: 20px;">
          <span style="font-size: 20px;flex: 1" >
            <slot name="task_name" ></slot>
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
export default {
  name: "selectedTask",
  props:["deadline","id"],
  data(){
    return{

    }
  },
  methods:{
    hilarity() {
      console.log(this.deadline)
      this.$notify({
        title: "Tip",
        message: "Time over!",
        duration: 0,
      });
    },
    finish(){
      console.log(this.id)
      this.$emit("finishTask",this.id);
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
</style>
