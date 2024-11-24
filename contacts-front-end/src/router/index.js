import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)

//配置路由
const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('../views/HomeView.vue')
  },
  {
    path: '/star',
    name: 'star',
    component: () => import('../views/StarView.vue')
  },
  {
    path: '/details',
    name: 'my-details',
    component: () => import('../views/DetailView.vue')
  },
]

const router = new VueRouter({
  routes
})

export default router
