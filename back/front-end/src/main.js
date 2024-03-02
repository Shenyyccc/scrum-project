// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from "element-ui";
import 'element-ui/lib/theme-chalk/index.css';
import './assets/globel.css'
import request from "@/utils/request";

Vue.config.productionTip = false

Vue.prototype.BASE_URL='http://localhost';

Vue.use(ElementUI,{size:"mini"});
Vue.prototype.request=request

/* eslint-disable no-new */
new Vue({
  router,
  // components: { App },
  render:h=>h(App),
  // template: '<App/>'
}).$mount('#app')


