<template>
<div class="menu">
  <div style="font-size:32px; text-align:left">주문결제</div>
  <br>
  <div style="font-size:18px; text-decoration:underline; text-align:left">주문상품 정보</div>
  <br>
  <table class="table">
  <thead>
    <tr style="background-color:#F1F3F4">
      <th scope="col">상품정보</th>
      <th scope="col">개수</th>
      <th scope="col">상품금액</th>
      <th scope="col">배송비</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td style="border-right-style:solid; border-right-width:1px; text-algin:left;">
        <div style="display:inline-block; vertical-align:top;">
        <img :src="productInfo.img" alt="" style="width:70px; height:70px">
        </div>
        <div style="display:inline-block; margin-left:5px">
        <div style="text-align:left">{{ productInfo.userNickname }}</div>
        <div>{{ productInfo.title }}</div>
        </div>
      </td>
      <td style="border-right-style:solid; border-right-width:1px">
        <button type="button" class="btn btn-secondary btn-sm" id="decrease" @click="decreaseCounter">-</button>
        {{ this.counter }}
        <button type="button" class="btn btn-secondary btn-sm" id="increase" @click="increaseCounter">+</button>
        </td>
      <td style="border-right-style:solid; border-right-width:1px">{{ productInfo.price }}</td>
      <td>무료</td>
    </tr>
  </tbody>
</table>
<br>
<div class="buy-container">
  <div class="row">
    <div class="col-8">
  <div style="font-size:18px; text-decoration:underline; text-align:left;">배송 정보</div>
  <table class="table">
  <thead>
    <tr>
      <th scope="col" style="background-color:#F1F3F4">주문자</th>
      <td scope="col">
        {{ordererInfo.name}} | {{ordererInfo.phone}} | {{ ordererInfo.email }}
      </td>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th scope="row" style="background-color:#F1F3F4">배송지</th>
      <td>
        <div><input class="buy-input w-100" type="text" placeholder="받는 사람 이름" v-model="receiverName"></div>
        <div><input class="buy-input w-100" type="text" placeholder="핸드폰번호" v-model="receiverPhone"></div>
        <!-- 주소찾기 -->
        <div class="text-start">
          <!-- 우편번호 검색 -->
          <input type="text" placeholder="우편번호" class="d-inline-block w-25 rounded buy-input"
            v-model="zipcode" 
          >
          <div class="ms-3 text-center d-inline-block buy-input rounded cursor-pointer" style="width: 20%; text-indent: 0;" 
            @click="findAddress"
          >
            주소 찾기
          </div>
          <input type="text" placeholder="기본 주소" class="w-100 mt-2 rounded buy-input"
            v-model="baseAddress"
          >
        </div>
        <div><input class="buy-input w-100" type="text" placeholder="상세 주소" v-model="detailAddress"></div>
      </td>
      <!-- orderForm post입력 적용된경우 -->
      <!-- <td>
        <div><input type="text" placeholder="받는 사람 이름" v-model="orderForm."></div>
        <div><input type="text" placeholder="우편번호" v-model="orderForm.zipcode"></div>
        <div><input type="text" placeholder="기본 주소" v-model="orderForm.baseAddress"></div>
        <div><input type="text" placeholder="상세 주소" v-model="orderForm.detailAddress"></div>
      </td> -->
    </tr>
    <!--  배송시 요청사항 삭제예정 -->
    <!-- <tr>
      <th scope="row" style="background-color:#F1F3F4">배송시 요청사항</th>
      <td>
        <select class="form-select" aria-label="Default select example">
          <option selected>배송시 요청사항 선택</option>
          <option value="1">One</option>
          <option value="2">Two</option>
          <option value="3">Three</option>
        </select>
      </td>
    </tr> -->
  </tbody>
</table>
  </div>
  <div class="col-4">
    <div style="font-size:18px; text-decoration:underline; text-align:left;">요청사항</div>
      <table class="table">
  <thead>
    <tr>
      <th scope="col" style="background-color:#F1F3F4">요청 사항</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td scope="row">
        <input type="text" style="width:352px; height:160px;" placeholder="요청사항을 입력해주세요" v-model="description">
        <!-- <input type="text" style="width:352px; height:160px;" placeholder="요청사항을 입력해주세요" v-model="orderForm.description"> -->
      </td>
    </tr>
  </tbody>
</table>
  </div>
  <div class="col-8">
  <div style="font-size:18px; text-decoration:underline; text-align:left;">결제 정보</div>
  <table class="table">
    <tr>
      <th scope="col" style="background-color:#F1F3F4">결제수단</th>
      <td scope="col">
        <!-- <button type="button" class="btn btn-light" style="marginleft:5px">아임포트</button> | -->
        <button type="button" class="btn btn-light" :class="{click_blue:clicked}" @click="btnClick">카카오페이</button>
         <!-- |<button type="button" class="btn btn-light">무통장입금</button> -->
      </td>
    </tr>
</table>
  </div>
  <div class="col-4">
    <div style="font-size:18px; text-decoration:underline; text-align:left;">결제 금액</div>
