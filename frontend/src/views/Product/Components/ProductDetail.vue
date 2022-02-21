<template>
<div class="menu">
  <div class="row mr-0">
    <div class="col">
    <!-- 물품 이미지 -->
      <img :src="productDetail.img" alt="" style="width:550px; height:550px">

    </div>

    <!-- 구매하기창 -->
    <div class="col product-detail-purchase-container">
      <div class="product-detail-purchase-info">
        <div class="product-detail-purchase-nav">
          <div style="display: none;">
            {{bookmarkFilter()}}
          </div>
        <div class="product-detail-seller">{{ productDetail.userNickname }}</div>
        <div v-if="isLoggedIn">
          <div v-if="this.bookmarked == true">
            <!-- <div :class="{display_none:!bookmarked}"> -->
            <span style="top:3px; right:3px; color:red; height:21px; width:21px;">
              <i class="fas fa-heart cursor-pointer" @click="removeBookmark()">찜해제</i>
            </span>
          </div>
          <!-- bookmarked 가 false 일때 보여야 함  -->
          <div v-else>
            <span style="top:3px; right:3px; color:red; height:21px; width:21px;">
              <i class="far fa-heart cursor-pointer" @click="addBookmark()">찜하기</i>
            </span>
          </div>
        </div>
        </div>
        <div class="product-detail-title">{{ productDetail.title }}</div>
        <div class="product-detail-price">{{ price }}<span>원</span></div>
        <hr>
        <div class="product-purchase-details">
          <div class="product-purchase-detail">작업기간 : {{ productDetail.productionPeriod }} 일</div>
        </div>
        <button v-if="productDetail.custom" class="product-purchase-button" @click="makeChatRoom()">문의하기</button>
        <button v-else class="product-purchase-button" @click="goToBuy(productDetail.productId)">구매하기</button>
      </div>
    </div>
  </div>
  <!--상세,가격,환불정보 리뷰/평가-->
  <div class="info">
    <ul class="list-group list-group-horizontal">
      <li class="list-group-item" @click="this.isDetailInfo=true; this.isPriceInfo=false; this.isReviewInfo=false;"><a href="#detail-info">상세정보</a></li>
      <li class="list-group-item" @click="this.isDetailInfo=false; this.isPriceInfo=true; this.isReviewInfo=false;"><a href="#detail-price">환불규정</a></li>
      <li class="list-group-item" @click="this.isDetailInfo=false; this.isPriceInfo=false; this.isReviewInfo=true;"><a href="#detail-review">후기</a></li>
      <!-- <li class="list-group-item">환불규정</li> -->
    </ul>
    <div class="info-container">
      <DetailInfo v-if="isDetailInfo"
        :content="content"/>
      <PriceInfo v-if="isPriceInfo" 
        :price="price"/>
      <ReviewInfo v-if="isReviewInfo"
        :review="review"/>
    </div>
  </div>
</div>
</template>
<script>
import axios from 'axios'
import DetailInfo from './DetailInfo'
import PriceInfo from './PriceInfo'
import ReviewInfo from './ReviewInfo'

