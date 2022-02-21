<template>
  <div class="column-direction order-wrapper">
    <div>
      <div class="product-img-wrapper">
        <img :src="order.productImg">

      </div>
      <div class="text-bold">{{ order.userNickname }}</div>
    </div>
    <div class="w-490">
      <div class="order-status-and-number">
        <div class="order-active-status product-info text-bold">{{ order.endDate ? "작업완료" : "진행 중" }}</div>
        <div class="product-info order-number text-bold">주문번호: {{ order.ordersId }}</div>
      </div>

      <!-- <div class="font-size-16 justify-content-between ">
        <div class="order-product-info d-flex">{{ order.regTime.slice(0, 10) }}</div> -->
        <!-- <div v-if="!!order.endDate" class="order-product-info d-flex">
          <span v-if="isProfessional">작업완료일:&nbsp;</span>  
          <span v-else>주문완료일:&nbsp;</span>
          {{ order.endDate.slice(0, 10) }}
        </div> -->
      <!-- </div> -->

      <div class="order-product-info mypage-text-overflow margin-top-7 font-size-18">{{ order.productTitle }}</div>
      <div class="order-description mypage-text-overflow" :class="{visibility_hidden: !order.description}">
        {{ order.description }}&nbsp;
      </div>
      <div class="mypage-order-date mypage-text-overflow">
        <div>
          {{ order.regTime.slice(0, 10) }}&nbsp;
        </div>
        <div :class="{visibility_hidden: !order.endDate}">
          주문완료일: {{ order.endDate }}
        </div>
      </div>
      <!-- <div class="order-product-info font-size-20 margin-top-7"><span class="text-bold">{{ order.price.toLocaleString() }}</span> 원</div> -->
    </div>
    <div>
      <a 
        v-if="!!order.endDate && !isProfessional && !isWrittenReview" 
        :order="order" 
        class="order-rating-btn text-bold" 
        @click="openPopOver">후기 작성
      </a>
      <a 
      v-else-if="!order.endDate && isProfessional"
        :order="order" 
        class="order-rating-btn text-bold" 
        @click="endOrder">주문 완료하기</a>
    </div>
  </div>

</template>

<script>
import { useStore } from 'vuex'
import { computed, ref } from 'vue'

import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'ProductCard',
  props: {
    order: Object,
  },
  setup(props, {emit}) {
    console.log(props.order)
    const store = useStore()
    // 내가 무슨 페이지로 들어왔는지 확인해야 함. 
    const isProfessional = computed(()=> store.state.Mypage.isMypageProfessional)
    // 주문 상세 존재 여부
    const isHidden = computed(()=>(!props.order.description))
    // console.log(props.order)
    // 리뷰 작성창 열기
    const openPopOver = () => { 
      // 들어온 페이지에 따라 주소 설정 
      const popOverPath = `/review/${props.order.ordersId}?productId=${props.order.productId}&userId=${store.state.userInfo.userId}&create=${!isWrittenReview.value}`
      window.open(popOverPath, "new", "toolbar=no, menubar=no, scrollbars=yes, resizable=no, width=500, height=800, left=0, top=0" )
    }
    const isWrittenReview = ref(Boolean)
    if (props.order.isWrittenReview === true) {
      isWrittenReview.value = true
    } else {
      isWrittenReview.value = false
    }

    const today = new Date()
    const year = today.getFullYear()
    const month = today.getMonth() + 1
    const day = today.getDate()

    const todayFormat = `${year}-${month >= 10 ? month : '0' + month}-${day >= 10 ? day : '0' + day}`

    const endOrder = function () {
      // console.log(props.order.ordersId)
      // console.log(todayFormat)
      // console.log('주문 완료 - enddate 넣기')
      axios({
        method: 'put',
        url: `${SERVER_URL}/order`,
        headers: { Authorization: `Bearer ${localStorage.getItem('jwt')}` },
        data:{
          ordersId: props.order.ordersId,
          endDate: todayFormat
        }
      })
        .then(res=>{
          emit('refresh')
          console.log(res)
        })
        .catch(err=>{
          console.log(err)
        })
    }

    return { isProfessional, openPopOver, isHidden, isWrittenReview, endOrder}
  },
}
</script>

<style>
.order-wrapper {
  position:relative; 
  width:735px; 
  height:180px; 
  border-radius:5px;
  border: solid 2px black;
  padding: 5px;
  margin-left: 40px;
  margin-top: 20px;
}
.order-wrapper > div {
  margin-right: 10px;
}
.product-img-wrapper {
  width: 115px;
  height: 115px;
  margin: 10px;
  position: relative;
  margin-top: 20px;
}
.product-img-wrapper > img {
  height: 115px;
  width: 115px;
}
.order-status-and-number {
  display: flex;
  /* align-items: flex-end; */
  font-size: 13px;
  margin-top: 12px;
}
.order-number {
  padding-top: 5px;
}
.product-info  {
  margin-top: 8px;
  margin-right: 20px;
}
.order-active-status {
  background-color: #C1D3FE;
  border-radius: 10px;
  font-size: 12px;
  padding: 4px;
  width: 56px;
}
.order-product-info {
  margin-top: 10px;
  display: flex;
  margin-top: 3px;
}
.order-reg-date {
  display: flex;
  margin-top: 2px;
  font-size: 18px;
}
.order-rating-btn {
  position:absolute; 
  bottom: 10px; 
  right: 20px;
  text-decoration: none;
  color: white;
  padding: 5px 10px;  
  background-color: #004BFE;
  /* border: 1px solid #004BFE; */
  border-radius: 4px;
  font-size: 12px;
}
.order-rating-btn:hover {
  cursor: pointer;
  background-color: #C1D3FE;
  color: white;
}
.mypage-text-overflow {
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: start;
}
.margin-top-7 { margin-top: 7px;}
.w-490 { width: 530px;}
.w-480 { width: 480px;}
.font-size-20 { font-size: 20px;}
.hover-cursor-pointer { cursor: pointer; }
.order-description {
  font-size: 13px; 
  margin-top: 5px;
}
.mypage-order-date {
  font-size: 13px;
  display: flex;
  margin-top: 2px;
  margin-left: 2px;
  justify-content: space-between;
  margin-right: 200px;
}
.font-size-16 {font-size: 16px;}
.font-size-18 {font-size: 18px;}

</style>