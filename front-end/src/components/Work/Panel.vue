<template>
  <div>
    <div style="text-align: center;font-size: 20px">
      <h1 style="align-content: center">Panel</h1>
      <el-divider></el-divider>
    </div>

    <div v-if="groupedData.length===0" style="text-align: center">
      <div style="display: block">
        <el-empty description="You need to go to Task Pickup to pick up task....." ></el-empty>
      </div>
    </div>
    <div class="container" v-else>
      <div style="">
        <div v-for="(group,index) in groupedData" :key="index" style="display: flex;height: 160px;margin-left: 5px">
            <selected-task v-for="(task,index) in group" :key="index" :deadline="task.deadline"  style="margin-left: 10px" :id="task.id"
                           :task-name="task.taskname" @finishTask="finish" @unfinishedTask="getRuningTask">
              <div slot="task_name">
                {{task.taskname}}
              </div>
              <div slot="task_content">
                {{task.taskscrib}}
              </div>
            </selected-task>
        </div>
      </div>
    </div>

  </div>


</template>

<script>
//导入draggable组件
import draggable from 'vuedraggable'
import {getUnstart, getStarting, getFinished, ChangWorkList, getRunningTask, finishTask} from "@/api/request";
import Work from "@/components/common/Work.vue";
import selectedTask from "@/components/common/selectedTask.vue";
export default {
  //注册draggable组件
  components: {
    Work,
    draggable,
    selectedTask,
  },
  data() {
    return {
      // drag:false,
      // //定义要被拖拽对象的数组
      // deadline: Date.now() + (new Date().setHours(23, 59, 59) - Date.now()),
      // myArray:[],
      // myArray1:[],
      // myArray2:[],
      Task:[],
      itemsPerGroup: 4,
    };
  },
  computed:{
    groupedData() {
      const groups = [];
      for (let i = 0; i < this.Task.length; i += this.itemsPerGroup) {
        groups.push(this.Task.slice(i, i + this.itemsPerGroup));
      }
      console.log(groups)
      return groups;
    },
  },
  mounted() {
    this.getRuningTask();

  },
  methods: {
    getRuningTask(){
      getRunningTask(JSON.parse(sessionStorage.getItem("user")).userId).then(rsp=>{
        this.Task=rsp.data.data;
        for(let i=0;i<this.Task.length;i++){
          // this.Task[i].deadline= Date.now() + (new Date(this.Task[i].endtime)- Date.now())
          this.Task[i].deadline= new Date(this.Task[i].endtime)
        }
        console.log(this.Task)
      })
    },
    finish(data){
      console.log(data)
      finishTask(data).then(rsp=>{
        this.$message.success("finish this task successfully!");
        this.getRuningTask();
      })
    },

    //拖拽结束事件
    onEnd(evt) {

      this.drag=false;
      // 获取元素原来的索引
      const oldIndex = evt.oldIndex;
      // 获取元素新的索引
      const newIndex = evt.newIndex;
      // 获取元素所在的组别
      const fromList = evt.from.getAttribute('id');
      // 获取元素被拖拽到的组别
      const toList = evt.to.getAttribute('id');
      //获取元素绑定的Id
      const workid=evt.item.getAttribute('workId')

      const param={
        paramId:workid,
        paramProcessId:toList,
      }
      ChangWorkList(param);
      console.log(`workList:${workid}`);
      console.log(`Old index: ${oldIndex}`);
      console.log(`New index: ${newIndex}`);
      console.log(`From list: ${fromList}`);
      console.log(`To list: ${toList}`);
    },
  },

};

</script>


<style scoped>

.container{
  display: flex;
}

/*被拖拽对象的样式*/
.item {
  padding: 6px;
  background-color: #fdfdfd;
  border: solid 1px #eee;
  margin-bottom: 10px;
  cursor: move;
}
/*选中样式*/
.chosen {
  border: solid 2px #3089dc !important;
  color: red;
}

.draggable-container{
  width: 400px;
  height: 500px;
  margin: 30px;
  background: #a4a4a5;
  border-radius: 30px;
}

transition div{
  margin: 20px;
}
</style>
