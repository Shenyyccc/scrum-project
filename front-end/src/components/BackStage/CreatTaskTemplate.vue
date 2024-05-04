<template>
  <el-container>
    <el-header>
      <div style="display: flex">
        <el-button type="primary" icon="el-icon-arrow-left" @click="lastPage" style="flex: 0.5;margin-right: 60px;">Last Page</el-button>

        <el-input  style="margin-right: 15px;flex: 1.7" placeholder="Please input the task name" v-model="input.name"></el-input>
        <el-input  style="width: 250px;margin-right: 15px;flex: 1.7" placeholder="Please input the task content" v-model="input.content"></el-input>
        <el-button type="warning" style="margin-right: 5px;flex: 0.5" icon="el-icon-search" @click="handleCurrentChange">Search</el-button>
        <el-button type="primary" style="margin-right: 15px;flex: 0.5" @click="dialogVisible=true"  icon="el-icon-edit">New Task Template</el-button>
      </div>
    </el-header>

    <el-main style="height: 505px">
      <div>
        <el-table :data="tableData" header-cell-class-name="headerBg" :header-cell-style="{background:'#eef1f6',color:'#606266'}"
                  selection-change="handleSelectionChange" empty-text="No Data">
          <el-table-column type="selection" width="55">
          </el-table-column>
          <el-table-column prop="taskname" label="Task Name" width="300" >
          </el-table-column>
          <el-table-column prop="taskscrib" label="Task Content" width="400">
          </el-table-column>
          <el-table-column prop="expiretime" label="Task Expiry(Day)" width="250">
          </el-table-column>

          <el-table-column label="Operation">
            <template v-slot:default="scope" style="display: flex">
              <el-button type="text" @click="handleEdit(scope.row)" size="mini" icon="el-icon-edit" style="margin-right: 10px">edit</el-button>
              <el-popconfirm confirm-button-text='confirm' class="ml-5"
                             cancel-button-text='think again'
                             icon="el-icon-info"
                             icon-color="red"
                             title="Are you sure to delete it？"
                             @confirm="handleDelete(scope.row.id)">
                <el-button type="text" slot="reference" size="mini" icon="el-icon-delete">delete</el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>

<!--        新建-->
        <div>
          <el-dialog
            title="New Task Template"
            :visible.sync="dialogVisible"
            width="33%"
            :before-close="handleClose">
<!--      v-model 主要用于处理表单元素的双向数据绑定，而 v-bind 用于动态地将属性与 Vue 数据绑定-->
            <el-form :inline="true" :model="formData" class="demo-form-inline" ref="newTemplate" :rules="rules">
              <el-form-item label="Task Name:" prop="taskname" label-width="120px">
                <el-input v-model="formData.taskname" placeholder="Please intput Task Name" style="width: 300px"></el-input>
              </el-form-item>
              <el-form-item label="Task Content:" prop="taskscrib" label-width="120px">
                <el-input type="textarea" v-model="formData.taskscrib" placeholder="Please intput Task Content" style="width: 300px"></el-input>
              </el-form-item>
              <el-form-item label="Expiry Time:" label-width="120px">
                <el-input v-model="formData.expiretime" style="width: 90px"></el-input> <span>Day</span>
              </el-form-item>
              <br>
              <el-form-item>
                <div style="display: flex">
                  <el-button type="info" size="medium" @click="handleClose" style="flex: 1;margin-left: 100px">Cancel</el-button>
                  <el-button type="success" size="medium" @click="onSubmit('newTemplate')" style="flex: 1;margin-left: 80px">Submit</el-button>
                </div>
              </el-form-item>

            </el-form>
          </el-dialog>
        </div>

