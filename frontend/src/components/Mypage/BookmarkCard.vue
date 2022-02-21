<template>
<div>
  <!-- <div class="card mb-3">
    <div class="row">
      <div class="col-md-4">
        <img 
          :src="bookmark.img" 
          alt="" class="img-fluid">
      </div>
      <div class="col-md-8">
        <h5 class="card-title">{{ bookmark.title }}</h5>
        <p class="card-text">{{ bookmark.storeName }}</p>
        <p class="card-text"><small class="text-muted">{{ bookmark.price }}원</small></p>
      </div>
    </div>
  </div> -->
  <div class="column-direction bookmark-product-wrapper">
    <i class="fas fa-times remove-icon" @click="removeBookmark"></i>
    <div class="bookmark-product-img-wrapper">
      <img class="bookmark-product-img" :src="bookmark.img" @click="routeToDetail">
        <a class="d-flex justify-content-end">
        </a>
      <!-- <i class="fas fa-heart bookmark-product-heart"></i> -->
    </div>
    <div class="d-flex flex-column justify-content-around">
      <div>
        <!-- 내 닉네임이 아닌 판매자 닉네임이 필요함... -->
        <!-- <div class="text-align-left user-nickname font-size-13">{{ bookmark.userNickname }}</div> -->
        <div class="text-align-left text-bold" @click="routeToDetail">{{ bookmark.title }}</div>
      </div>
      <div>
        <div class="text-align-left">{{ bookmark.price.toLocaleString() }}원</div>
        <div class="text-align-left product-rating">
          <i class="fas fa-star"></i>
          {{ bookmark.rating }}
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import router from '@/router'
import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'BookmarkCard',
  props: {
    bookmark: Object,
  },
  setup(props, {emit}) {
    const routeToDetail = function () {
      router.push(`/product/detail/${props.bookmark.productId}`)
    }
    const removeBookmark = function () {
      // emit('refresh')
      console.log('emit')
      axios({
        method: 'delete',
        url: `${SERVER_URL}/product/bookmark/`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('jwt')}`
        },
        data: {
          productId: props.bookmark.productId,
          userId: props.bookmark.userId
        }
      })
        // refresh 시켜야 함. 새로 받아와야 해
        .then(res=>{
          emit('refresh')
          console.log(res)
        })
        .catch(err=>console.log(err))
    }
    const test = function () {
      // emit('refresh')
      console.log('emit')
      axios({
        method: 'post',
        url: `${SERVER_URL}/product/bookmark/`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('jwt')}`
        },
        data: {
          productId: 3,
          userId: props.bookmark.userId
        }
      })
        // refresh 시켜야 함. 새로 받아와야 해
        .then(res=>{
          emit('refresh')
          console.log(res)
        })
        .catch(err=>console.log(err))
    }
    return {routeToDetail, removeBookmark, test}
  },
}
</script>

<style>
.bookmark-product-wrapper { 
  /* border: 1px solid black; */
  margin-bottom: 10px;
  width: 428px;
  height: 145px;
  position: relative;
}
.bookmark-product-wrapper > div {
  margin-left: 10px;
}
.bookmark-product-img-wrapper {
  margin: 10px;
  position: relative;
}
.bookmark-product-img {
  width: 125px;
  height: 125px;
}
.bookmark-product-heart {
  position: absolute;
  top: 3px;
  right: 3px;
  color: red;
}
.product-rating > i {
  padding-top: 3px;
  color: #FEE500;
}
.text-align-left {
  text-align: start;
}
.remove-icon {
  position: absolute;
  right: 10px;
  top: 12px;
}
.remove-icon:hover {
  color: pink;
}
</style>