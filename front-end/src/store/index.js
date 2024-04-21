import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  //数据，相当于data
  state: {
    name:"张三",
    age:12,
    count:0,
  },
  getters: {

  },
  //里面定义方法，操作state方发
  mutations: {
      addNum(state,num){
        state.count=state.count+num;
      }
  },
  // 操作异步操作mutation
  actions: {

  },
  modules: {

  },
})
