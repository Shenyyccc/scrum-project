<template>
  <div style="margin: 0px;padding: 0px;">
    <div style="margin-top: 0px;display: flex;margin-left: 500px">
      <h1>Task template</h1>
      <router-link :to="{path:'/Backstage/FormDesign/CreateDefinition'}">
        <el-button style="margin-left: 400px;height: 40px" size="mini" type="success" round icon="el-icon-edit" >Create WorkFlow Definition</el-button>
      </router-link>
    </div>


    <div>
      <draggable group="group"  @start="" @end="" v-for="(group,index) in groups" :key="index">
        <div  style="margin-bottom: 10px;background: white;border-radius: 20px;">
          <div  style="display: flex;">
            <div style="height: 60px;flex: 1;width: 300px">
                <span style="line-height: 60px;margin-left: 30px;font-weight: bold;font-size: 20px">{{group.name}}</span>
                <span style="line-height: 60px;font-size: 11px;color: gray">（{{group.workList.length}} Template Task）</span>
            </div>
            <div style="text-align: right;line-height: 60px;width: 1100px;margin-right: 20px;flex: 1">
              <el-button icon="el-icon-search" round type="primary" style="font-size: 10px;margin-right: 10px" size="mini" @click="changeManager(group.id)">Change Manager</el-button>

              <router-link :to="{path:'/Backstage/FormDesign/CreateTask',query:{id:group.id}}">
                <el-button icon="el-icon-edit" round type="success" style="font-size: 10px" size="mini" @click="">Add Template Task</el-button>
              </router-link>
            </div>
          </div>
          <div>
            <div style="width: 700px" v-if="group.managerid!=null&&group.managerid!=''">
              <el-descriptions title="Department Manager" style="margin-left: 30px;">
                <el-descriptions-item label="name">{{group.manager.name}}</el-descriptions-item>
                <el-descriptions-item label="phone">{{ group.manager.phone }}</el-descriptions-item>
              </el-descriptions>
            </div>
            <div v-else>
              <el-descriptions title="Department Manager" style="margin-left: 30px;">
                <el-descriptions-item label="Tip">
                  <span style="color: red">Please appoint a manager as soon as possible!</span>
                </el-descriptions-item>
              </el-descriptions>
            </div>
          </div>

        </div>
      </draggable>
    </div>

    <div>
      <el-dialog
        title="Change Department Manager"
        :visible.sync="dialogVisible"
        width="35%"
        :before-close="handleClose">
        <div style="display: flex;">
          <el-input prefix-icon="el-icon-search" style="width: 300px;margin-left:30px" v-model="research" placeholder="please input username"></el-input>
          <el-button @click="getDepartMembers" round type="primary" style="margin-left: 20px" size="small">Research</el-button>
        </div>


        <div>
          <el-table
            :data="departGroup"
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
              fixed="right"
              label="Operation"
              width="150"
              align="center">
              <template slot-scope="scope">
                <el-button
                  @click="appoint(scope.row.id)"
                  type="text"
                  size="small">
                  appoint
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
import draggable from 'vuedraggable'
import {getTaskTemplates, getDepartMember, setManager, getUserById} from "@/api/request";
import index from "vuex";
export default {
  name: "FormDesign",
  computed: {
    index() {
      return index
    },

  },
  components:{
    draggable,
  },
  data(){
    return{
        scale:100,
        num:this.$store.state.count,
        groups:{},
        companyId:JSON.parse(sessionStorage.getItem('user')).companyid,
        dialogVisible:false,
        departGroup:[],
        research:'',
        currentDepart:'',

    }
  },
  mounted() {
    this.getDepartments();
  },
  methods:{
    clickDown(){
        this.$store.commit('addNum',3)
    },

    getDepartments(){
        getTaskTemplates(this.companyId).then(rep=>{
          console.log(rep.data.data)
          this.groups=rep.data.data;
        })
    },

    changeManager(param){
      this.dialogVisible=true;
      this.currentDepart=param;
      this.getDepartMembers();
    },

    getDepartMembers(){
      let departId=this.currentDepart;
      let input=this.research
      let params={departId,input}
      getDepartMember(params).then(rep=>{
        this.departGroup=rep.data.data;
      })
    },

    handleClose(){
      this.dialogVisible=false;
      this.departGroup=[];
      this.research='';
      this.currentDepart='';
    },

    appoint(param){
        let userId=param;
        let departId=this.currentDepart;
        let params={userId,departId};
        console.log(params)
        setManager(params).then(rep=>{
          this.dialogVisible=false;
          window.location.reload()
        })

    }

  }
}
</script>

<style lang="less" scoped>
.scale {
  z-index: 999;
  position: fixed;
  top: 80px;
  right: 40px;

  span {
    margin: 0 10px;
    font-size: 15px;
    color: #7a7a7a;
    width: 50px;
  }
}
</style>
