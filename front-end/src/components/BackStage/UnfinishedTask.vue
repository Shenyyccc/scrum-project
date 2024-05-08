<template>
  <div style="">
    <el-container>

      <div style="height: 75px">
        <div style="text-align: center;margin-top: -20px">
          <h1 v-if="user.departmentid" style="font-size: 20px">Task collection in {{this.depart}}</h1>
          <h1 v-else style="font-size: 20px">{{user.company}} Unfinished Task</h1>
        </div>
        <div style="width: 1300px;margin-bottom: 10px;height: 65px;">
          <div style="margin-bottom: 20px;margin-left: 150px;display: flex">
            <el-input v-model="input.name" style="width: 250px;margin-right: 10px" placeholder="Please input the task name"></el-input>
            <el-input v-model="input.scrib" style="width: 250px;margin-right: 10px" placeholder="Please input the task content"></el-input>
            <el-input v-model="input.time" style="width: 250px;margin-right: 10px" placeholder="Please input expiry time"></el-input>
            <el-button type="warning" style="margin-right: 5px" @click="clear">Clear</el-button>
            <el-button type="primary" style="margin-right: 15px" @click="getUnfinishedTasks">Search</el-button>
          </div>

        </div>
      </div>

      <el-main style="height: 500px;margin-top: 0px">
        <el-divider></el-divider>
        <div>
          <div v-if="groupedData.length===0" >
            <el-empty description="There is no Task can be selected"></el-empty>
          </div>
          <div v-else v-for="(group, index) in groupedData" :key="index" style="display: flex">
            <Work v-for="(work,index) in group" :key="index" style="margin-bottom: 10px;width: 400px;margin-right: 10px" v-if="index<3" @pickUpTask="pickUp" :id="work.id"
                  :manager="user.identity" @Assign="assign" :depart-id="work.departmentid">
              <div slot="work_name" style="font-family: sans-serif;">
                {{work.taskname}}
              </div>
              <div slot="work_content">
                <span style="font-weight: bold">DESCRIPTION:&nbsp;</span>
                <span style="color: #a4a4a5">{{work.taskscrib}}</span>
              </div>
              <span slot="work_time">
                {{work.expiretime}}
              </span>
            </Work>
          </div>
        </div>
      </el-main>
      <el-footer>
        <div class="block" style="text-align: center;">
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

    <el-drawer
      title="Assign Task"
      :visible.sync="dialogVisible">
      <div>
        <el-table
          :data="memberData"
          style="width: 100%"
          empty-text="No Data">
          <el-table-column
            prop="username"
            label="Username"
            width="180">
          </el-table-column>
          <el-table-column
            prop="name"
            label="Name"
            width="180">
          </el-table-column>
          <el-table-column
            label="Operation">
            <template v-slot:default="scope" style="display: flex">
              <el-popconfirm confirm-button-text='confirm' class="ml-5"
                             cancel-button-text='think again'
                             icon="el-icon-info"
                             icon-color="red"
                             title="Are you sure？"
                             @confirm="handleAssign(scope.row.id)">
                <el-button type="text" slot="reference" size="mini" icon="el-icon-circle-plus">assign</el-button>
              </el-popconfirm>

            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-drawer>


  </div>

</template>

<script>
import Work from "@/components/common/Work.vue";
import {getDepartMember, getIndividualDepart, getTasks, getUnfinishedTask, getWorks, pickUpTask} from "@/api/request";

export default {
  name: "UnfinishedTask",
  components: {Work},
  data(){
    return{
      input:{
        name:'',
        scrib:'',
        prio:'',
        time:'',
        pageSize:9,
        pageNum:1,
      },
      works:[
      ],
      depart:{},
      itemsPerGroup: 3,
      total:0,
      dialogVisible:false,
      user:JSON.parse(sessionStorage.getItem('user')),
      memberData:[],
      currentTaskId:'',

    }
  },
  computed:{
    // 计算分组后的数据
    groupedData() {
      const groups = [];
      for (let i = 0; i < this.works.length; i += this.itemsPerGroup) {
        groups.push(this.works.slice(i, i + this.itemsPerGroup));
      }
      console.log(groups)
      return groups;
    },
  },
  mounted() {
    this.getDepart();
    this.getUnfinishedTasks();
  },
  methods:{
    getUnfinishedTasks(){
      if(this.user.departmentid===null){
        this.input.departmentid=''
      }else {
        this.input.departmentid=this.user.departmentid;
      }
      this.input.companyid=this.user.companyid
      console.log(this.input)
      getUnfinishedTask(this.input).then(rsp=>{
        console.log(rsp.data.data)
        this.works=rsp.data.data.list;
        this.total=rsp.data.data.total;
        this.$emit("num",this.total)
        if(this.works.length===0){
          this.$message.info("There is no task for you to select!")
        }
        // console.log(rsp.data.data.total)
      })

    },
    clear(){
      this.input.name='';
      this.input.scrib='';
      this.input.time='';
    },
    handleCurrentChange(){
      this.getUnfinishedTasks()
    },
    getDepart(){
      getIndividualDepart(this.user.departmentid).then(rsp=>{
        if(rsp.data.data!=null){
          this.depart=rsp.data.data.name
        }
      })
    },
    pickUp(data){
      let userid=this.user.userId;
      let taskid=data;
      let params={userid,taskid};
      pickUpTask(params).then(rsp=>{
        console.log(rsp.data.data)
        this.getUnfinishedTasks();
      })
    },
    assign(param1,param2){
      this.dialogVisible=true;
      console.log(param1,param2)
      this.currentTaskId=param2
      getDepartMember({departId:param1,input:''}).then(rsp=>{
        console.log(rsp.data.data)
        this.memberData=rsp.data.data;
      })
    },
    handleAssign(param){
      console.log(param)
      pickUpTask({userid:param,taskid:this.currentTaskId}).then(rsp=>{
        console.log(rsp.data.data)
        this.$message.success("Assign successfully")
        this.getUnfinishedTasks();
      })
    }

  },
}
</script>

<style scoped>


</style>
