import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import UserInfo from '../views/Mypage/components/UserInfo.vue'
import Orders from '../views/Mypage/components/Orders.vue'
import Bookmark from '../views/Mypage/components/Bookmark.vue'
import MyService from '../views/Mypage/components/MyService.vue'
import Reviews from '../views/Mypage/components/Reviews.vue'
import Detail from '../views/Product/Components/ProductDetail.vue'
import Buy from '../views/Product/Components/Buy.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/signup',
    name: 'Signup',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "signup" */ '../views/Signup/Signup.vue')
  },  
  {
    path: '/mypage/professional',
    name: 'ProfessionalUserInfo',
    component: UserInfo,
  },
  {
    path: '/mypage/general',
    name: 'GeneralUserInfo',
    component: UserInfo,
  },    
  {
    path: '/mypage/orders',
    name: 'Orders',
    component: Orders,
  },  
  {
    path: '/mypage/bookmarks',
    name: 'Bookmarks',
    component: Bookmark,
  },   
  {
    path: '/mypage/reviews',
    name: 'Reviews',
    component: Reviews,
  },  
  {
    path: '/mypage/expert/order',
    name: 'ExpertOrder',
    component: Orders,
  },  
  {
    path: '/mypage/myservice',
    name: 'MyService',
    component: MyService,
  },   
  {
    path: '/product/:categoryId',
    name: 'Product',
    component: () => import('../views/Product/Product.vue')
  },
  {
    path: '/product/detail/:proId',
    name: 'Detail',
    component: Detail,
  },
  {
    path: '/live/',
    name: 'Live',
    component: () => import('../views/Live/Live.vue')
  },
  {
    path: '/live/:liveRoomId',
    name: 'LiveRoom',
    component: () => import('../views/Live/Components/Live-room.vue'),
    props: true
  },
  {
    path: "/review/:orderId",
    name: "CreateReivew",
    component: () => import("../components/Mypage/CreateReview.vue"),
  },
  {
    path: '/product/detail/buy/:proId',
    name: 'Buy',
    component: Buy,
  },
  {
    path: '/pay/result/Success',
    name: 'Pay',
    component: () => import("../views/Pay/Pay.vue"),
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