<div class="card text-center">
  <div class="card-body" style="background-color:#F1F3F4;">
    <h5 class="card-title" style="text-align:left">물품가격 : {{ productInfo.price }} 원</h5>
    <p class="card-text" style="text-align:left">물품갯수 : {{ this.counter }} 개</p>
    <p class="card-text" style="text-align:left">배송비</p>
    <hr>
    <p class="card-text" style="text-align:left">최종 결제 금액 : {{ total }} 원</p>
    <a href="#" class="btn" style="background-color:#C1D3FE; color:white;" @click="makePayment">결제하기</a>
  </div>
</div>
  </div>
</div>
</div>  
</div>
</template>
<script>
import axios from 'axios'

axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=utf-8"
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*'
axios.defaults.headers.post['Access-Control-Allow-Headers'] = '*'

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'buys',
  data: function() {
    return {
      title:"buys",
      counter: 1,
      productInfo:[],
      receiverName: '',
      receiverPhone: '',
      zipcode: '',
      baseAddress: '',
      detailAddress: '',
      errors: [],
      errorshow: false,
      clicked: false,
      ordererInfo: Object,
    }
  },
  methods: {
    btnClick: function() {
      this.clicked = !this.clicked
    },
    increaseCounter: function() {
      this.counter += 1
    },
    decreaseCounter: function() {
      if(this.counter<=1){
        alert("1개 이상 골라야 합니다.");
      } else {
        this.counter -= 1
      }
    },
    validationCheck() {
      this.errors = [];
      if (!this.orderForm.baseAddress) {
        this.errors.push("기본주소를 입력해주세요")
      }
      if (!this.orderForm.detailAddress) {
        this.errors.push("상세주소를 입력해주세요")
      }
      if (!this.orderForm.zipcode) {
        this.errors.push("우편번호를 입력해주세요")
      }
      if (!this.orderForm.description) {
        this.errors.push("요청사항을 입력해주세요")
      }
      if (!this.errshow) {
        return true;
      }
    },
    findAddress: function() {
      new window.daum.Postcode({
        oncomplete: data => {
          var roadAddr = data.roadAddress

          // 우편번호와 주소 정보를 해당 필드에 넣는다.
          this.zipcode = data.zonecode
          this.baseAddress = roadAddr
        }
      }).open();
    },
    makePayment: function() {
      // 빈 데이터 체크
      if(!localStorage.getItem('jwt')) {
        alert('로그인 해주세요.')
        return false
      }
      axios({
        method: "post",
        url: `${SERVER_URL}/order`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('jwt')}`
        },
        data: {
          "buyerId": this.$store.state.Login.userInfo.userId,
          "count": this.counter,
          "description": this.description,
          "price": this.productInfo.price,
          "productId": this.productInfo.productId,
          "receiverName": this.receiverName,
          "sellerId": this.productInfo.user,
          "zipcode": this.zipcode,
          "baseAddress": this.baseAddress,
          "detailAddress": this.detailAddress,
          "receiverPhone": this.receiverPhone,
        }
      })
        .then((res) => {
          console.log(res)
          const orderId = res.data.orderId
          axios({
            method: "post",
            url: `${SERVER_URL}/pay`,
            headers: {
              Authorization: `Bearer ${localStorage.getItem('jwt')}`
            },
            data: {
              "buyerId": this.$store.state.Login.userInfo.userId,
              "count": this.counter,
              "orderId": orderId,
              "productId": this.productInfo.productId,
              "productName": this.productInfo.title,
              "serverBaseUrl": SERVER_URL.slice(0, SERVER_URL.lastIndexOf(':')),
              "taxFreePrice": 0,
              "totalPrice": this.productInfo.price,
            }
          })
            .then((res) => {
              location.href = res.data.next_redirect_pc_url
            })
            .catch((err) => {
              console.log(err)
            })
        })
        .catch((err) => {
          console.log(err)
        })

    },
  },
  mounted() {
    axios.get(`${SERVER_URL}/product/${this.$route.params.proId}`)
    .then((res)=> {
      console.log(res)
      this.productInfo = res.data
    })
    .catch((err) => {
      console.log(err)
    })

    axios({
      method:'get',
      url:`${SERVER_URL}/user/${this.$store.state.Login.userInfo.email}`,
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      },
    })
    // axios.get(`${SERVER_URL}/product/${this.$route.params.proId}`)
    .then((res)=> {
      console.log(res)
      this.ordererInfo = res.data
    })
    .catch((err) => {
      console.log(err)
    })

    this.ordererInfo 
  },
  computed: {
    total() {
      return this.counter * this.productInfo.price
    }
  }
}
</script>
<style>
  .buy-input {
    height: 24px;
    line-height: 24px;
    font-size: 14px;
    box-shadow: 0 2px 5px -2px rgba(0,0,0,0.5);
    border: 0.5px; 
    text-indent: .5rem;
    margin-bottom: .5rem;
  }
  .click_blue {
    color: blue !important;
    border: 1px solid blue !important;
  }
</style>