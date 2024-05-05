<template>
  <div>
    <el-container>
      <el-header>
        <div style="display: flex;margin-left: 400px">
          <el-input  style="margin-right: 15px;width: 300px" placeholder="Please input the definition name" v-model="input.search"></el-input>
          <el-button type="warning" style="margin-right: 5px;" icon="el-icon-search" @click="getRunningWorkflow">Search</el-button>
        </div>
      </el-header>

      <el-main style=";margin-bottom: 0px">
        <div style="">
          <el-table :data="runningWorkflow" header-cell-class-name="headerBg" :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                    selection-change="handleSelectionChange" empty-text="No Data"  style="width: 1000px;margin: 0 130px" height="468" align="center">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column prop="name" label="Definition Name" width="200" >
            </el-table-column>
            <el-table-column prop="createtime" label="Create Time" width="300">
            </el-table-column>
            <el-table-column prop="progress" label="Progress" width="200" >
            </el-table-column>

            <el-table-column label="Operation">
              <template v-slot:default="scope" style="display: flex">
                <el-button type="text" @click="preview(scope.row)" size="mini" icon="el-icon-video-play" style="margin-right: 10px">preview</el-button>
                <!--              <el-button type="text" @click="handleEdit(scope.row)" size="mini" icon="el-icon-edit" style="margin-right: 10px">edit</el-button>-->
                <el-popconfirm confirm-button-text='confirm' class="ml-5"
                               cancel-button-text='think again'
                               icon="el-icon-info"
                               icon-color="red"
                               title="Are you sure to delete it？"
                               @confirm="handleDelete(scope.$index)">
                  <el-button type="text" slot="reference" size="mini" icon="el-icon-delete" style="color:red">delete</el-button>
                </el-popconfirm>

              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-main>
    </el-container>
    <el-footer style="">
      <el-pagination
        @size-change=""
        @current-change="handleCurrentChange"
        :current-page.sync="input.pageNum"
        :page-size="input.pageSize"
        layout="total, prev, pager, next"
        :total="total"
        style="margin-left: 480px">
      </el-pagination>
    </el-footer>
    <div>
      <el-dialog
        title="The progress of Workflow"
        :visible.sync="dialogVisible"
        width="800px"
        center>
        <TaskflowGraph :taskId="currentTaskflowId" ref="taskflowGraph"></TaskflowGraph>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {getRunningWorkflow} from "@/api/request";
import TaskflowGraph from "@/components/BackStage/taskflowGraph.vue";

export default {
  name: "RunningTaskflow",
  components: {TaskflowGraph},
  data(){
    return{
      input:{
        search:'',
        pageSize:8,
        pageNum:1,
        companyid:JSON.parse(sessionStorage.getItem('user')).companyid
      },
      total:0,
      runningWorkflow:[],
      dialogVisible:false,
      currentTaskflowId:'',
    }
  },
  methods:{
    getRunningWorkflow(){
      getRunningWorkflow(this.input).then(rsp=>{
          console.log(rsp.data.data)
          this.runningWorkflow=rsp.data.data.data
          this.total=rsp.data.data.total
      })
    },
    handleCurrentChange(){
      this.getRunningWorkflow()
    },
    preview(param){
      this.currentTaskflowId=param.id
      this.dialogVisible=true
      this.$nextTick(() => {
        this.$refs.taskflowGraph.initGraph() // 调用子组件的方法
        this.$refs.taskflowGraph.fetchDataAndDraw();
      });
    },
  },
  mounted() {
    this.getRunningWorkflow()
  }
}
</script>

<style scoped>
/deep/ .el-dialog--center .el-dialog__body{
  padding: 0 0;
}
</style>
