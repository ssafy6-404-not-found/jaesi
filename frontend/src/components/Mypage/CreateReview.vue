<template>
<div class="position-absolute top-0 start-0 w-100 h-100 bg-white">
  <div class="create-review-title text-bold">후기 작성</div>
  <div class="px-3">
    <div class="review-product-info-container">
      <img class="review-product-img" :src="product.img">
      <div class="review-product-info">
        <div class="review-product-seller">{{product.userNickname}}</div>
        <div class="review-product-title">{{product.title}}</div>
        <div class="review-product-seller">{{product.description}}</div>
        <div v-if="product.custom" class="review-product-detail-info">주문제작</div>
      </div>
    </div>
    <div class="review-rating-container">
      <div class="review-rating-title text-bold">별점을 매겨주세요</div>
      <div class="star-rating-container">
        <div class="star-rating">
          <input type="radio" id="5-stars" name="rating" value="5" v-model="ratings"/>
          <label for="5-stars" class="star">&#9733;</label>
          <input type="radio" id="4-stars" name="rating" value="4" v-model="ratings"/>
          <label for="4-stars" class="star">&#9733;</label>
          <input type="radio" id="3-stars" name="rating" value="3" v-model="ratings"/>
          <label for="3-stars" class="star">&#9733;</label>
          <input type="radio" id="2-stars" name="rating" value="2" v-model="ratings"/>
          <label for="2-stars" class="star">&#9733;</label>
          <input type="radio" id="1-star" name="rating" value="1" v-model="ratings"/>
          <label for="1-star" class="star">&#9733;</label>
        </div>
      </div>
    </div>
    <div class="review-content-container">
      <div class="review-rating-title text-bold">구매 후기를 작성해주세요</div>
      <div class="review-content-wrapper">
        <textarea 
          placeholder="최소 20자 이상으로 구매 후기를 남겨주세요." 
          @keyup="limitWord"
          v-model="reviewContent"></textarea>
          <span class="word-count">{{wordCount}} / 2,000</span>
      </div>
    </div>
    <div class="review-img-container" :class="{border_none:!!reviewImg}">
      <img :src="reviewImg" alt="" :class="{display_none:!reviewImg}">
      <i class="fas fa-times-circle" :class="{display_none:!reviewImg}" @click="deleteImage"></i>
      <label for="reviewImage" :class="{display_none:!!reviewImg}">
        <div class="review-img-upload-container">
          <i class="fas fa-camera test"></i>
          <div>사진 추가</div>
        </div>
      </label>
    </div>
    <input id="reviewImage" class="d-none" type="file" accept="image/png, image/jpeg" @change="selectImage">
    <div class="review-button-wrapper">
      <button class="review-cancel" @click="cancelCreate">취소</button>
      <button v-if="!isActive" class="review-create">등록</button>
      <button v-else class="review-create-active" @click="createReview">등록</button>
    </div>
  </div>
  <div id="snackbar">Some text some message..</div>
</div>

</template>

