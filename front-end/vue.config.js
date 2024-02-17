//用 vue-cli-service serve 启动就会用 vue.config.js；
// 用 webpack-dev-server 启动就会用 wepback.config.js（默认）。

module.exports = {
  lintOnSave: false,
  outputDir: "dist",
  // 开发环境显示报错位置 生产环境设置为false减少打包体积
  productionSourceMap: true,

  devServer: {
    proxy: {
      '/url': { // /url 表示拦截以/url开头的请求路径 【与request.js的url字段要一致】
        target: 'http://127.0.0.1:8080', // http://域名或ip
        changeOrigin: true, // 是否开启跨域
        pathRewrite: { // 重写路径
          '^/url': '' // 把/url变成空字串
        }
      }
    }
  }
}




