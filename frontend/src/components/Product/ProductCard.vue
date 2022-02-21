<template>
<div :class="{category_product:!isRanking}" class="cursor-pointer">
  <div class="post" :class="{category_product2:!isRanking}">
    <div class="img-wrap">
      <img :src="product.img"  
      class="width-100 thumbnail"
      :class="{ranking_product_img:isRanking}">
    </div>
    <!-- <span class="imti"><i class="fas fa-heart"></i></span> -->
    <div class="product-card-seller">{{ product.userNickname }}</div>
    <div class="product-card-title">{{ product.title }}</div>
    <div class="product-card-price">
      <span class="text-bold">{{ product.price.toLocaleString() }}</span>
      <span>원</span>
    </div>
  </div>
</div>
</template>

<script>
export default {
  name: 'RankingProductCard',
  props: {
    product: Object,
    isRanking:Boolean,
  }
}
</script>

<style>
.thumbnail {
  width: 100%;
  height: auto;
}
.post:hover img {
  transform: scale(1.05, 1.05);
  transition-duration: 0.5s;
}
.post:hover .product-card-title{
  color: #C1D3FE;
}
.post img{
  transition-duration: 1s;
}
.category_product2 img {
  width: 267px;
  height: 267px;
}
.post .img-wrap {
  overflow: hidden;
}
.width-100 {width: 100%;}
.category_product {
  width: 287px;
  margin: 0px;
  padding: 0px;
}
.category_product2{
  margin-right: 20px;
  margin-bottom: 10px;
}
.product-card {
  /* position: relative; */
  width: 250px;
  /* float: left; */
  margin-right: 20px;
  margin-bottom: 10px;
}
.ranking_product_img {
  width: 244px;
  height: 244px;
}
.product-card-seller {
  font-size:12px; 
  text-align:left; 
  margin-bottom:0px;
  color: #c9c9c9;
  overflow: hidden;
  text-overflow: ellipsis;
}
.product-card-title {
  text-align: left;
  font-size: 14px;
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
}
.product-card-price {
  text-align: left;
  font-size: 13px;
  overflow: hidden;
  text-overflow: ellipsis;
}
.cursor-pointer {
  cursor: pointer;
}
</style>