<script>
import { ref, computed} from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router'
// import { useStore } from 'vuex'
import firebase from 'firebase/app'
import 'firebase/storage'
// const SERVER_URL = process.env.VUE_APP_SERVER_URL
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'CreateReview',
  setup() { 
    // 상품 정보 
    // 상품 id 뽑아내기
    // 1. url 파라미터로 들어온 정보로 다시 axios 요청ㅜ
    const productId = useRoute().query.productId
    const product = ref(Object)
    const sellerId = ref(Number)
    axios({
      method: 'get',
      url: `${SERVER_URL}/product/${productId}`,
    })
      .then(res => {
        console.log(res)
        product.value = res.data
        sellerId.value = res.data.user
      })
      .catch(err => {
        console.log(err)
      })

    // 2. 이동하면서 vuex에 정보 저장. -> 목록 페이지에선 저장되는데 새창에선 안 뜬다...
    // 3. url에 다 우겨넣기    

    // 리뷰 내용 
    const reviewContent = ref('')
    const ratings = ref(0)
    // 글자수 계산
    const wordCount = computed(()=>reviewContent.value.length.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','))
    // 글자수 제한
    const limitWord = function () {
      if (reviewContent.value.length > 2000) {
        reviewContent.value = reviewContent.value.substring(0, 2000)
      }
    }

    // 등록 버튼 활성화
    const isActive = computed(()=>ratings.value && reviewContent.value.length > 20)
    
    // ***** 리뷰 생성 *****
    // userid 받기
    // 1. 새창 띄우면 vuex 변경 사항이 유지가 안 됨!!!!!!!  => plugin -> 실패해서 지움 vuex-persistedstate
    // const userId = computed(()=>useStore().state.userInfo.userId)
    // 2. 이전 페이지에서 넘기기 
    const userId = useRoute().query.userId   
    const orderId = useRoute().params.orderId
    // const sellerId
    
    const createReview = function () {
      axios({
        method: 'post',
        url: `${SERVER_URL}/review`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('jwt')}`
        },
        data: {
          content: reviewContent.value,
          rating: ratings.value,
          buyerId: userId,
          productId: productId, 
          img: reviewImg.value,
          sellerId: sellerId.value,
          ordersId: orderId,
        }
      })
        .then(res => {
          console.log(res)
          // 변경사항 반영을 위해서 새로고침 시키는데 내 로그인 정보 persist 시켜야 한다. 
          myFunction()
          opener.parent.location.reload()
          window.close()
        })
        .catch(err =>{
          console.log(err)
        })
    }

    const cancelCreate = () => {
      myFunction()
      window.close()
    }

    const reviewImg = ref(null)

    const selectImage = function(event) {
      // 이미지 등록 프로세스, url 가져오기
      const imageData = event.target.files[0];
      let fileName = imageData.name
      const storageRef = firebase.storage().ref(`images/${fileName}`).put(imageData)
      storageRef.on("state_changed", 
      snapshot => {
        console.log(snapshot);
        // 업로드 중입니다 loading pin
        // upload percentage = (snapshot.bytesTransferred/snapshot.totalBytes)*100
      },
      error => {
        console.log(error);
      },
      () => {
        storageRef.snapshot.ref.getDownloadURL().then( url => {
          console.log("성공")
          reviewImg.value = url
        })
      })
    }
    const deleteImage = function () {
      reviewImg.value = null
    }

    const myFunction = function () {
    // Get the snackbar DIV
  var x = document.getElementById("snackbar");

  // Add the "show" class to DIV
  x.className = "show";

  // After 3 seconds, remove the show class from DIV
  setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);    
    }
    
    return {limitWord, reviewContent, wordCount, isActive, ratings, createReview, product, cancelCreate,
      selectImage, reviewImg, deleteImage
    }
  }
}
</script>

<style>
.create-review-title {
  font-size: 20px;
  border-bottom: 2px solid black;
  padding: 10px 0px 8px 0px;
}
.review-product-info-container {
  border-bottom: 1px solid #929292;
  display: flex;
  padding: 20px 1px;
  margin-bottom: 10px;
}
.review-product-img {
  width: 100px;
  height: 100px;
  background-color: #929292;
}
.review-product-info {
  flex: 1;
  /* justify-content: start; */
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  margin-left: 10px;
}
.review-product-info > div {margin: 2px 0px;}
.review-product-seller {
  font-size: 13px;
  color: #929292;
}
.review-product-title {
  font-size: 14px;
  font-weight: bolder;
}
.review-product-detail-info {
  font-size: 13px;
  color: #525252;
}
.star-rating-container {
  /* margin: 2px 0px 6px 0px; */
  display: flex;
  justify-content: center;
}
.review-content-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.review-content-wrapper {
  width: 100%;
  position: relative;
  padding: 10px 10px 20px 10px;
  border: 1px solid #c4c4c4;
  margin-top: 5px;
}
.review-content-wrapper > textarea{
  resize: none;
  width: 100%;
  height: 250px;
  border: none;
}
.review-content-wrapper > span { color: #929292;}
.review-rating-title {
  font-size: 18px;
}
.review-img-container {
  margin-top: 10px;
  border: 1px dashed black;
  width: 100px;
  height: 100px;
  position: relative;
}
.review-img-container > img {
  height: 100px;
  width: 100px;
  object-fit: cover;
}
.review-img-upload-container {
  display: flex;
  flex-direction: column;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
}
.review-img-upload-container > i {
  font-size: 20px;
}
.review-img-upload-container > div {
  font-size: 12px;
  font-weight: bold;
  margin-top: 3px;
}
.review-button-wrapper {
  margin-top: 10px;
}
.review-button-wrapper > button {
  width: 150px;
  height: 40px;
  margin: 2.4px;
  margin-top: 15px;
  font-size: 20px;  
}
.review-cancel {
  color: #c4c4c4;
  background-color: white;
  border: 1px solid #c4c4c4;  
}
.review-create {
  background-color: #c4c4c4;
  border: 1px solid #c4c4c4;
  color: white;
}
.review-create-active {
  background-color: var(--default-color);
  border: 1px solid var(--default-color);
  color: white;
}
.word-count {
  position: absolute;
  bottom: 1px;
  right: 6px;
}
.star-rating {
  display:flex;
  flex-direction: row-reverse;
  font-size:1.5em;
  justify-content:space-around;
  padding:0 .2em;
  text-align:center;
  width:5em;
}
.star-rating input {
  display:none;
}
.star-rating label {
  color:#ccc;
  cursor:pointer;
}
.star-rating :checked ~ label {
  color:#f90;
}
.star-rating label:hover,
.star-rating label:hover ~ label {
  color:#fc0;
}
.border_none {
  border: none;
}
#nav {
  display: none;
}
/* The snackbar - position it at the bottom and in the middle of the screen */
#snackbar {
  visibility: hidden; /* Hidden by default. Visible on click */
  min-width: 250px; /* Set a default minimum width */
  margin-left: -125px; /* Divide value of min-width by 2 */
  background-color: #333; /* Black background color */
  color: #fff; /* White text color */
  text-align: center; /* Centered text */
  border-radius: 2px; /* Rounded borders */
  padding: 16px; /* Padding */
  position: fixed; /* Sit on top of the screen */
  z-index: 1; /* Add a z-index if needed */
  left: 50%; /* Center the snackbar */
  bottom: 30px; /* 30px from the bottom */
}

/* Show the snackbar when clicking on a button (class added with JavaScript) */
#snackbar.show {
  visibility: visible; /* Show the snackbar */
  /* Add animation: Take 0.5 seconds to fade in and out the snackbar.
  However, delay the fade out process for 2.5 seconds */
  -webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
  animation: fadein 0.5s, fadeout 0.5s 2.5s;
}

/* Animations to fade the snackbar in and out */
@-webkit-keyframes fadein {
  from {bottom: 0; opacity: 0;}
  to {bottom: 30px; opacity: 1;}
}

@keyframes fadein {
  from {bottom: 0; opacity: 0;}
  to {bottom: 30px; opacity: 1;}
}

@-webkit-keyframes fadeout {
  from {bottom: 30px; opacity: 1;}
  to {bottom: 0; opacity: 0;}
}

@keyframes fadeout {
  from {bottom: 30px; opacity: 1;}
  to {bottom: 0; opacity: 0;}
}
</style>