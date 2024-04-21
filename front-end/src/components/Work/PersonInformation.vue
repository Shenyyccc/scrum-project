<template>
  <div style="text-align: center">
    <el-form :model="userInform" ref="userInform" label-width="100px" class="demo-ruleForm">
      <el-form-item>
          <el-avatar :src="'http://localhost:8080/files/'+userInform.avator" size="large" style="width: 200px;height: 200px"></el-avatar>
      </el-form-item>
      <el-form-item>
        <el-upload
          action="http://localhost:8080/files/upload"
          :on-success="successUpload"
          multiple>
<!--          <el-button size="small" type="primary">点击上传</el-button>-->
<!--          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
          <el-avatar :src="'http://localhost:8080/files/'+userInform.avator" size="large" style="width: 200px;height: 200px"></el-avatar>
        </el-upload>
      </el-form-item>
      <el-form-item label="Username" prop="username" style="width: 300px">
        <el-input v-model="userInform.username" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="Name" prop="name"  style="width: 300px">
        <el-input v-model="userInform.name"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="">RESET</el-button>
        <el-button type="primary" @click="">SAVE</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "PersonInformation",

  data(){
      return{
        userInform:{
        }
      }
  },
  methods:{
    getUserInform(){
        this.userInform=JSON.parse(sessionStorage.getItem('user'));
        console.log(this.userInform);
    },
    successUpload(res){
        console.log(res)
    },
  },
  mounted() {
    this.getUserInform();
  }

}
</script>

<style scoped>

</style>
