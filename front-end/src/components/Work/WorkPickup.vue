<template>
  <div style="">
    <h1>某某部门任务领取处</h1>
<!--    <div style="width: 1250px;display: flex">-->
<!--      <Work v-for="(work,index) in works" style="margin-bottom: 10px;width: 400px;margin-right: 10px" v-if="index<3">-->
<!--        <div slot="work_name">-->
<!--          {{work.people}}-->
<!--        </div>-->
<!--        <div slot="work_content">-->
<!--          {{work.name}}-->
<!--        </div>-->
<!--      </Work>-->
<!--    </div>-->
    <div>
      <div v-for="(group, index) in groupedData" :key="index" style="display: flex">
          <Work v-for="(work,index) in group" style="margin-bottom: 10px;width: 400px;margin-right: 10px" v-if="index<3">
            <div slot="work_name">
              {{work.people}}
            </div>
            <div slot="work_content">
              {{work.name}}
            </div>
          </Work>
      </div>
    </div>

    <div>
      <el-pagination
        small
        layout="prev, pager, next"
        :total="50">
      </el-pagination>
    </div>


  </div>

</template>

<script>
import Work from "@/components/common/Work.vue";

export default {
  name: "WorkPickup",
  components: {Work},
  data(){
    return{
      works:[
        {people:'cn',id:1,name:'www.itxst.com'},
        {people:'cn',id:2,name:'www.baidu.com'},
        {people:'cn',id:3,name:'www.taobao.com'},
        {people:'us',id:4,name:'www.google.com'},
        {people:'cn',id:1,name:'www.itxst.com'},
        {people:'cn',id:2,name:'www.baidu.com'},
        {people:'cn',id:3,name:'www.taobao.com'},
        {people:'us',id:4,name:'www.google.com'}
      ],
      itemsPerGroup: 3,

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
  }
}
</script>

<style scoped>

</style>
