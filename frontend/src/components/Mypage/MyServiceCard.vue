<template>
  <div class="column-direction myservice-product-wrapper">
    <div class="img-wrapper">
      <div class="myservice-product-img">
        <img :src="product.img">
      </div>
    </div>
    <div class="d-flex flex-column myservice-text-wrapper my-3 ms-1">
      <div class="text-bold d-flex mb-4 font-size-13">상품번호: {{ product.productId }}</div>
      <div class="text-bold myservice-text d-flex font-size-18 mb-1">{{ product.title }}</div>
      <div class="text-bold myservice-text d-flex font-size-20 mb-4">{{ product.price.toLocaleString() }} 원</div>
      <div class="text-bold myservice-text d-flex font-size-13 mb-2">제작 기간: {{ product.productionPeriod }} 일</div>          
      <div class="d-flex justify-content-between">
        <div class="text-bold myservice-text d-flex font-size-12">판매 완료: {{ product.sell_count }} 건</div>  
        <div class="text-bold myservice-text d-flex font-size-12">{{ product.hit ? products.hit: 0 }} 건</div>  
      </div>
    </div>
  </div>

</template>

<script>
export default {
  name: 'ProductCard',
  props: {
    product: Object,
  }
}
</script>

<style>
.myservice-product-wrapper {
  border: solid 2px black;
  padding: 5px;
  margin-top: 20px;
  position:relative; 
  width: 90%; 
  height:218px; 
  border-radius:5px;
}
.img-wrapper {
  display: flex;
  align-items: center;
}
.myservice-product-img {
  margin-left: 10px;
  display: flex;
  align-items: center;
  width: 190px;
  height: 190px;
}
.myservice-product-img > img {
  width: 182px;
  height: 182px;
}
.myservice-text-wrapper {
  width: 70%;
}
.font-size-12 { font-size: 12px;}
.font-size-13 { font-size: 13px;}
.font-size-18 { font-size: 18px;}
.font-size-20 { font-size: 20px;}
</style>