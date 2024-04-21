import Vue from 'vue'
import Router from 'vue-router'
import Main from '../views/Main.vue'
import Register from "../components/common/register.vue";
import FormDesign from "@/components/BackStage/FormDesign.vue";


Vue.use(Router)

const router=new Router({
  mode:"history",
  routes: [
    {
      path: '/',
      name: 'Main',
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
        {path:'/WorkSpace/Panel',component:()=>import('@/components/Work/Panel.vue')},
        {path:'/WorkSpace/WorkPickup',component:()=>import('@/components/Work/WorkPickup.vue')},
        {path:'/WorkSpace/PersonInformation',component:()=>import('@/components/Work/PersonInformation.vue')},
      ]

    },
    {
      path:'/Backstage',
      name:'Backstage',
      component:()=>import('../views/WorkSpace/Backstage.vue'),
      redirect:'/Backstage/CreateDepartment',
      children:[
        {path:'/Backstage/CreateDepartment',component:()=>import('@/components/BackStage/CreateDepartment.vue')},
        {path:'/Backstage/FormDesign', component:()=>import('@/components/BackStage/FormDesign.vue')},
        {path:'/Backstage/FormDesign/CreateTask', component:()=>import('@/components/BackStage/CreatTaskTemplate.vue')},
        {path:'/Backstage/PersonInformation',component:()=>import('@/components/BackStage/PersonInformation.vue')},
        {path:'/Backstage/FormDesign/CreateDefinition', component:()=>import('@/components/BackStage/CreateDefinition.vue')},
        {path:'/Backstage/WorkflowTemplate', component:()=>import('@/components/BackStage/WorkflowTemplate.vue')},
      ],
    }
  ]
})
export default router;

//1.用户跨域跳过登录，直接在浏览器输入后台地址可以直接访问敏感数据，不安全
//2.前端的数据不安全，因为可以人工篡改
router.beforeEach((to, from, next) => {
  if(to.path==='/'){
    next();
  }
  const user=sessionStorage.getItem('user')
  if(!user&&to.path!='/'){
    return next('/');
  }
  next();
})
