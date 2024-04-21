<template>
  <div style="background: white;margin-top: -20px">
    <div style="text-align: center">
      <div><h1 style="font-size: 20px;height: 40px;line-height: 60px;">Create WorkFlow Definition</h1></div>
<!--      <div>Define a new workflow template</div>-->
    </div>
    <el-divider></el-divider>
    <div style="margin-top: 20px">
        <div style="text-align: center">
          <span style="">Definition Name: </span>
          <el-input v-model="processname" placeholder="Please input Definition Name" style="width: 250px"></el-input>
        </div>

        <div style="margin-left: 501px;margin-top: 10px">
          <span >
            Category:
          </span>
          <el-select v-model="category" placeholder="Please select" clearable style="width: 250px">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </div>

        <div style="display: flex;height: 70px;line-height: 35px">
          <div style="margin-left: 510px">
            <h1>Workflow Definition Element</h1>
          </div>
          <div style="margin-left: 40px;line-height: 70px">
            <el-button @click="dialogVisible=true" size="medium" round>
              <i class="el-icon-circle-plus"></i>
              <span>Add</span>
            </el-button>
          </div>
        </div>

          <div style="margin:0 20px">
            <el-table  :data="tableData" header-cell-class-name="headerBg" :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                       selection-change=""  empty-text="No Data" height="245px">
              <el-table-column type="selection" width="55" align="center">
              </el-table-column>
              <el-table-column prop="taskname" label="Task Name" width="290" align="center">
              </el-table-column>
              <el-table-column prop="taskscrib" label="Task Content" width="400" align="center">
              </el-table-column>
              <el-table-column prop="expiretime" label="Task expiry(day)" width="250" align="center">
              </el-table-column>

              <el-table-column label="Operation" align="center">
                <template v-slot:default="scope">
                  <el-button type="text" slot="reference"  @click="addTemplate(scope.row)" size="20" icon="el-icon-circle-plus">add</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <div style="text-align: center">
            <h1>Workflow Definition</h1>
          </div>

          <div style="margin:0 20px">
            <el-table  :data="workflowData" header-cell-class-name="headerBg" :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                      selection-change=""  empty-text="No Data" height="250px">
              <el-table-column type="selection" width="55" align="center">
              </el-table-column>
              <el-table-column prop="taskname" label="Task Name" width="300" align="center">
              </el-table-column>
              <el-table-column prop="taskscrib" label="Task Content" width="300" align="center">
              </el-table-column>
              <el-table-column prop="expiretime" label="Task expiry(day)" width="220" align="center">
              </el-table-column>

              <el-table-column label="Operation" align="center">
                <template v-slot:default="scope">
                  <el-button type="text" slot="reference"  @click="moveUp(scope.$index)" size="mini" icon="el-icon-top">move up</el-button>&nbsp;&nbsp;
                  <el-button type="text" slot="reference"  @click="setPreCondition(scope)" size="mini" icon="el-icon-more">set pre-task</el-button>&nbsp;&nbsp;
                  <el-button type="text" slot="reference"  @click="deleteElement(scope.$index)" size="mini" icon="el-icon-delete" style="color: red;">delete</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>


      <div style="height: 100px;line-height: 100px;text-align: center">
        <el-button @click="lastPage" type="primary" style="width: 140px">
          Back
        </el-button>
        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <el-button @click="uploadDefinition" type="success" style="width: 140px;">
          Create Definition
        </el-button>
      </div>

    </div>


    <div style="">
      <el-dialog
        title="Add Workflow Task Element"
        :visible.sync="dialogVisible"
        width="50%"
        :before-close="handleClose">
<!--        <el-transfer v-model="departData" :data="data"></el-transfer>-->
        <div style="">
          <div style="">
            <tree-transfer
              :title="['Unselected Task','Selected Task']"
              :from_data='departData'
              :to_data='selectData'
              :defaultProps="defaultProps"
              @add-btn='add'
              @remove-btn='remove'
              :mode='mode'
              height='450px'>
            </tree-transfer>
          </div>
        </div>

      </el-dialog>

    </div>

    <!--      用于添加前置任务-->
    <div style="">
      <el-dialog
        title="Set Pre-task"
        :visible.sync="preTaskVisible"
        width="50%"
        :before-close="handleClose_pre">
        <!--        <el-transfer v-model="departData" :data="data"></el-transfer>-->
        <div style="">
          <el-transfer v-model="preTaskData" :data="workflowData" @change="handleChange"></el-transfer>
        </div>
      </el-dialog>
    </div>

  </div>
