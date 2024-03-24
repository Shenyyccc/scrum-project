<template>
<div>
  <div style="text-align: right;margin-bottom: 20px">
    <el-button icon="el-icon-plus" round @click="addGroup" size="mini">新建新部门</el-button>
  </div>
  <div>
    <draggable group="group"  @start="" @end="" v-for="(group,index) in groups" :key="index">
      <div  style="margin-bottom: 10px;background: white;border-radius: 20px;">
        <div  style="display: flex;">
          <div style="height: 60px;display: flex">
            <span style="line-height: 60px;margin-left: 30px">{{group.name}}</span>
            <span style="line-height: 60px">(10人)</span>
          </div>


          <div style="text-align: right;line-height: 60px;width: 1100px">
            <el-dropdown >
              <el-button type="text" icon="el-icon-setting">编辑分组</el-button>
              <el-dropdown-menu slot="dropdown" >
                <el-dropdown-item icon="el-icon-edit-outline" @click.native="">修改部门名称</el-dropdown-item>
                <el-dropdown-item icon="el-icon-delete" @click.native="">删除部门</el-dropdown-item>
                <el-dropdown-item icon="el-icon-delete" @click.native="">添加成员</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </div>

        <div>
          <el-collapse @change="" style="border-radius: 20px">
            <el-collapse-item>
              <template slot="title" style="margin-left: 50px">
                <span style="margin-left: 20px">
                  详情
                </span><i class="header-icon el-icon-info"></i>
              </template>
              <div v-for="(person, pid) in group.employees" :key="pid" style="border-radius: 20px;text-align: left;display: flex">
                <!--        <div class="form-group-title">-->
                <span style="margin-left: 20px">姓名：{{person.name}}</span>
                <div style="text-align: right;width: 1150px">
                  <el-button type="text" icon="el-icon-edit-outline" size="mini" @click="">编辑</el-button>
                  <el-button type="text" icon="el-icon-delete" size="mini" @click="">删除</el-button>
                  <!--            <el-button type="text" icon="el-icon-edit-outline" size="mini" @click="">删除</el-button>-->
                </div>
              </div>
            </el-collapse-item>
          </el-collapse>
        </div>
      </div>

    </draggable>
  </div>


</div>
</template>

<script>
import {createDepart, getDepart} from "@/api/request";
import draggable from 'vuedraggable'

export default {
  name: "CreateDepartment",
  components:{
    draggable,
  },
  data(){
    return{
      groups:[
        {name:'开发组',
        employees:[{name:'ys'},
                    {name:'syc'},
                    {name:'jyh'}]
        },
        {name:'科研组',
          employees:[{name:'ys1'},
                    {name:'syc1'},
                    {name:'jyh1'}]
        },
        {name:'销售组',
          employees:[{name:'ys2'},
                    {name:'syc2'},
                   {name:'jyh2'}]
        },
        {name:'金融组',
          employees:[{name:'ys3'},
                      {name:'syc3'},
                      {name:'jyh3'}]
        },
      ]
    }
  },
  mounted() {
    this.getrGroups();
  },
  methods:{
    addGroup() {
      this.$prompt('请输入要添加的部门名', '创建新部门', {
        confirmButtonText: '创建',
        cancelButtonText: '取消',
        inputPattern: /^[\u4E00-\u9FA5A-Za-z0-9\\-]{1,30}$/,
        inputErrorMessage: '分组名不能为空且长度小于30',
        inputPlaceholder: '请输入分组名'
      }).then(({value}) => {
        console.log(value)
        createDepart(value).then(rsp => {
          console.log(rsp)
          this.$message.success("创建成功")
          // this.getGroups()
        }).catch(err => this.$message.error("创建失败"))
      })
    },
    getrGroups(){
      // getDepart().then(rep=>{
      //   console.log(rep)
      //   this.groups=rep.data;
      // })
    }
  }
}
</script>

<style scoped>



</style>
