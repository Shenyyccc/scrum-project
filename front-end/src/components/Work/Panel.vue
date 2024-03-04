<template>
  <div>
    <h1>{{drag?'拖拽中':'拖拽停止'}}</h1>
    <div class="container">
      <!--    <div>{{drag?'拖拽中':'拖拽停止'}}</div>-->
      <div class="draggable-container">
        <h1>未开始</h1>
        <!--使用draggable组件-->
        <draggable id="1" v-model="myArray"  chosenClass="chosen" forceFallback="true" group="people"
                   animation="300" @start="onStart" @end="onEnd"  style="overflow-y: scroll;height: 450px">
<!--          <transition-group>-->
            <div :workId="element.id" class="item" v-for="element in myArray" :key="element.id" style="margin:10px">{{element.workname}}</div>
<!--          </transition-group>-->
        </draggable>
      </div>

      <div class="draggable-container" >
        <h1>进行中</h1>
        <draggable id='2' v-model="myArray1"  chosenClass="chosen" forceFallback="true" group="people"
                   animation="300" @start="onStart" @end="onEnd" >
<!--          <transition-group>-->
            <div :workId="element.id" class="item" v-for="element in myArray1" :key="element.id" style="margin: 10px">{{element.workname}}</div>
<!--          </transition-group>-->
        </draggable>
      </div>

      <div class="draggable-container">
        <h1>已完成</h1>
        <draggable id='3' v-model="myArray2"  chosenClass="chosen" forceFallback="true" group="people"
                   animation="300" @start="onStart" @end="onEnd" >
<!--          <transition-group>-->
            <div :workId="element.id" class="item" v-for="element in myArray2" :key="element.id" style="margin:10px">{{element.workname}}</div>
<!--          </transition-group>-->
        </draggable>
      </div>
    </div>

  </div>


</template>

<script>
//导入draggable组件
import draggable from 'vuedraggable'
import {getUnstart,getStarting,getFinished,ChangWorkList} from '@/api/request';
export default {
  //注册draggable组件
  components: {
    draggable,
  },
  data() {
    return {
      drag:false,
      //定义要被拖拽对象的数组
      myArray:[
        // {people:'cn',id:1,name:'www.itxst.com'},
        // {people:'cn',id:2,name:'www.baidu.com'},
        // {people:'cn',id:3,name:'www.taobao.com'},
        // {people:'us',id:4,name:'www.google.com'}
      ],
      myArray1:[
        // {people:'1',id:5,name:'www.itxst.com'},
        // {people:'2',id:6,name:'www.baidu.com'},
        // {people:'3',id:7,name:'www.taobao.com'},
        // {people:'4',id:8,name:'www.google.com'}
      ],
      myArray2:[
        // {people:'1',id:9,name:'www.itxst.com'},
        // {people:'2',id:10,name:'www.baidu.com'},
        // {people:'3',id:11,name:'www.taobao.com'},
        // {people:'4',id:12,name:'www.google.com'}
      ]
    };
  },
  mounted() {
    this.GetUnstart();
    this.GetStarting();
    this.GetFinished();
  },
  methods: {
    //获取数组
    GetUnstart(){
      getUnstart().then(rsp=>{
        this.myArray=rsp.data
      }).catch(err=> this.$message.error('获取Unstarted异常'))
    },
    GetStarting(){
      getStarting().then(rsp=>{
        this.myArray1=rsp.data
      }).catch(err=> this.$message.error('获取Starting异常'))
    },
    GetFinished(){
      getFinished().then(rsp=>{
        this.myArray2=rsp.data
      }).catch(err=> this.$message.error('获取Finished异常'))
    },

    //开始拖拽事件
    onStart(evt){
      this.drag=true;
    },
    //拖拽结束事件
    onEnd(evt) {

      this.drag=false;
      // 获取元素原来的索引
      const oldIndex = evt.oldIndex;
      // 获取元素新的索引
      const newIndex = evt.newIndex;
      // 获取元素所在的组别
      const fromList = evt.from.getAttribute('id');
      // 获取元素被拖拽到的组别
      const toList = evt.to.getAttribute('id');
      //获取元素绑定的Id
      const workid=evt.item.getAttribute('workId')

      const param={
        paramId:workid,
        paramProcessId:toList,
      }
      ChangWorkList(param);
      console.log(`workList:${workid}`);
      console.log(`Old index: ${oldIndex}`);
      console.log(`New index: ${newIndex}`);
      console.log(`From list: ${fromList}`);
      console.log(`To list: ${toList}`);
    },
  },
};

</script>


<style scoped>

.container{
  display: flex;
}

/*被拖拽对象的样式*/
.item {
  padding: 6px;
  background-color: #fdfdfd;
  border: solid 1px #eee;
  margin-bottom: 10px;
  cursor: move;
}
/*选中样式*/
.chosen {
  border: solid 2px #3089dc !important;
  color: red;
}

.draggable-container{
  width: 400px;
  height: 500px;
  margin: 30px;
  background: #a4a4a5
}

transition div{
  margin: 20px;
}
</style>
