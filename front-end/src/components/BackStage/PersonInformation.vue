<template>
  <div style="text-align: center">
    <div style="height: 80px;margin-top: 20px;margin-left: 120px">
      <h1 style="font-size: 30px">Personal Information</h1>
    </div>
    <el-form :model="userInform" ref="userInform" label-width="100px" class="demo-ruleForm" style="">
      <el-form-item v-if="userInform.avatar==null||userInform.avatar==''" >
        <el-upload
          action="http://localhost:8080/files/upload"
          :on-success="successUpload"
          multiple
          :show-file-list="false">
          <el-avatar :size="200"><span style="font-size: 100px">{{userInform.name.charAt(0)}}</span></el-avatar>
        </el-upload>
      </el-form-item>
      <el-form-item v-else>
        <el-upload
          action="http://localhost:8080/files/upload"
          :on-success="successUpload"
          multiple
          :show-file-list="false">
<!--          <el-button size="small" type="primary">点击上传</el-button>-->
<!--          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
          <el-avatar :src="'http://localhost:8080/files/'+userInform.avatar" :size="200" style=""></el-avatar>
        </el-upload>
      </el-form-item>

      <div style="margin-left: 500px">
        <el-form-item label="Username:" prop="username" style="width: 300px">
          <el-input v-model="userInform.username" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="Name:" prop="name"  style="width: 300px">
          <el-input v-model="userInform.name"></el-input>
        </el-form-item>
        <el-form-item label="Company:" prop="company"  style="width: 300px">
          <el-input v-model="userInform.company"></el-input>
        </el-form-item>
      </div>

      <el-form-item>
        <el-button type="primary" @click="reset">RESET</el-button>
        <el-button type="primary" @click="updateInfo">SAVE</el-button>
      </el-form-item>
    </el-form>
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
    successUpload(res){
        this.userInform.avatar=res.data
    },
    reset(){
      this.userInform=JSON.parse(sessionStorage.getItem('user'));
    },
    updateInfo(){
        console.log(this.userInform)
        updateUser(this.userInform).then(rsp=>{
          console.log(rsp.data.data)
          sessionStorage.setItem('user',JSON.stringify(rsp.data.data))
        })
        window.location.reload();
    }
  },
  mounted() {
      console.log(JSON.parse(sessionStorage.getItem('user')))
  }

}
</script>

<style scoped>

</style>
