<template>
<div class="mypage-container">
  <div class="mypage-wrapper">
    <sidebar></sidebar>
    <section class="main-content">
      <h1 v-if="!isProfessional">주문내역</h1>
      <h1 v-else>주문관리</h1>
      <!-- <div :class="{display_none:!isNull}" class="no-content">
        주문이 존재하지 않습니다.
      </div> -->
      <div v-if="orders.length != 0">
        <product-card
          v-for="order in orders"
          :key="order.orderId"
          :order="order"
          @refresh="refreshOrders($event)"></product-card>
        <pagination
          :totalPage="totalPage"
          :currPage="currPage"
          @selectPage="changeCurrPage($event, pageNum)"
          @next="nextPagination($event, pageNum)"
          @pervious="perviousPagination($event, pageNum)"
          style="width:90%;"></pagination>
      </div>
      <div v-else class="no-content no-bookmark">
        <img src="@/assets/exclamation-mark.png">
        <div>내역이 없습니다.</div>
        <div v-if="!isProfessional" class="font-size-14">마음에 드는 상품을 주문해보세요</div>
      </div>
    </section>
  </div>
</div>
</template>

<script>
import ProductCard from '../../../components/Mypage/OrderCard.vue'
import Sidebar from './Sidebar.vue'
import Pagination from "../../Product/Components/Pagination.vue"

import { useStore } from 'vuex'
import { computed, ref } from 'vue'

export default {
  name: 'Orders',
  components: {
    ProductCard,
    Sidebar,
    Pagination,
  },
  setup() {
    const store = useStore()
    // const first = computed(()=>store.state.Mypage.pagination.first)
    // const last = computed(()=>store.state.Mypage.pagination.last)

    const currPage = ref(1)
    const isProfessional = computed(()=> store.state.Mypage.isMypageProfessional)
    const userId = useStore().state.Login.userInfo.userId

    if (isProfessional.value) {
      store.dispatch('setMyServiceOrders', currPage.value)
    } else {
      store.dispatch('setOrders', currPage.value)
    }

    const refreshOrders = function () {
      isProfessional.value ? store.dispatch('setMyServiceOrders', currPage.value) :  store.dispatch('setOrders', currPage.value)
    }
    
    const orders = computed(() => isProfessional.value ? store.state.Mypage.myServiceOrders : store.state.Mypage.orders)
    // console.log('주문내역 empty', orders.value.length)
    const totalPage = computed(()=>store.state.Mypage.pagination.totalPages)

    const changeCurrPage = function (pageNum) {
      if (currPage.value !== pageNum) {
        isProfessional.value ? store.dispatch('setMyServiceOrders', pageNum) : store.dispatch('setOrders', pageNum)
        currPage.value = pageNum
      }
    }
    const nextPagination = function (pageNum) {
      changeCurrPage(pageNum)
      currPage.value = pageNum
    }
    const perviousPagination = function (pageNum) {
      changeCurrPage(pageNum)
      currPage.value = pageNum
    }
    console.log(orders.value)
    const isNull = computed(()=>!orders.value.length)

    // if (!isNull.value) {
    //   console.log(orders.value)
    //   orders.value.reverse()
    // }
    return { 
      isNull,
      orders, isProfessional, totalPage, currPage, userId, changeCurrPage, nextPagination, perviousPagination, refreshOrders }
  },
}
</script>

<style>
.display_none {
  display: none;
}
</style>