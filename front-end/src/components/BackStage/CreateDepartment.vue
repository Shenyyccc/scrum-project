<template>
<div>
  <div style="text-align: right;margin-bottom: 20px">
    <el-button icon="el-icon-plus" round @click="addGroup" size="mini">Creat Department</el-button>
  </div>
  <div>
    <draggable group="group"  @start="" @end="" v-for="(group,index) in groups" :key="index">
      <div  style="margin-bottom: 10px;background: white;border-radius: 20px;">
        <div  style="display: flex;">
          <div style="height: 60px;flex:1">
            <span style="line-height: 60px;margin-left: 30px">{{group.name}}</span>
            <span style="line-height: 65px;font-size: 10px;color: #a4a4a5">( {{group.num}} PEOPLE )</span>
          </div>


          <div style="text-align: right;line-height: 60px;width: 300px;margin-right: 25px">
            <el-dropdown>
              <el-button type="text" icon="el-icon-setting">edit department</el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item icon="el-icon-plus" >
                  <el-button type="text" @click="addMember(groups[index].companyid,groups[index].id)">add member</el-button>
                </el-dropdown-item>
                <el-dropdown-item icon="el-icon-edit-outline">
                  <el-button type="text" @click="editName(group.id,index)">edit name</el-button>
                </el-dropdown-item>
                <el-dropdown-item icon="el-icon-delete">
                  <el-button type="text" @click="deleteDepart(groups[index].id,index)" style="color: red">delete department</el-button>
                </el-dropdown-item>

              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>

        <div>
          <el-collapse @change="" style="border-radius: 20px">
            <el-collapse-item>
              <template slot="title" style="">
                <span style="margin-left: 20px">
                  detail&nbsp;
                </span><i class="header-icon el-icon-info"></i>
              </template>
              <div v-for="(person, pid) in group.userList" :key="pid" style="border-radius: 20px;text-align: left;display: flex">
                <!--        <div class="form-group-title">-->
                <span style="margin-left: 20px;flex: 4">name：{{person.name}}</span>
                <div style="text-align: right;width: 1150px;flex: 1;margin-right: 20px">
<!--                  <el-button type="text" icon="el-icon-edit-outline" size="mini" @click="">edit</el-button>-->
                  <el-button type="text" icon="el-icon-delete" size="mini" @click="deleteMember(index,pid)" style="color: red">delete</el-button>
                </div>
              </div>
            </el-collapse-item>
          </el-collapse>
        </div>

      </div>

    </draggable>
  </div>

  <div>
    <el-dialog
      title="Add Member"
      :visible.sync="dialogVisible"
      width="35%"
      :before-close="handleClose">
    <div style="display: flex;">
      <el-input prefix-icon="el-icon-search" style="width: 300px;margin-left:30px" v-model="research" placeholder="please input username"></el-input>
      <el-button @click="getMember()" round type="primary" style="margin-left: 20px" size="small">Research</el-button>
    </div>


<!--    <div v-for="(NoDepart,index) in NoDepartGroup">-->
<!--      <span>{{NoDepart.username}}</span>-->
<!--    </div>-->
    <div>
      <el-table
        :data="NoDepartGroup"
        style="width: 100%"
        max-height="250"
      empty-text="No data">
        <el-table-column
          prop="username"
          label="Username"
          width="150">
        </el-table-column>
        <el-table-column
          prop="name"
          label="Name"
          width="150">
        </el-table-column>

        <el-table-column
          prop="companyid"
          label="Company"
          width="150">
        </el-table-column>

        <el-table-column
          fixed="right"
          label="Operation"
          width="70"
          align="center">
          <template slot-scope="scope">
            <el-button
              @click="add(scope.$index)"
              type="text"
              size="small">
              add
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    </el-dialog>

  </div>

</div>
</template>

<script>
import {createDepart, deleteDepart, editDepart, getDepart, getNoDepart,joinDepart,removeDepart} from "@/api/request";
import draggable from 'vuedraggable'

