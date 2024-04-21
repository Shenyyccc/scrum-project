<template>
  <div style="">
    <el-container>
      <el-header style="width: 1300px;margin-bottom: 10px">
        <h1>某某部门任务领取处</h1>
        <div style="margin-bottom: 20px;margin-right: 60px;text-align: right">
          <el-input v-model="input.name" style="width: 250px;margin-right: 5px" placeholder="请输入查询任务"></el-input>
          <el-input v-model="input.scrib" style="width: 250px;margin-right: 5px" placeholder="请输入查询任务内容"></el-input>
          <el-input v-model="input.prio" style="width: 250px;margin-right: 5px" placeholder="请输入查询优先级"></el-input>
          <el-input v-model="input.time" style="width: 250px;margin-right: 5px" placeholder="请输入期限时间"></el-input>
          <el-button type="warning" style="margin-right: 5px">查询</el-button>
          <el-button type="primary" style="margin-right: 15px">清空</el-button>
        </div>
      </el-header>
      <el-main style="height: 520px">
        <el-divider></el-divider>
        <div>
          <div v-for="(group, index) in groupedData" :key="index" style="display: flex">
            <Work v-for="(work,index) in group" style="margin-bottom: 10px;width: 400px;margin-right: 10px" v-if="index<3">
              <div slot="work_name">
                {{work.workname}}
              </div>
              <div slot="work_content">
                {{work.workscrib}}
              </div>
            </Work>
          </div>
        </div>
      </el-main>
      <el-footer>
        <div class="block">
          <el-pagination
            @size-change=""
            @current-change="handleCurrentChange"
            :current-page.sync="input.pageNum"
            :page-size="input.pageSize"
            layout="total, prev, pager, next"
            :total="total">
          </el-pagination>
        </div>
      </el-footer>
    </el-container>




  </div>

</template>

<script>
import Work from "@/components/common/Work.vue";
import {getWorks} from "@/api/request";

export default {
  name: "WorkPickup",
  components: {Work},
  data(){
    return{
      input:{
        name:'',
        scrib:'',
        prio:'',
        time:'',
        pageSize:9,
        pageNum:1,
      },
      works:[
      ],
      itemsPerGroup: 3,
      total:0,
      dialogVisible:''

    }
  },
  computed:{
      // 计算分组后的数据
      groupedData() {
        const groups = [];
        for (let i = 0; i < this.works.length; i += this.itemsPerGroup) {
          groups.push(this.works.slice(i, i + this.itemsPerGroup));
        }
        console.log(groups)
        return groups;
      },
  },
  mounted() {
    this.getWorks();
  },
  methods:{
    getWorks(){
      getWorks(this.input).then(rsp=>{
        this.works=rsp.data.data.list;
        this.total=rsp.data.data.total;
        console.log(rsp.data.data.total)
      })

    },
    handleCurrentChange(){
      this.getWorks()
    },

  }
}
</script>

<style scoped>

</style>
