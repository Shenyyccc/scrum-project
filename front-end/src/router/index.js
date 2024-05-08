import Vue from 'vue'
import Router from 'vue-router'
import Main from '../views/Main.vue'
import Register from "../components/common/register.vue";


Vue.use(Router)

const router=new Router({
  mode:"history",
  routes: [
    {path: '/', name: 'Main', component: Main},
    {path:'/Register', name:'Register', component:Register},
    {
      path:'/WorkSpace',
      name:'WorkSpace',
      component:()=>import('../views/WorkSpace/WorkSpace.vue'),
      redirect:'/WorkSpace/WorkPickup',
      children:[
        {path:'/WorkSpace/WorkList',component:()=>import('../components/Work/WorkList.vue')},
        {path:'/WorkSpace/Panel',component:()=>import('@/components/Work/Panel.vue')},
        {path:'/WorkSpace/WorkPickup',component:()=>import('@/components/Work/WorkPickup.vue')},
        {path:'/WorkSpace/PersonInformation',component:()=>import('@/components/common/PersonInformation.vue')},
        {path: '/WorkSpace/ModifyPass',component:()=>import('@/components/common/ModifyPass.vue')},
        {path: '/WorkSpace/Calendar',component:()=>import('@/components/Work/Calendar.vue')},
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
        {path:'/Backstage/PersonInformation',component:()=>import('@/components/common/PersonInformation.vue')},
        {path:'/Backstage/FormDesign/CreateDefinition', component:()=>import('@/components/BackStage/CreateDefinition.vue')},
        {path:'/Backstage/WorkflowTemplate', component:()=>import('@/components/BackStage/WorkflowTemplate.vue')},
        {path:'/Backstage/ModifyPass',component:()=>import('@/components/common/ModifyPass.vue')},
        {path:'/Backstage/WorkflowGraph',component:()=>import('@/components/BackStage/taskflowGraph.vue')},
        {path:'/Backstage/RunningTaskflow',component:()=>import('@/components/BackStage/RunningTaskflow.vue')},
        {path:'/Backstage/unfinishedTask',component:()=>import('@/components/BackStage/UnfinishedTask.vue')},
      ],
    },
    //403表示无权访问
    {path:'/403', name:'Auth', component:()=>import("@/views/Auth.vue")},
    //404表示没有该页面
    {path:"/taskView",name:'taskView',component:()=>import("@/components/TaskView.vue")},
    {path:'*', name:'Nofound', component:()=>import("@/views/Nopage.vue")},

  ]
})
export default router;

//1.用户跨域跳过登录，直接在浏览器输入后台地址可以直接访问敏感数据，不安全
//2.前端的数据不安全，因为可以人工篡改
router.beforeEach((to, from, next) => {

  let adminPath=['/Backstage','/Backstage/CreateDepartment','/Backstage/FormDesign/CreateTask','/Backstage/FormDesign',
                 '/Backstage/PersonInformation','/Backstage/FormDesign/CreateDefinition','/Backstage/WorkflowTemplate']
  const user=JSON.parse(sessionStorage.getItem('user'))

  if(user===null){
    //如果没登录就访问页面路径
    if(to.path!='/'&&to.path!='/403'){
      next("/403")
    }

  }else {

    //如果身份不是管理员并且访问管理员路径
    if(user.identity!=1&&user.identity!=2&&adminPath.includes(to.path)){
      next("/403")
    }

    //如果是游客，也什么页面都不能访问
    if(user.identity===0&&to.path!='/'){
      next("/403")
    }
  }

  next();
})
