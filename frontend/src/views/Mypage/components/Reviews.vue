<template>
  <div class="mypage-container">
    <div class="mypage-wrapper">
      <Sidebar/>
      
      <div class="main-content">
        <h1>리뷰관리</h1>
        <!-- <div class="no-content" :class="{display_none:isNull}">
          작성된 리뷰가 없습니다.
        </div> -->
        <div v-if="Object.keys(reviews).length != 0">
          <review
            v-for="review in reviews"
            :key="review.content"
            :review="review"
            :detail="false">
          </review>
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
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Sidebar from './Sidebar.vue'
import Review from "../../../components/Mypage/ReviewCard.vue"
import Pagination from "../../Product/Components/Pagination.vue"

import axios from 'axios'
import { useStore } from "vuex"
import { ref, computed } from "vue"

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'MyReviews',
  components: {
    Sidebar,
    Review,
    Pagination
  },
  setup() {
    const currPage = ref(1)
    const reviews = ref(Object)
    const first = ref(Boolean)
    const last = ref(Boolean)
    const totalPage = ref(0)
    const sellerId = useStore().state.Login.userInfo.userId

    const getReviews = function(currPage, sellerId) {
      axios({
        method: 'get',
        url: `${SERVER_URL}/review/expert/${sellerId}`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('jwt')}`
        },
        params: {
          // currPage: currPage.value,
          currPage: currPage,
          perPage: 10,
          // sellerId: useStore().state.Login.userInfo.userId,
          sellerId: sellerId
        }
      })
        .then(res=>{
          console.log(res.data.content)
          reviews.value =  res.data.content
          first.value = res.data.first
          last.value = res.data.last
          totalPage.value = res.data.totalPages
        })
    }

    getReviews(currPage.value, sellerId)
    const isNull = computed(()=>!reviews.value.length)
    return { reviews, totalPage, currPage, getReviews, sellerId, isNull }
  },
  methods: {
    changeCurrPage(pageNum){
      if(this.currPage !== pageNum) {
        this.getReviews(pageNum, this.sellerId)
        this.currPage = pageNum
      }
    },    
    nextPagination(pageNum) {
      this.changeCurrPage(pageNum)
      this.currPage = pageNum
    },
    perviousPagination(pageNum) {
      this.changeCurrPage(pageNum)
      this.currPage = pageNum
    },
  }
}
</script>

<style>

</style>