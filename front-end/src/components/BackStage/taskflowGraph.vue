<template>
  <div ref="container" style="width: 40%;"></div>
</template>


<script>

import { Graph } from '@antv/x6';
import {getRunningTaskByWorkflowId} from "@/api/request";
import { GridLayout,DagreLayout, CircularLayout } from '@antv/layout';

export default {
  name: "taskflowGraph",
  props:['taskId'],
  data(){

    return{}
  },

  methods:{

    getColorByProgress(progress) {
        switch (progress) {
          case 0:
            return '#D3D3D3' //灰色
          case 1:
            return 'white';//白色
          case 2:
            return '#9AFF9A'; //绿色
          case 3:
            return 'red';
      }
    },
    initGraph() {
      this.graph = new Graph({
        container: this.$refs.container,
        width: 800,
        height: 400,
        interacting: false,  // 禁用所有交互功能,
        background: {
          color: '#ffffff', // 设置画布背景颜色
        },
        // grid: {
        //   size: 10,      // 网格大小 10px
        //   visible: true, // 渲染网格背景
        // },
        connecting: {
          router: 'manhattan',
          step:1
        },
      });
    },

    processData(data) {
      const nodes = data.map(task => ({
        id: task.taskkey.toString(),
        width: 100,
        height: 30,
        shape:'rect',
        attrs: {
          label: { text: task.taskname},
          body: {
            fill: this.getColorByProgress(task.progress),
            stroke: '#333',
            strokeWidth: 2,
          },
        },
      }));

      const edges = data.flatMap(task => task.pretaskkey.map(link=>({
        source: link.toString(),
        target: task.taskkey.toString(),
      })));

      return { nodes, edges };
    },
    fetchDataAndDraw() {
      getRunningTaskByWorkflowId(this.taskId).then(rsp=>{
        console.log(rsp.data.data)
        const data = this.processData(rsp.data.data);
        console.log(data)

        const dagreLayout = new DagreLayout({
          type: 'dagre',
          rankdir: 'LR',
          align: 'UL',
          nodesep: 10,
          ranksep: 70,
          controlPoints: true,
        });
        const newModel = dagreLayout.layout( data)

        this.graph.fromJSON(newModel)
        this.graph.centerContent()
      })


    },
  },
  mounted() {
  },


}
</script>

<style scoped>

</style>
