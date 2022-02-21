<template>
  <div class="column-direction review-wrapper" :class="[detail?'':'width-735']">
    <div class="img-wrapper">
      <div class="writer-info-wrapper" :class="{display_none:!review.img}">
        <img :src="review.img">
      </div>
    </div>
    <div class="review-info-wrapper">
      <!-- {{review.id}} 제품번호는 제품 상세 리뷰에선 필요 없음. 작성자 안 뜬다. -->
      <div class="review-review-id" :class="[detail?'display_none':'']">주문번호: {{ review.orderId }} | 제품번호: {{review.productId}}</div> 
      <div class="font-size-13" :class="[detail?'display_none':'']">작성자: {{ review.buyerName }}</div>
      <div class="review-due-time">{{ formattedTime }}</div>
      <div class="review-content">{{ review.content }}</div>
    </div>
    <div class="product-rating review-product-rating">
      <i class="fas fa-star"></i>{{ review.rating }}
    </div>
  </div>
</template>

<script>
import {ref} from 'vue'

export default {
  name: 'ReviewCard',
  props: {
    review: Object,
    detail:Boolean
  },
  setup(props) {
    const formattedTime = ref(String)
    if (!!props.review.regTime == true) {
      formattedTime.value = props.review.regTime.slice(0,10)
    } else {
      formattedTime.value = ''
    }
    return { formattedTime }
  }
}
</script>

<style>
.review-wrapper {
  border-bottom: solid 1px #979797; 
  padding: 5px;
  /* margin-left: 40px;
  margin-top: 20px; */
  position:relative; 
  /* width:735px;  */
  height:218px; 
}
.width-735 {
  margin-left: 40px;
  margin-top: 20px;
  width: 735px !important;
}
.writer-info-wrapper {margin-left: 10px;}
.writer-info-wrapper > img {
  display: flex;
  align-items: center;
  width: 100px;
  height: 100px;
  margin-bottom: 10px;
}
.review-info-wrapper {
  margin-top: 30px;
  margin-left: 10px;
}
.review-info-wrapper > div {
  text-align: start;
}
.review-review-id {
  font-size: 13px;
}
.review-content {
  margin-top: 10px;
  font-size: 18px;
  font-weight: 500;
  padding-right: 20px;
}
.review-product-rating {
  position: absolute;
  bottom: 10px;
  right: 10px;
}
.review-product-rating > i {
  position: relative;
  top: -1px;
  margin-right: 3px;
  /* text-shadow:3px 6px rgba(255,165,0,.75) */
  text-shadow:1px 1px rgba(255,165,0,.75)
}
</style>