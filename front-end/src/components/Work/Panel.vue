<template>
  <div>
    <h1 style="text-align: center">{{drag?'拖拽中':'拖拽停止'}}</h1>
    <div class="container">
      <!--    <div>{{drag?'拖拽中':'拖拽停止'}}</div>-->
      <div>
        <h1>未开始</h1>
        <!--使用draggable组件-->
        <draggable v-model="myArray"  chosenClass="chosen" forceFallback="true" group="people"
                   animation="300" @start="onStart" @end="onEnd">
          <transition-group>
            <div class="item" v-for="element in myArray" :key="element.id">{{element.name}}</div>
          </transition-group>
        </draggable>
      </div>

      <div>
        <h1>进行中</h1>
        <draggable v-model="myArray1"  chosenClass="chosen" forceFallback="true" group="people"
                   animation="300" @start="onStart" @end="onEnd">
          <transition-group>
            <div class="item" v-for="element in myArray1" :key="element.id">{{element.name}}</div>
          </transition-group>
        </draggable>
      </div>

      <div>
        <h1>已完成</h1>
        <draggable v-model="myArray2"  chosenClass="chosen" forceFallback="true" group="people"
                   animation="300" @start="onStart" @end="onEnd">
          <transition-group>
            <div class="item" v-for="element in myArray2" :key="element.id">{{element.name}}</div>
          </transition-group>
        </draggable>
      </div>
    </div>

  </div>





</template>

<script>
//导入draggable组件
import draggable from 'vuedraggable'
export default {
  //注册draggable组件
  name:'Panel',
  components: {
    draggable,
  },
  data() {
    return {
      drag:false,
      //定义要被拖拽对象的数组
      myArray:[
        {people:'cn',id:1,name:'www.itxst.com'},
        {people:'cn',id:2,name:'www.baidu.com'},
        {people:'cn',id:3,name:'www.taobao.com'},
        {people:'us',id:4,name:'www.google.com'}
      ],
      myArray1:[
        {people:'1',id:1,name:'www.itxst.com'},
        {people:'2',id:2,name:'www.baidu.com'},
        {people:'3',id:3,name:'www.taobao.com'},
        {people:'4',id:4,name:'www.google.com'}
      ],
      myArray2:[
        {people:'1',id:1,name:'www.itxst.com'},
        {people:'2',id:2,name:'www.baidu.com'},
        {people:'3',id:3,name:'www.taobao.com'},
        {people:'4',id:4,name:'www.google.com'}
      ]
    };
  },
  methods: {
    //开始拖拽事件
    onStart(){
      this.drag=true;
    },
    //拖拽结束事件
    onEnd() {
      this.drag=false;
    },
  },
};
</script>


<style scoped>

.container{
  display: flex;
}
Panel{
  text-color:#ffffff
}
/*被拖拽对象的样式*/
.item {
  padding: 6px;
  background-color: #fdfdfd;
  border: solid 1px #eee;
  margin-bottom: 10px;
  cursor: move;
  text-align: center;
}
/*选中样式*/
.chosen {
  text-align: center;
  border: solid 2px #3089dc !important;
  color: red;
}
</style>