<!--        编辑-->
        <div>
          <el-dialog
            title="Edit Task Template"
            :visible.sync="dialogVisible_edit"
            width="33%"
            :before-close="handleClose_edit">
            <!--      v-model 主要用于处理表单元素的双向数据绑定，而 v-bind 用于动态地将属性与 Vue 数据绑定-->
            <el-form :inline="true" :model="editData" class="demo-form-inline" ref="editForm" :rules="rules">
              <el-form-item label="Task Name:" prop="taskname" label-width="120px">
                <el-input v-model="editData.taskname" placeholder="Please intput Task Name" style="width: 300px"></el-input>
              </el-form-item>
              <el-form-item label="Task Content:" prop="taskscrib" label-width="120px">
                <el-input type="textarea" v-model="editData.taskscrib" placeholder="Please intput Task Content" style="width: 300px"></el-input>
              </el-form-item>
              <el-form-item label="Expiry Time:" label-width="120px">
                <el-input v-model="editData.expiretime" style="width: 90px"></el-input> <span>Day</span>
              </el-form-item>
              <br>
              <el-form-item>
                <<el-button type="info" size="medium" @click="handleClose_edit" style="flex: 1;margin-left: 100px">Cancel</el-button>
                <el-button type="success" size="medium" @click="updateTemplate('editForm')" style="flex: 1;margin-left: 80px">Submit</el-button>
              </el-form-item>

            </el-form>
          </el-dialog>
        </div>

      </div>
    </el-main>

    <el-footer>
      <el-pagination
        @size-change=""
        @current-change="handleCurrentChange"
        :current-page.sync="input.pageNum"
        :page-size="input.pageSize"
        layout="total, prev, pager, next"
        :total="total">
      </el-pagination>
    </el-footer>

  </el-container>
</template>

<script>
import {createTempalte, deleteTemplateById, getTempalte, updateTemplate} from "@/api/request";

export default {
  name: "CreatTaskTemplate",
  data(){
      return{
        tableData:[],
        input:{
          name:'',
          content:'',
          pageSize:8,
          pageNum:1,
          departmentid:'',
          // companyid:JSON.parse(sessionStorage.getItem('user')).companyid
        },

        total:0,
        dialogVisible:false,
        dialogVisible_edit:false,

        formData:{
          taskname:'',
          taskscrib:'',
          expiretime:1,
          departmentid: '',
          companyid:JSON.parse(sessionStorage.getItem('user')).companyid
        },

        editData: {},

        departmentId:'',
        rules:{
          taskname:[{required:true,message:'Task name cannot be null',trigger:'blur'}],
          taskscrib:[{required:true,message:'Task content cannot be null ',trigger:'blur'}],
        },
      }
  },
  mounted() {
    this.formData.departmentid = this.$route.query.id;
    this.input.departmentid = this.$route.query.id;
    console.log('carry data:', this.formData.departmentid);
    this.handleCurrentChange();
  },
  methods:{
      lastPage(){
        this.$router.go(-1);
      },
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            createTempalte(this.formData).then(rsp=>{
              console.log(rsp);
            })
            this.handleClose();
            this.handleCurrentChange();
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },

      updateTemplate(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            console.log(this.editData)
            updateTemplate(this.editData).then(rsp=>{
              console.log(rsp.data)
            })
            this.handleClose_edit();
            this.handleCurrentChange();
          }
        });
      },

      handleClose(){
        this.dialogVisible=false;
        this.formData.taskname='';
        this.formData.taskscrib='';
        this.formData.expiretime=1;
        this.$nextTick(() => {
          this.$refs['newTemplate'].clearValidate();
        })
      },
      handleClose_edit(){
        this.dialogVisible_edit=false;
        this.editData={};
        this.$nextTick(() => {
          this.$refs['editForm'].clearValidate();
        })
      },

      handleCurrentChange(){
          getTempalte(this.input).then(rsp=>{
              console.log(rsp.data.data)
              this.total=rsp.data.data.total;
              this.tableData=rsp.data.data.list;
          })
      },

      handleEdit(param){
        console.log("--->edit")
        console.log(param)
        this.editData=param;
        this.dialogVisible_edit=true;
      },

      handleDelete(param){
        console.log(param)
        deleteTemplateById(param).then(rsp=>{
          console.log(rsp.data)
        })
        this.handleCurrentChange();
      }
  },


}
</script>

<style scoped>

</style>