export default {
  name: "CreateDepartment",
  components:{
    draggable,
  },
  data(){
    return{
      groups:[],
      dialogVisible:false,
      research:'',
      NoDepartGroup:[],
      company_id:JSON.parse(sessionStorage.getItem('user')).companyid,
      currentDepart:'',
    }
  },
  mounted() {
    this.getGroups();
  },
  methods:{
    addGroup() {
      this.$prompt('Please enter the department you want to add', 'Create Department', {
        confirmButtonText: 'Create',
        cancelButtonText: 'Cancel',
        // inputPattern: /^[\u4E00-\u9FA5A-Za-z0-9\\-]{1,100}$/,
        // inputErrorMessage: '分组名不能为空且长度小于40',
        inputPlaceholder: 'Please input the name the department'
      }).then(({value}) => {
        console.log(JSON.parse(sessionStorage.getItem('user')))
        let companyId=JSON.parse(sessionStorage.getItem('user')).companyid
        let params={
          value,
          companyId
        }
        createDepart(params).then(rsp => {
          console.log(rsp)
          this.$message.success("Create Successful")
          // this.getGroups()
          location.reload();
        }).catch(err => this.$message.error("Creation failure"))
      }).catch(err=>{})
    },

    getGroups(){

      getDepart(JSON.parse(sessionStorage.getItem('user')).companyid).then(rep=>{
        console.log(rep.data)
        this.groups=rep.data.data;
      })
    },

    addMember(param,deparId){
      this.currentDepart=deparId;
      this.dialogVisible=true;
      this.getMember(param)
    },

    getMember(){
      let comId=this.company_id;
      let rese=this.research;
      let params={
        comId,rese
      }
      getNoDepart(params).then(rep=>{
        let company=JSON.parse(sessionStorage.getItem('user')).company;
        this.NoDepartGroup=rep.data.data;
        rep.data.data.map(function (element){
          if(element.companyid!='' && element.companyid!=null){
            element.companyid=company
          }
        })
        console.log(this.NoDepartGroup)
      });
    },

    add(index){
      let userId=this.NoDepartGroup[index].id;
      let departId=this.currentDepart;
      let companyId=this.company_id;
      let params={userId,departId,companyId}
      console.log(params);
      joinDepart(params).then(rep=>{
        console.log(rep.data.data);
        this.getMember();
        this.getGroups();
      })
    },

    editName(departmentid,index){
      this.$prompt('Please enter the new department name', 'Change Department Name', {
        confirmButtonText: 'Change',
        cancelButtonText: 'Cancel',
        // inputPattern: /^[\u4E00-\u9FA5A-Za-z0-9\\-]{1,100}$/,
        // inputErrorMessage: '分组名不能为空且长度小于40',
        inputPlaceholder: 'Please input the new name the department'
      }).then(({value}) => {

        let param={
          value,departmentid
        };
        editDepart(param).then(rep=>{
          this.$message.success("Change Successful")
          this.groups[index].name=value
        }).catch(err=>this.$message.error("Change failure"))
      }).catch(err=>{})
    },

    deleteDepart(departmentid,index){
      this.$confirm("Are you sure to delete this group?","Tip",{
        confirmButtonText: 'SURE',
        cancelButtonText: 'NO',}
      ).then(()=>{
        let param=departmentid
        deleteDepart(param).then(rep=>{
          this.$message.success("delete successful")
          this.groups.splice(index,1)
        })
      }).catch(()=>{})

    },

    deleteMember(departmentIndex,personIndex){
      let userId=this.groups[departmentIndex].userList[personIndex].id;
      let departId=this.groups[departmentIndex].id
      let param={userId,departId}
        removeDepart(param).then(rep=>{
          if(rep.data.data){
            this.$message.success("remove member successful!")
            this.groups[departmentIndex].userList.splice(personIndex,1);
          }
        });
    },

    handleClose(){
      this.dialogVisible=false;
      this.research='';
      this.currentDepart='';
    },

  }
}
</script>

<style scoped>



</style>
