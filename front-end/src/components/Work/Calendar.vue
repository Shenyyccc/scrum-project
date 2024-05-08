<template>
  <div style="height: 100%;width: 100%;padding: -20px">
    <el-calendar>
      <template
        slot="dateCell"
        slot-scope="{date, data}">
        {{ data.day.split('-').slice(1).join('-') }}
<!--        <div>未完成任务</div>-->
        <div v-for="(task,index) in Task" :key="index">
          <div v-if="new Date(task.deadline).setHours(0, 0, 0, 0)-new Date(date).setHours(0, 0, 0, 0)===0">
            <el-popconfirm confirm-button-text='yes' class="ml-5"
                           cancel-button-text='No'
                           title="Do you finish this task？"
                           hide-icon
                           @confirm="handleDelete(scope.$index)">
              <el-button type="text" slot="reference" size="mini" style="color:darkgreen">{{ task.taskname }}</el-button>
            </el-popconfirm>
          </div>
        </div>
      </template>
    </el-calendar>
  </div>
</template>

<script>
import {getRunningTask} from "@/api/request";

export default {
  name: "Calendar",
  data() {
    return {
      Task:[],
    };
  },
  methods: {
    text(data1,data2){
      console.log(data1,data2)
    },
    getRunningTask(){
      getRunningTask(JSON.parse(sessionStorage.getItem("user")).userId).then(rsp=>{
        this.Task=rsp.data.data;
        for(let i=0;i<this.Task.length;i++){
          // this.Task[i].deadline= Date.now() + (new Date(this.Task[i].endtime)- Date.now())
          this.Task[i].deadline= new Date(this.Task[i].endtime)
        }
        console.log(this.Task)
      })
    }
  },
  mounted() {
    this.getRunningTask()
  }
}
</script>

<style scoped>

</style>
