<template>
  <div style="display: flex; justify-content: center;">
    <el-card style="width: 90%">

      <el-card style="height: 70px;display: flex; justify-content: center;">
        <div style="margin-top: 10px;">
          <h1 style="font-size: 30px;margin-top: -18px">Modify Password</h1>
        </div>
      </el-card>

      <el-card style="display: flex; justify-content: center;margin-top: 10px">
        <div style="text-align: center;width:500px">
          <el-form :model="newInfo" ref="userInform" label-width="170px" class="demo-ruleForm" style="display: inline-block;padding: 0px" inline
          :rules="Rules">
<!--            <el-form-item >-->
<!--                <el-avatar v-if="newInfo.avatar" :src="'http://localhost:8080/files/'+newInfo.avatar" :size="200" style=""></el-avatar>-->
<!--                <el-avatar v-else :size="200"><span style="font-size: 100px">{{newInfo.name.charAt[0]}}</span></el-avatar>-->
<!--            </el-form-item>-->

            <div style="">
              <el-form-item label="Username:" prop="username" style="margin-right: 10px">
                <el-input v-model="newInfo.username" :disabled="true"></el-input>
              </el-form-item>
              <el-form-item label="Original Password:" prop="oldPass"  style="margin-right: 10px">
                <el-input v-model="newInfo.oldPass" show-password></el-input>
              </el-form-item>
              <el-form-item label="New Password:" prop="newPass"  style="margin-right: 10px">
                <el-input v-model="newInfo.newPass" show-password></el-input>
              </el-form-item>
              <el-form-item label="Confirm new Password:" prop="confirmPass"  style="margin-right: 10px " >
                <el-input v-model="newInfo.confirmPass" show-password></el-input>
              </el-form-item>
            </div>

            <div>
              <el-button type="primary" @click="updateInfo('userInform')">Update</el-button>
            </div>
          </el-form>
        </div>
      </el-card>
    </el-card>
  </div>
</template>

<script>
import {getUserById, updatePass, updateUser} from "@/api/request";

export default {
  name: "ModifyPass",
  data(){

    const validateOldPass=(rule, value, callback)=>{
      if (value === '') {
        callback(new Error('Please enter your password'));
      } else if (value!==this.oldInfo.password){
        callback(new Error('The original password is wrong'))
      }else{
        callback();
      }
    }

    const validateNewPass=(rule, value, callback)=>{
      if (value === '') {
        callback(new Error('Please enter your password'));
      } else if (value===this.oldInfo.password){
        callback(new Error('The new Password is same'))
      }else{
        callback();
      }
    }

    const validatePass=(rule, value, callback)=>{
      if (value === '') {
        callback(new Error('Please enter your password'));
      } else if (value!==this.newInfo.newPass&&this.newInfo.newPass!=''){
        callback(new Error('Two passwords do not match'))
      }else{
        callback();
      }
    }

    return{
      oldInfo:{},
      newInfo:{
        username:'',
        oldPass:'',
        newPass:'',
        confirmPass:''
      },
      Rules:{
        oldPass: [{validator:validateOldPass,trigger:'blur'},
                  {required:true,message:'The original password cannot be empty',trigger:'blur'}],
        newPass:[{validator:validateNewPass,trigger:'blur'},
          {required:true,message:'The new password cannot be empty',trigger:'blur'}],
        confirmPass: [{validator:validatePass,trigger:'blur'},
          {required:true,message:'The new password cannot be empty',trigger:'blur'}],

      },
    }
  },
  methods:{

    updateInfo(userInform){
      this.$refs[userInform].validate((valid) =>{
        if(valid){
          this.oldInfo.password=this.newInfo.newPass
          console.log(this.oldInfo)
          updatePass(this.oldInfo).then(rsp=>{
            console.log(rsp.data.data)
            sessionStorage.removeItem("user")
            this.$message.success("The password is modified successfully. Please log in again")
            this.$router.push('/')
          })
        }
      })

    }
  },

  mounted(){
    let userid=JSON.parse(sessionStorage.getItem('user')).userId
    getUserById(userid).then(rsp=>{
      console.log(rsp.data.data)
      this.oldInfo=rsp.data.data
      this.newInfo.username=this.oldInfo.username;
      console.log(this.newInfo)
    })
  }

}
</script>

<style scoped>

</style>
