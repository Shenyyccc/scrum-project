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
            <div class="item" v-for="element in myArray" :key="element.id" style="margin:10px">{{element.name}}</div>
<!--          </transition-group>-->
        </draggable>
      </div>

      <div class="draggable-container" >
        <h1>进行中</h1>
        <draggable id='2' v-model="myArray1"  chosenClass="chosen" forceFallback="true" group="people"
                   animation="300" @start="onStart" @end="onEnd" >
<!--          <transition-group>-->
            <div class="item" v-for="element in myArray1" :key="element.id" style="margin: 10px">{{element.name}}</div>
<!--          </transition-group>-->
        </draggable>
      </div>

      <div class="draggable-container">
        <h1>已完成</h1>
        <draggable id='3' v-model="myArray2"  chosenClass="chosen" forceFallback="true" group="people"
                   animation="300" @start="onStart" @end="onEnd" >
<!--          <transition-group>-->
            <div class="item" v-for="element in myArray2" :key="element.id" style="margin:10px">{{element.name}}</div>
<!--          </transition-group>-->
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
        {people:'1',id:5,name:'www.itxst.com'},
        {people:'2',id:6,name:'www.baidu.com'},
        {people:'3',id:7,name:'www.taobao.com'},
        {people:'4',id:8,name:'www.google.com'}
      ],
      myArray2:[
        {people:'1',id:9,name:'www.itxst.com'},
        {people:'2',id:10,name:'www.baidu.com'},
        {people:'3',id:11,name:'www.taobao.com'},
        {people:'4',id:12,name:'www.google.com'}
      ]
    };
  },
  methods: {
    //开始拖拽事件
    onStart(){
      this.drag=true;
    },
    //拖拽结束事件
    onEnd(evt) {
      this.drag=false;
      const draggedItem = evt.item._underlying_vm_;
      const oldIndex = evt.oldIndex
      const newIndex = evt.newIndex;
      const fromList = evt.from.getAttribute('id');
      const toList = evt.to.getAttribute('id');

      console.log(evt.relatedContext)

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
