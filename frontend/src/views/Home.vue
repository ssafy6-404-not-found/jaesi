<template>
  <div class="home"> 
    <!-- 메인 배너 -->
    <div id="carouselExampleControls" class="carousel slide w-100 mb-3" data-bs-ride="carousel" style="height: 250px;">
      <div class="carousel-inner">
        
        <!-- <div v-for="(category, index) in banners" :key="index" class="carousel-item"
          :class="{ 'active': index === 0 }"
          style="height: 250px; background-size: 100% !important;" 
          :style="{ 'background': 'no-repeat right url(' + category.bannerImg + ')'}"
        > -->
        <div class="carousel-item active">
          <img src="@/assets/main.png">
        </div>
        <div class="carousel-item">
          <img src="@/assets/new.png">
        </div>
        <div class="carousel-item">
          <img src="@/assets/signup.png">
        </div>
          <!-- &nbsp; -->
          <!-- <img :src="category.bannerImg" alt="..." class="d-block w-100 h-100"> -->
        <!-- </div> -->
        
      </div>
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>

    <!-- 서비스 목록 -->
    <div class="text-start" style="font-size: 24px; font-weight:bold;">
      이번 주 주목 재능
    </div>
    <section id="service-list" class="mb-3">
      <!-- <div class="d-flex justify-content-between flex-wrap"> -->
        <!-- <div class="product-card" @click="goToDetail(item.productId)" v-for="item in productList" :key="item.productId">
          <img :src="item.img" alt="" class="product-img">
          <span class="imti">
          <i class="fas fa-heart"></i>
          </span>
          <p class="saler" style="font-size:12px; text-align:left; margin-bottom:0px">{{ item.userNickname }}</p>
          <h4 style="margin-top:0px; margin-bottom:0px; text-align:left">{{ item.title }}</h4>
          <p style="font-size:10px; text-align:left">{{ item.price }}원</p>
        </div> -->
    <div class="service-container">
        <product-card 
          @click="goToDetail(item.productId)" 
          v-for="item in productList" 
          :key="item.productId"
          :product="item"
          :isRanking="false"></product-card>

    </div>
      <!-- </div> -->

      <router-link class="text-dark" style="text-decoration: none;" :to="{ name: 'Product', params: { categoryId: 1 } }">
        <div class="text-center mx-auto" style="width: 400px; height: 46px; line-height:46px; border: 1px solid #878787; font-size: 16px;">
          전문가의 재능 더보기
        </div>
      </router-link>
    </section>

    <!-- 라이브 목록 -->
    <div class="text-start" style="font-size: 24px; font-weight:bold;">
      진행중인 라이브
    </div>
    <section id="live-list" class="mb-3">
      <div class="live-videos-container">
        <live-card v-for="video in onairList" :video="video" :key="video.id" />
      </div>

      <router-link class="text-dark" style="text-decoration: none;" :to="{ name: 'Live' }">
        <div class="text-center mx-auto" style="width: 400px; height: 46px; line-height:46px; border: 1px solid #878787; font-size: 16px;">
          라이브 방송 더보기
        </div>
      </router-link>
    </section>
  </div>
</template>

<script>
import LiveCard from '@/views/Live/Components/Live-card.vue'  
import ProductCard from '@/components/Product/ProductCard.vue'  

import axios from 'axios'

const SERVER_URL = process.env.VUE_APP_SERVER_URL
// const SERVER_URL = "http://i6a404.p.ssafy.io:8000"

export default {
  name: 'Home',
  components: {
    LiveCard,
    ProductCard,
  },
  data: function() {
    return {
      productList: [],
      onairList: [],
      banners: [],
    }
  },
  methods: {
    goToDetail(pid) {
      this.$router.push({ name:'Detail', params: { proId: pid }})
    },
    getProductList: function() {
      axios({
        method: 'get',
        url: `${SERVER_URL}/product/`,
        // headers: 
        params: {
          category: 0,
          sortType: 1,
          currPage: 1,
          perPage: 4,
        }
      })
        .then(res => {
          // console.log(res.data)
          this.productList = res.data.content
        })
        .catch((err) => {
          console.log(err)
        })
    },
    getOnairList: function() {
      axios({
        method: 'get',
        url: `${SERVER_URL}/live/list`,
        // headers: 
        params: {
          category: 0,
          sortType: 0,
          currPage: 1,
          perPage: 5,
        }
      })
        .then(res => {
          this.onairList = res.data.content.splice(0, 5)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    // getBannerImg() {
    //   axios.get('https://i6a404.p.ssafy.io:8000/product/category')
    //     .then((res)=> {
    //       console.log(res)
    //       this.banners = res.data
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     })
    // },
  },
  mounted() {
    // this.getBannerImg()
    this.getProductList()
    this.getOnairList()
  },
}
</script>
<style>
  .imti {
    position: absolute;
    top: 3px;
    right: 3px;
    width: 14px;
    height: 14px;
    color: red;
  }

  .product-img {
    width: 244px;
    height: 244px;
  }

  .product-card {
    position: relative;
    max-width: 250px;
    float: left;
    margin-right: 10px;
  }

  .live-videos-container {
    display: flex;
    flex-wrap: wrap;
    /* width: 1152px; */
    width: 102%;
  }
.service-container {
  width: 1200px;
  display: flex;
  flex-wrap: wrap;
  margin: 0px;
  padding: 0px;
}
</style>