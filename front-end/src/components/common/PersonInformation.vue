
<template>
  <div style="display: flex; justify-content: center;">
    <el-card style="width: 90%">

      <el-card style="height: 70px;display: flex; justify-content: center;">
        <div style="margin-top: 10px;">
          <h1 style="font-size: 30px;margin-top: -18px">Personal Information</h1>
        </div>
      </el-card>

      <el-card style="display: flex; justify-content: center;margin-top: 10px">
        <div style="text-align: center;width:500px">
          <el-form :model="userInform" ref="userInform" label-width="80px" class="demo-ruleForm" style="display: inline-block;padding: 0px" inline>
            <el-form-item >
              <el-upload
                action="http://localhost:8080/files/upload"
                :on-success="successUpload"
                multiple
                :show-file-list="false">
                <el-avatar v-if="userInform.avatar" :src="'http://localhost:8080/files/'+userInform.avatar" :size="200" style=""></el-avatar>
                <el-avatar v-else :size="200"><span style="font-size: 100px">{{userInform.name.charAt(0)}}</span></el-avatar>
              </el-upload>
            </el-form-item>

            <div >
              <el-form-item label="Username:" prop="username" style="margin-right: 10px;">
                <el-input v-model="userInform.username" :disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="Name:" prop="name"  style="margin-right: 10px">
                <el-input v-model="userInform.name"></el-input>
              </el-form-item>
              <el-form-item label="Company:" prop="company"  style="margin-right: 10px">
                <el-input v-model="userInform.company" :disabled="userInform.identity!='1'"></el-input>
              </el-form-item>
            </div>

            <div>
              <el-button type="primary" style="margin-left: 60px" @click="reset">RESET</el-button>
              <el-button type="primary" @click="updateInfo">SAVE</el-button>
            </div>
          </el-form>
        </div>
      </el-card>
    </el-card>
  </div>
</template>

<script>
import {updateUser} from "@/api/request";

export default {
  name: "PersonInformation",

  data(){
      return{
        userInform:JSON.parse(sessionStorage.getItem('user'))
      }
  },
  methods:{
    getUserInform(){

    },
    successUpload(response, file, fileList){
      console.log(response,file,fileList)
        this.userInform.avatar=response.data
    },
    reset(){
      this.userInform=JSON.parse(sessionStorage.getItem('user'));
    },
    updateInfo(){
        console.log(this.userInform)
        updateUser(this.userInform).then(rsp=>{
          console.log(rsp.data.data)
          sessionStorage.setItem('user',JSON.stringify(rsp.data.data))
          userInform:JSON.parse(sessionStorage.getItem('user'))
          this.$emit("updateInfo",this.userInform);
        })
    }
  },
  mounted() {
      console.log(JSON.parse(sessionStorage.getItem('user')))
  }

}
</script>

<style scoped>
.el-form--inline .el-form-item{
  margin-right: 10px;
}
</style>
