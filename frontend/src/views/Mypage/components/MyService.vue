<template>
<div class="mypage-container">
  <div class="mypage-wrapper">
    <sidebar></sidebar>
    <div class="main-content">
      <h1>나의 서비스</h1>
      <a class="my-service-add-product d-flex justify-content-center align-items-center flex-column myservice-product-wrapper" @click.prevent="toggleService">
        <h1 class="text-bold"><i class="fas fa-archive"></i></h1>
        <h1 class="text-bold">서비스 추가하기</h1>
      </a>
      <!-- <div class="myservice-pagination" :class="{display_none:!isNull}">
        아직 등록된 서비스가 없습니다.
      </div> -->
      <div>
        <my-service-card
          v-for="product in myService"
          :key="product.productId"
          :product="product"></my-service-card>   
        <pagination
          class="myservice-pagination"
          :totalPage="totalPage"
          :currPage="currPage"
          @selectPage="changeCurrPage($event, pageNum)"
          @next="nextPagination($event, pageNum)"
          @pervious="perviousPagination($event, pageNum)"></pagination>
      </div>
    </div>
  </div>
  <service-modal v-if="showService" @close-service="toggleService"/>
</div>
</template>

<script>

import MyServiceCard from "../../../components/Mypage/MyServiceCard.vue"
import Sidebar from "./Sidebar.vue"
import ServiceModal from '@/components/Service/Service.vue'
import Pagination from "../../Product/Components/Pagination.vue"

import { useStore } from "vuex"
import { computed, ref } from "vue"

export default {
  name: 'Orders',
  components: {
    MyServiceCard,
    Sidebar,
    ServiceModal,
    Pagination
  },
  data: function() {
    return {
      showService: false,
    }
  },
  setup() {
    const currPage = ref(1)
    const store = useStore()
    const userId = useStore().state.Login.userInfo.userId

    store.dispatch('setMyService',currPage.value)

    const myService = computed(() => store.state.Mypage.myService)
    const totalPage = computed(()=>store.state.Mypage.pagination.totalPages)

    const changeCurrPage = function (pageNum) {
      if (currPage.value !== pageNum) {
        store.dispatch('setMyService', pageNum)
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

    const isNull = computed(()=>!myService.value.length)
    return { myService, totalPage, userId, changeCurrPage, nextPagination, perviousPagination, currPage, isNull }
  },
  methods: {
    toggleService: function() {
      this.showService = !this.showService
    },  
  },
  watch: {
    showService(newValue) {
      if (!newValue) {
        this.$store.dispatch('setMyService')
      }
    }
  }
}
</script>

<style>
.my-service-add-product {
  position:relative; 
  width:90%; 
  height:218px; 
  border-radius:5px;
  border: black 3px dashed;
  text-decoration: none;
  color: black;
  font-size: 40px;
  background-color: #c4c4c4;
}
.my-service-add-product:hover {
  background-color: white;
  color: #004BFE;
  border-color: #004BFE;
}
/* .my-service-add-product h1 {
  font-size: 40px;
} */
.myservice-pagination {
  padding: 5px;
  width: 90%; 
}
</style>
