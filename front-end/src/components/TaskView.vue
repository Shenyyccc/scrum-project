<template>
  <div style="margin-top: 100px">
    <div>
      <canvas ref="canvas" width="800" height="400"></canvas>
    </div>
<!--    <el-timeline>-->
<!--      <el-timeline-item timestamp="开始" placement="top">-->
<!--        <el-card>-->
<!--          <h4>起始阶段</h4>-->
<!--        </el-card>-->
<!--      </el-timeline-item>-->
<!--      <el-timeline-item timestamp="并行阶段" color="green">-->
<!--        <el-row>-->
<!--          <el-col :span="12">-->
<!--            <el-card>-->
<!--              <h4>并行任务 1</h4>-->
<!--              <p>任务详情 1...</p>-->
<!--            </el-card>-->
<!--          </el-col>-->
<!--          <el-col :span="12">-->
<!--            <el-card>-->
<!--              <h4>并行任务 2</h4>-->
<!--              <p>任务详情 2...</p>-->
<!--            </el-card>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!--      </el-timeline-item>-->
<!--      <el-timeline-item timestamp="结束" placement="bottom">-->
<!--        <el-card>-->
<!--          <h4>结束阶段</h4>-->
<!--        </el-card>-->
<!--      </el-timeline-item>-->
<!--    </el-timeline>-->
  </div>
</template>

<script>
export default {
  name: 'TaskView',
  mounted() {
    this.draw();
  },
  data() {
    return {
      activeStep: 1,  // 外部步骤条中活跃的步骤
      activeInnerStep1: 1, // 第一列内部步骤的活跃步骤
      activeInnerStep2: 1  // 第二列内部步骤的活跃步骤
    };
  },
  methods:{
    draw() {
      const ctx = this.$refs.canvas.getContext('2d');
      ctx.fillStyle = 'skyblue';
      ctx.fillRect(100, 50, 150, 100); // 画一个矩形表示一个流程节点

      ctx.beginPath();
      ctx.moveTo(250, 100); // 从一个节点中间位置开始
      ctx.lineTo(400, 100); // 画线到下一个节点的起始位置
      ctx.stroke();

      ctx.fillRect(400, 50, 150, 100); // 画下一个节点
    }
  }
}
</script>

<style scoped>
.box-card {
  margin: 20px auto;
  width: 90%;
}
</style>
