<template>
  <div>
    <img :src="category.bannerImg" class="category-img">
  </div>

  <!-- 랭킹 -->
  <div class="product-ranking-container">
    <div class="product-ranking-nav" style="display:flex; justify-content:space-between">
      <h2 class="product-ranking-title">랭킹</h2>
      <div class="btn-group" style="align-items: center">
        <button type="button" class="btn btn-outline-info" style="width:40px; height:40px;" @click="goLeft()">◁</button>
        <button type="button" class="btn btn-outline-info" style="width:40px; height:40px" @click="goRight()">▷</button>
      </div>
    </div>
    <div class="ranking-products">
      <product-card
        v-for="product in ranks"
        :product="product"
        :key="'rank'+product.productId"
        class="product-card cursor-pointer"
        @click="goToDetail(product.productId)"
        :isRanking="true"></product-card>
    </div>
  </div>


  <!-- 카테고리별 -->
    <div class="product-category-nav">
      <h2 class="product-ranking-title" v-if="$route.params.categoryId==1">개발</h2>
      <h2 class="product-ranking-title" v-if="$route.params.categoryId==2">디자인</h2>
      <h2 class="product-ranking-title" v-if="$route.params.categoryId==3">핸드메이드</h2>
      <select class="product-sort-selected" @change="onChange()" v-model="selected">
        <option v-for="option in options" :key="option.value" v-bind:value="option.value">
          {{ option.text }}
        </option>
      </select>

    </div>

    <!-- 카테고리 상품 -->
    <div class="category-product-container">
      <product-card 
        :isLoggedIn="isLoggedIn"
        v-for="product in products" 
        :key="product.productId"  
        :product="product"
        @click="goToDetail(product.productId)"
        :isRanking="false">
      </product-card>
    </div>

    <pagination
      :totalPage="totalPage"
      :currPage="currPage"
      @selectPage="changeCurrPage($event, pageNum)"
      @next="nextPagination($event, pageNum)"
      @pervious="perviousPagination($event, pageNum)">
    </pagination>
</template>
<script>
import ProductCard from '../../components/Product/ProductCard.vue'
import Pagination from './Components/Pagination.vue'
import axios from "axios"

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'Product',
  components: {
    ProductCard,
    Pagination
  },
  data() {
    return {
      category: '',
      ranks: [],
      products: [],
      selected: 0,
      options: [
        { text: '최신순', value: 0, },
        { text: '인기순', value: 1, },
      ],
      totalPage: Number,
      currPage: 1,
      // 임시
      first: Boolean,
      last: Boolean,
      showLogin: Boolean,
    }
  },
  mounted() {
    this.getBannerImg()
    this.getCatProduct(this.$route.params.categoryId, 1)
    // 랭크
    axios.get(`${SERVER_URL}/product/rank`)
    .then((res)=> {
      this.ranks = res.data
    })
    .catch((err) => {
      console.log(err)
    })
  },

  methods: {
    onChange() {
      // console.log(this.selected)
      this.getCatProduct(this.$route.params.categoryId, this.currPage)
    },
    // 선택 페이지로 변경
    changeCurrPage(pageNum){
      if(this.currPage !== pageNum) {
        this.getCatProduct(this.$route.params.categoryId, pageNum)
        this.currPage = pageNum
      }
    },
    goLeft() {
      let content = document.querySelector(".ranking-products");
      content.scrollLeft -= 250
    },
    goRight() {
      let content = document.querySelector(".ranking-products");
      content.scrollLeft += 250
    },
    goToDetail(productId) {
      this.$router.push({
        name:'Detail',
        params: {
          proId: productId,
        }
      })
    },
    getBannerImg() {
      axios.get(`${SERVER_URL}/product/category`)
        .then((res)=> {
          console.log(res)
          this.category = res.data[this.$route.params.categoryId-1]
        })
        .catch((err) => {
          console.log(err);
        })
    },
    nextPagination(pageNum) {
      this.currPage = pageNum
    },
    perviousPagination(pageNum) {
      this.currPage = pageNum
    },
    // product 목록
    // res.data.totalPages => 총 페이지 갯수 
    // 페이지를 첨 생성할 때만 페이지네이션 만들어주면 된다.
    getCatProduct(catId, selectedPage) {
      axios.get(`${SERVER_URL}/product`, {
        params: {
          category: catId,
          sortType: this.selected,
          currPage: selectedPage,
          perPage: 8
        }
      })
      .then((res)=> {
        this.totalPage = res.data.totalPages
        this.products = res.data.content
        this.first = res.data.first
        this.last = res.data.last
      })
      .catch((err) => {
        console.log(err)
      })
    }
  },

  watch: {
    $route(to, from) {
      if(to.path != from.path) {
        this.getBannerImg()
        this.getCatProduct(to.params.categoryId, 1)
        this.currPage = 1
      }
    }
  }  
}
</script>

<style>
.category-img {
  object-fit: cover;
  width: 100%;
  height: 240px;
  object-position: 10% 35%;
}
.product-ranking-container {
  /* margin: 10px; */
  width: 100%;
}
.product-ranking-title {
  text-align: left;
  font-weight: bold;
  margin: 20px 0px 12px 0px;
  font-size: 24px;
}
.product-category-nav {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
}
.category-product-container {
  width: 1200px;
  display: flex;
  flex-wrap: wrap;
  margin: 0px;
  padding: 0px;
}
.ranking-products {
  display: flex;
  white-space: nowrap;
  overflow-x: scroll;
  /* justify-content: space-between; */
  scrollbar-width: thin;
}
.category-products{
  display: flex;
  width: 250px;
  height: 250px;
}
.product-sort-selected {
  height:30px;
  margin-top: 15px;
}
.ranking-products::-webkit-scrollbar {
  width: 5px;
  height: 8px;
}
.ranking-products::-webkit-scrollbar-track {
  background-color: transparent;
}
.ranking-products::-webkit-scrollbar-thumb {
  border-radius: 3px;
  width: 3px;
  background-color: #c4c4c4;
}
.ranking-products::-webkit-scrollbar-button {
  width: 0;
  height: 0;
}
</style>