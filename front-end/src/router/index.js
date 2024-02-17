import Vue from 'vue'
import Router from 'vue-router'
import Main from '../views/Main.vue'
import Register from "../components/common/register.vue";

Vue.use(Router)

export default new Router({
  mode:"history",
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Main
    },
    {
      path:'/Register',
      name:'Register',
      component:Register
    },{
      path:'/WorkSpace',
      name:'WorkSpace',
      component:()=>import('../views/WorkSpace/WorkSpace.vue'),
      children:[
        {path:'/WorkSpace/WorkList',component:()=>import('../components/Work/WorkList.vue')},
        {path:'/WorkSpace/Panel',component:()=>import('@/components/Work/Panel.vue')}
      ]

    }
  ]
})
