<template>
  <div class="not_found">
    <p>
      Page will redirect to Home after <span>{{ time }}</span> seconds，<br>
      You can also click here to jump to <a href="/">Home</a>
    </p>
  </div>
</template>

<script>
// 这里可以导入其他文件（比如：组件，工具js，第三方插件js，json文件，图片文件等等）

export default {
  name: '',
  components: {

  },
  // 定义属性
  data() {
    return {
      time: '10',
      time_end: null
    }
  },
  // 计算属性，会监听依赖属性值随之变化
  computed: {},
  // 监控data中的数据变化
  watch: {},
  // 方法集合
  methods: {
    GoIndex() {
      let _t = 9
      this.time_end = setInterval(() => {
        if (_t !== 0) {
          this.time = _t--;
        } else {
          this.$router.replace('/')
          clearTimeout(this.time_end)
          this.time_end = null
        }
      }, 1000)
    }
  },
  // 生命周期 - 创建完成（可以访问当前this实例）
  created() {
    this.GoIndex()
  },
  // 生命周期 - 挂载完成（可以访问DOM元素）
  mounted() {

  },
  beforeCreate() { }, // 生命周期 - 创建之前
  beforeMount() { }, // 生命周期 - 挂载之前
  beforeUpdate() { }, // 生命周期 - 更新之前
  updated() { }, // 生命周期 - 更新之后
  beforeDestroy() { }, // 生命周期 - 销毁之前
  destroyed() {
    clearTimeout(this.time_end)
    this.time_end = null
  }, // 生命周期 - 销毁完成
  activated() { }, // 如果页面有keep-alive缓存功能，这个函数会触发
}
</script>

<style scoped lang='less'>
.not_found {
  width: 100%;
  height: 100%;
  background: url('../../static/404.png') no-repeat;
  background-position: center;
  background-size: cover;

  p {
    position: absolute;
    top: 50%;
    left: 20%;
    transform: translate(-50%, 0);
    color: #fff;
    span{
      color: orange;
      font-family: '仿宋';
      font-size: 25px;
    }
    a {
      font-size: 30px;
      color: aqua;
      text-decoration: underline;
    }
  }
}
</style>
