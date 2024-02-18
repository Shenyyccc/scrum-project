<template>
  <div class="containers">
    <div class="canvas" ref="canvas"></div>
    <div id="js-properties-panel" class="panel"></div>
  </div>
</template>

<script>
/* bpmn-js 主体依赖 */
import BpmnModeler from 'bpmn-js/lib/Modeler' // Bpmn-Modeler
/* bpmn-js 左侧工具栏 */
import 'bpmn-js/dist/assets/diagram-js.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css'
/* bpmn-js 右侧属性栏 */
import 'bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css'
import propertiesPanelModule from 'bpmn-js-properties-panel'
import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/camunda'
/* 要渲染的bpmn文件内容（实质为xml） */
import xml from '@/plugins/defaultXml' // 样例直接预置xml，实际应该从后台获得

export default {
  name: '',
  components: {},
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() {
  },
  // 生命周期 - 载入后, Vue 实例挂载到实际的 DOM 操作完成，一般在该过程进行 Ajax 交互
  mounted() {
    this.init()
  },
  data() {
    return {
      // bpmn建模器
      bpmnModeler: null,
      container: null,
      canvas: null
    }
  },
  methods: {
    init() {
      // 获取到属性ref为“canvas”的dom节点
      const canvas = this.$refs.canvas
      // 建模
      this.bpmnModeler = new BpmnModeler({
        // 基础画布
        container: canvas,
        // 控制板
        propertiesPanel: {
          parent: '#js-properties-panel'
        },
        // 插件
        additionalModules: [
          propertiesPanelModule,
          propertiesProviderModule,  // 右侧属性栏
        ],
      })
      this.createNewDiagram()
    },
    createNewDiagram() {
      console.log('xml', xml)
      // 将字符串转换成图显示出来
      this.bpmnModeler.importXML(xml, (err) => {
        if (err) {
          // console.error(err)
        } else {
          // 这里是成功之后的回调, 可以在这里做一系列事情
          this.success()
        }
      })
    },
    success() {
      // console.log('创建成功!')
    }
  }
}
</script>

<style scoped>
.containers{
  position: absolute;
  background-color: #ffffff;
  width: 80%;
  height: 100%;
}
.canvas{
  width: 100%;
  height: 100%;
}
.panel{
  position: absolute;
  right: 0;
  top: 0;
  width: 300px;
}
</style>
