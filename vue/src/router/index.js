import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'

Vue.use(VueRouter)


const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login,
    hidden:true
  },
  // {
  //   path: '/home',
  //   name:'Home',
  //   component: Home,
  //   hidden: true,
  //   children:[{
  //     path:'/chat',
  //     name:'在线聊天',
  //     component:FriendChat
  //
  //   }]
  //
  // }
]

const router = new VueRouter({
  routes
})

export default router