const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
  name: 'details',
  components: {
    DetailInfo,
    PriceInfo,
    ReviewInfo,
  },
  props:{
    showLogin: Boolean
  },
  data() {
    return {
      title:"details",
      productDetail: Object,
      isDetailInfo: true,
      isPriceInfo: false,
      isReviewInfo: false,
      content: '',
      price:'',
      review:[],
      bookmarked: Boolean,
    }
  },
  computed: {
    isLoggedIn: function() {
      return !!this.$store.state.Login.token
    },
  },
  methods: {
    goToBuy(productId) {
      if (this.isLoggedIn == false) {
        window.alert('로그인이 필요한 서비스입니다.')
      } else {
        this.$router.push({name:'Buy',params: {proId: productId,}})
      } 
    },
    bookmarkFilter() {
      axios.get(`${SERVER_URL}/product/bookmark`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('jwt')}`
        },
        params: {
          productId: this.productDetail.productId,
          userId: this.$store.state.userInfo.userId
        }
      })
      .then((res)=> {
        console.log(res.data.useBookmark)
        this.bookmarked = res.data.useBookmark
        return res.data.useBookmark
      })
      .catch((err) => {
        console.log(err)
      })
    },
    removeBookmark: function () {
      axios({
        method: 'delete',
        url: `${SERVER_URL}/product/bookmark/`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('jwt')}`
        },
        data: {
          productId: this.productDetail.productId,
          userId: this.$store.state.userInfo.userId,
        }
      })
        .then(res=>{
          this.bookmarkFilter()
          console.log(res)
        })
        .catch(err=>console.log(err))
    },
    makeChatRoom: function() {
      if (this.isLoggedIn == false) {
        window.alert('로그인이 필요한 서비스입니다.')
      } else {
        axios({
          method: "post",
          url: `${SERVER_URL}/chat`,
          headers: {
            Authorization: `Bearer ${localStorage.getItem('jwt')}`
          },
          data: {
            receiveUserId: this.productDetail.user,
            sendUserId: this.$store.state.Login.userInfo.userId
          }
        })
          .then(() => {
            // console.log(res.data.chatId)
            this.$emit('open-chat')
            // this.$refs.app.toggleChat()
          })
          .catch((err) => {
            // 400 에러 - 이미 존재하는 채팅방, 유저가 없음
            console.log(err)
            this.$emit('open-chat')
            // this.$refs.app.toggleChat()
          })
      }
    },
    addBookmark: function () {
      axios({
        method: 'post',
        url: `${SERVER_URL}/product/bookmark/`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('jwt')}`
        },
        data: {
          productId: this.productDetail.productId,
          userId: this.$store.state.userInfo.userId,
        }
      })
        .then(res=>{
          this.bookmarkFilter()
          console.log(res)
        })
        .catch(err=>console.log(err))
    },
  },
  mounted() {
    axios.get(`${SERVER_URL}/product/${this.$route.params.proId}`)
    .then((res)=> {
      this.productDetail = res.data
      this.content = res.data.content
      this.price = res.data.price.toLocaleString()
      console.log(this.price)
      this.review = res.data.reviews
    })
    .catch((err) => {
      console.log(err)
    })   
    this.bookmarkFilter()
  },
  // created() {
  //   this.bookmarkFilter()
  // }
  
}
</script>
<style>
.menu {
  width: 1152px;
  margin: auto;
}
.mr-0 {
  margin-right: 0px !important;
}
.info {
  margin-top: 20px;
}
.list-group-item {
  width: 100%;
}
/* #slidemenu {
  position: relative;
  width: 550px;
  height: 550px;
  border:1px solid black;
} */
.product-detail-purchase-container {
  position: relative;
  height: 550px;
  border:1px solid #c4c4c4;
}
.product-detail-purchase-info {
  margin: 20px;
}
.product-detail-seller {
  /* display: flex; */
  font-size:20px;
  color: #7c7c7c;
}
.product-detail-title {
  text-align: left;  
  margin-top: 10px;
  display: flex;
  font-size:30px;
  font-weight: bold;
}
.product-detail-price{
  display: flex;
  justify-content: flex-end;
  font-size: 25px;
  font-weight: bold;
}
.product-detail-price > span {
  padding-top: 4px;
  font-size: 21px;
  font-weight: 400;
}
.product-purchase-details { margin-top:50px; }
.product-purchase-detail {
  margin-top: 20px;
  display: flex;
  font-size: 20px;
}
.product-purchase-button {
  /* padding: 10px 180px; */
  width: 100%;
  padding: 10px 0px;
  font-size: 30px;
  background-color:#C1D3FE;
  border: 1px solid #C1D3FE;
  border-radius: 5px;
  color: white;
  margin-top: 200px;
}
.list-active {
  color: #C1D3FE;
}
.change_color {
  background-color: #C1D3FE;
}
.list-group-item > a {
  text-decoration: none;
  color: black;
}
.info-container {
  margin-bottom: 40px;
}
.product-detail-purchase-nav {
  display: flex;
  justify-content: space-between;
}
</style>