<template>
  <div>

    <div style="margin: 10px 0">
      <el-input type="text" suffix-icon="el-icon-search" v-model="username" clearable style="width: 200px" placeholder="请输入用户名"></el-input>
      <el-input type="text" suffix-icon="el-icon-search" v-model="address"  clearable style="width: 200px" placeholder="请输入地址"></el-input>
      <el-button class="ml-5" type="primary" @click="load" >搜索</el-button>
      <el-button class="ml-5" type="success" @click="reset" >重置</el-button>
      <!--            <el-input type="text" suffix-icon="el-icon-search" style="width: 200px" placeholder="请输入内容"></el-input><el-button class="ml-5" type="primary">搜索</el-button>-->
      <!--            <el-input type="text" suffix-icon="el-icon-search" style="width: 200px" placeholder="请输入内容"></el-input><el-button class="ml-5" type="primary">搜索</el-button>-->
    </div>
    <div style="margin: 10px 0" >
      <el-button type="primary" @click="handleAdd">添加 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm class="ml-5"
                     confirm-button-text='确定'
                     cancel-button-text='再想想'
                     icon="el-icon-info"
                     icon-color="red"
                     title="确定要删除所选数据吗吗？"
                     @confirm="deleteBatch">
        <el-button type="danger" slot="reference" class="mr-5">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

      <el-button type="primary" class="ml-5">导入 <i class="el-icon-upload2"></i></el-button>
      <el-button type="primary" class="mr-5">导出 <i class="el-icon-download"></i></el-button>
    </div>
    <el-table :data="tableData" header-cell-class-name="headerBg" :header-cell-style="{background:'#eef1f6',color:'#606266'}"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="username" label="用户名" width="120">
      </el-table-column>
      <el-table-column prop="email" label="邮箱" width="140">
      </el-table-column>
      <el-table-column prop="phone" label="手机" width="140">
      </el-table-column>
      <el-table-column prop="address" label="地址" width="240">
      </el-table-column>
      <el-table-column prop="address" label="xx" width="240">
      </el-table-column>


      <el-table-column label="操作">
        <template v-slot:default="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm confirm-button-text='确定' class="ml-5"
                         cancel-button-text='再想想'
                         icon="el-icon-info"
                         icon-color="red"
                         title="确定要删除吗？"
                         @confirm="handleDelete(scope.row.id)">
            <el-button type="danger" slot="reference">删除 <i class="el-icon-circle-close"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div class="pd-10">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[2, 5, 8, 10]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <router-view></router-view>
    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="60px" size="mini">
        <el-form-item label="用户名">
          <el-input v-model="form.username" clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机">
          <el-input v-model="form.phone" clearable autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" clearable autocomplete="off"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="load">取 消</el-button>
        <el-button type="primary"  @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data(){
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      username: "",
      address: "",
      form: {},
      dialogFormVisible: false,
      multipleSelection: [],
      headerBg: 'headerBg',
    }
  },

  created() {
    this.load()
  },

  methods:{
    load(){
      this.request.get("/user/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
          address:this.address
        }
      }).then(res =>{
        console.log(res)
        this.tableData=res.records
        this.total=res.total
        this.dialogFormVisible=false
      })
      //请求分页查询数据
      // fetch("http://localhost:9090/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username+"&address="+this.address)
      //   .then(res => res.json()).then(res => {
      //   console.log(res)
      //   this.tableData=res.data
      //   this.total=res.total
      // })
    },

    //退出
    logout(){
      // 清空token
      // window.sessionStorage.clear()
      //  将登录页面转入到登录界面
      this.$router.push('/')
    },

    //重置
    reset(){
      this.username=""
      this.address=""
      this.load()
    },

    //增加
    handleAdd(){
      this.dialogFormVisible=true
      this.form={}
    },

    //编辑
    handleEdit(row){
      this.form=row
      this.dialogFormVisible=true
    },

    //删除
    handleDelete(id){
      this.request.delete(("/user/"+id)).then(res =>{
        if(res){
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },

    //批量删除
    handleSelectionChange(val){
      console.log(val)
      this.multipleSelection=val
    },

    deleteBatch(){
      let ids = this.multipleSelection.map(v => v.id)//把对象转变成 对象中某个属性的数字：[{name},{pwd},{id}]====>[1,2,3]
      this.request.post("/user/batch",ids).then(res =>{
        if(res){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },

    //保存
    save(){
      this.request.post("/user",this.form).then(res => {
        if(res){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    handleSizeChange(pageSize){
      // console.log(pageSize)
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum){
      // console.log(pageNum)
      this.pageNum=pageNum
      this.load()
    },
  }

}
</script>

<style scoped>
.headerBg{
  background: #eee !important;
}
</style>
