import Vue from 'vue'
import Router from 'vue-router'
import Main from '../views/Main.vue'
import Register from "../components/common/register.vue";


Vue.use(Router)

export default new Router({
  mode:"history",
  routes: [
    // {
    //   path: '/',
    //   name: 'Login',
    //   component: Main
    // },
    {
      path:'/',
      name:'Manage',
      component:()=>import('../views/Manage.vue'),
      redirect:"/home",
      children:[
        {path:'/home',name:'首页',component:()=>import('../views/Home.vue')},
        {path:'/user',name:'用户管理',component:()=>import('../views/User.vue')}
      ]
    },
    {
      path:'/Register',
      name:'Register',
      component:Register
    },
    {
      path:'/WorkSpace',
      name:'WorkSpace',
      component:()=>import('../views/WorkSpace/WorkSpace.vue'),
      children:[
        {path:'/WorkSpace/WorkList',component:()=>import('../components/Work/WorkList.vue')},
        {path:'/WorkSpace/Panel',component:()=>import('@/components/Work/Panel.vue')}
      ]
    },
    {
      path:'/Manage',
      name:'Manage',
      component:()=>import('../views/Manage.vue'),
      children:[
        {
          path: 'user', name:'User',
          component:()=>import('../views/User.vue'),
        },
        { path:'/Home/Panel',
          component:()=>import('@/components/Work/Panel.vue')},

        {
          path:'/Home/WorkList',
          component:()=>import('../components/Work/WorkList.vue')
        }
      ]
    },
  ]
})