</template>

<script>

import {createDefinition, getDepart, getTempalteByid} from "@/api/request";
import treeTransfer from 'el-tree-transfer'
import axios from "axios";
export default {
  name: "CreateDefinition",
  components:{
    treeTransfer,
  },
  data(){

    return{
      defaultProps: {
        children:'children',
        label: 'label',
        id:'id',
      },

      mode: "transfer", // transfer addressList


      processname:'',
      category:'',

      options:[{label:"Workflow Definition",value:'1'},
                {label:"Workflow Creation",value:'2'}],


      tableData: [],
      // tableCopyData:[],
      workflowData:[],

      preTaskData:[],
      //因为transfer的传递都是通过key
      preTaskKey:[],

      departData:[],
      selectData:[],
      // dialog是否可见
      dialogVisible:false,
      preTaskVisible:false,

      count:0,


    }
  },

  mounted() {
    this.getDepartments();
  },
  methods:{
    // 监听穿梭框组件添加
    add(fromData,toData,obj){
      // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的{keys,nodes,halfKeys,halfNodes}对象
      // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
      console.log("fromData:", fromData);
      console.log("toData:", toData);
      console.log("obj:", obj);

      //使用这段代码实现tableData和toData同步
      this.tableData=[];
      // 使用push来添加数组
      for(let i=0;i<toData.length;i++){
        console.log(toData[i])
        for(let j=0;j<toData[i].children.length;j++){
          this.tableData.push(toData[i].children[j])
        }
      }
    },

    // 监听穿梭框组件移除
    remove(fromData,toData,obj){
      // 树形穿梭框模式transfer时，返回参数为左侧树移动后数据、右侧树移动后数据、移动的{keys,nodes,halfKeys,halfNodes}对象
      // 通讯录模式addressList时，返回参数为右侧收件人列表、右侧抄送人列表、右侧密送人列表
      console.log("fromData:", fromData);
      console.log("toData:", toData);
      console.log("obj:", obj);

      //使用这段代码实现tableData和toData同步
      this.tableData=[];
      // 使用push来添加数组
      for(let i=0;i<toData.length;i++){
        for(let j=0;j<toData[i].children.length;j++){
          this.tableData.push(toData[i].children[j])
        }
      }
      // console.log(this.tableData)

      for(let i=0;i<this.tableData.length;i++){
        this.tableCopyData[i]=this.tableData;
      }
      console.log(this.tableCopyData)
    },

    handleClose(){
      this.dialogVisible = false;
      this.preTaskVisible=false;
    },

    getDepartments() {
      getDepart(JSON.parse(sessionStorage.getItem('user')).companyid).then(rsp=>{
          this.departData=rsp.data.data;
          console.log(rsp.data.data)
          for(let i=0;i<this.departData.length;i++){
            // 获取组的任务
            getTempalteByid(this.departData[i].id).then(rsp=>{
              this.departData[i].children=rsp.data.data
              // console.log(this.departData[i].children)
              //很重要，不能删，因为可能需要保持父节点和子节点的名称一致
              this.departData[i].label=this.departData[i].name
              this.departData[i].pid=0;

              for(let j=0;j<this.departData[i].children.length;j++){
                //产品的前置任务
                this.departData[i].children[j].preworks=[];
                //transfer组件中需要key
                this.departData[i].children[j].key=this.departData[i].children[j].id

                this.departData[i].children[j].pid=this.departData[i].children[j].departmentid
                this.departData[i].children[j].label=this.departData[i].children[j].taskname
              }

            })
          }
      })
    },

    handleDelete(obj){

      let depart_id='';
      let depart_index=-1;
      let selectData_index=-1;
      let tableData_index=this.tableData.findIndex((arr)=>arr.id==obj.id)
      for(let i=0;i<this.selectData.length;i++){
        selectData_index=this.selectData[i].children.findIndex((arr)=>arr.id==obj.id)
        if(selectData_index!=-1){
          depart_id=this.selectData[i].id
          depart_index=i;
          break;
        }
        // console.log(this.selectData[i])
      }
      //如果部门不存在
      //如果部门存在
      // console.log(this.selectData.indexOf(obj))
      // this.tableData.splice(tableData_index,1)
      // console.log(this.departData)
      // let Depart_index=this.departData.findIndex((arr)=>arr.id==depart_id)
      // if(Depart_index!=-1){
      //   this.departData[Depart_index].children.push(this.selectData[depart_index].children[selectData_index])
      //
      //   console.log(this.departData[Depart_index].children)
      //   console.log(this.selectData[depart_index])
      //
      // }else {
      //   this.departData.push(this.selectData[depart_id])
      // }
      // // this.departData[Depart_id].children.push(obj)
      // console.log(this.selectData)
    },

    addTemplate(param){
      //直接push param会是引用地址
      let newObject = JSON.parse(JSON.stringify(param));
      newObject.key=++this.count;
      newObject.preTaskKey=[];
      this.workflowData.push(newObject)
      console.log(this.workflowData)
    },

    moveUp(index) {
      if (index > 0) { // 确保不是第一个元素
        const temp = this.workflowData[index - 1];
        this.$set(this.workflowData, index - 1, this.workflowData[index]);
        this.$set(this.workflowData, index, temp);
      }
    },

    deleteElement(index){
      this.workflowData.splice(index,1);
    },

    setPreCondition(obj){
      this.current_id=obj.$index
      this.preTaskVisible=true;
      this.preTaskData=obj.row.preTaskKey
      for(let i=0;i<this.workflowData.length;i++) {
        console.log(this.workflowData[i].preTaskKey.indexOf(obj.row.key))
        if (this.workflowData[i].key == obj.row.key||this.workflowData[i].preTaskKey.indexOf(obj.row.key)!=-1) {
          this.workflowData[i].disabled = true;
        } else {
          this.workflowData[i].disabled = false;
        }
      }

    },

    handleClose_pre(){
      this.dialogVisible = false;
      this.preTaskVisible=false;
      this.workflowData[this.current_id].preTaskKey=this.preTaskData;

      for(let i=0;i<this.workflowData[this.current_id].preTaskKey.length;i++){
        for(let j=0;j<this.workflowData.length;j++){
          if(this.workflowData[j].key===this.workflowData[this.current_id].preTaskKey[i]){
            this.workflowData[this.current_id].preworks[i]=this.workflowData[j]
          }
        }
      }
      console.log(this.workflowData)
      this.preTaskData=[];
      this.current_id=-1;
    },

    handleChange(val, direction, movedKeys) {
      console.log('The transferred data:', val);
      console.log('Transfer direction:', direction);
      console.log('Keys of moved items:', movedKeys);

      // 在这里编写你的业务逻辑
      if (direction === 'right') {
        // 当数据从左边转移到右边时执行的操作
      } else {
        // 当数据从右边转移到左边时执行的操作
      }
    },

    uploadDefinition(){
      let processname=this.processname;
      console.log(processname)
      let workflowData=this.workflowData;
      let companyid=JSON.parse(sessionStorage.getItem('user')).companyid
      let param={processname,workflowData,companyid};
      console.log(param)
      if(processname===''||processname===null){
        this.$message.info("Please input the definition name")
      }else if(this.category===''||this.category===null){
        this.$message.info("Please choose the catalogue")
      }else if (this.workflowData.length===0){
        this.$message.info("Please add the instance in this definition")
      } else{
        createDefinition(param).then(rsp=>{
          console.log(rsp.data)
          if(rsp.data.data===1){
            this.$message.success("Create successfully");
            this.$router.go(-1);
          }
        })
      }
    },

    lastPage(){
      this.$router.go(-1);
    },


  }

}
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.header h1 {
  margin-bottom: 0.5em;
}

.form-group {
  margin-top: 20px;
}

.input-width {
  width: 250px;
}

.select-width {
  width: 250px;
}

.workflow-elements {
  margin-top: 40px;
}

.workflow-elements h2 {
  margin-bottom: 20px;
}

.el-button {
  margin-left: 0 !important; /* Adjust based on layout requirements */
}

/* for better table presentation */
.el-table {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>
