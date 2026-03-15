import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import NProgress from 'nprogress'
import { useUserStore } from '@/stores/user'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: () => import('@/layouts/MainLayout.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        component: () => import('@/views/home/index.vue'),
        meta: { title: '首页' },
      },
      {
        path: 'product/:id',
        name: 'ProductDetail',
        component: () => import('@/views/product/detail.vue'),
        meta: { title: '商品详情' },
      },
      {
        path: 'category/:id',
        name: 'Category',
        component: () => import('@/views/product/list.vue'),
        meta: { title: '商品列表' },
      },
      {
        path: 'search',
        name: 'Search',
        component: () => import('@/views/product/search.vue'),
        meta: { title: '搜索结果' },
      },
      {
        path: 'cart',
        name: 'Cart',
        component: () => import('@/views/cart/index.vue'),
        meta: { title: '购物车', requiresAuth: true },
      },
      {
        path: 'order',
        name: 'Order',
        component: () => import('@/views/order/index.vue'),
        meta: { title: '我的订单', requiresAuth: true },
      },
      {
        path: 'order/:id',
        name: 'OrderDetail',
        component: () => import('@/views/order/detail.vue'),
        meta: { title: '订单详情', requiresAuth: true },
      },
      {
        path: 'checkout',
        name: 'Checkout',
        component: () => import('@/views/order/checkout.vue'),
        meta: { title: '结算', requiresAuth: true },
      },
      {
        path: 'user',
        name: 'UserCenter',
        component: () => import('@/views/user/index.vue'),
        meta: { title: '个人中心', requiresAuth: true },
      },
    ],
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/auth/login.vue'),
    meta: { title: '登录' },
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/auth/register.vue'),
    meta: { title: '注册' },
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/error/404.vue'),
    meta: { title: '页面未找到' },
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(_to, _from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  },
})

router.beforeEach((to, _from, next) => {
  NProgress.start()
  
  document.title = `${to.meta.title || '首页'} - Shopping Cloud`
  
  const userStore = useUserStore()
  
  if (to.meta.requiresAuth && !userStore.isLoggedIn) {
    next({
      name: 'Login',
      query: { redirect: to.fullPath },
    })
  } else {
    next()
  }
})

router.afterEach(() => {
  NProgress.done()
})

export default router
