<template>
  <div>
    <div class="signup-header">
      <div class="logo">
        재능마켓
      </div>
    </div>

    <div class="mt-3">
      <h2 class="m-0 text-start">이메일 인증</h2>
      <i class="fas fa-envelope-open-text" style="font-size: 100px; color: #C1D3FE;"></i>
      <p style="font-size: 24px;">계정 분실에 대비하여<br><strong style="color: #72B3FF;">이메일 주소 인증</strong>이 필요합니다.</p>
    </div>
    <hr>
    <div class="d-grid text-start" style="grid-template-columns: 3fr 1fr;">
      <div>
        <div>{{ email }}</div>
        <div>위 메일로 인증번호를 전송합니다.</div>
      </div>
      <div>
        <button class="rounded w-100 bg-white" style="height: 30px;" @click="sendEmailToken">전송요청</button>
      </div>
    </div>
    <hr>
    <form action="" class="my-3 text-start">
      <input type="text" placeholder="인증 번호 입력" class="rounded email-input" v-model="token" style="width: 85%; height: 40px;">
      <input type="submit" value="확인" class="float-end rounded border-0" style="width: 10%; height: 40px; background-color: #C1D3FE;" @click.prevent="checkEmailToken">
      <div>인증번호는 최대 10분간만 유효합니다.</div>
    </form>
    <div class="text-start" style="background-color: #E9E9E9; border-top: 1px solid #C4C4C4; border-bottom: 1px solid #C4C4C4;">
      <p>인증메일을 받지 못하셨나요?</p>
      <ul style="color: gray;">
        <li>혹시 인증번호를 메일로 받지 못하셨나요?<br>정확한 이메일 주소를 등록하셨는지 확인해주세요.</li>
        <li>만약, 잘못된 이메일 주소를 등록하셨다면 이메일 주소를 변경해주세요. (이메일 주소 변경 방법)</li>
      </ul>
    </div>
    <!-- <button class="w-100 border-0 mt-3 text-white" style="height: 52px; background-color: #C7C7C7; font-size: 24px;">시작하기</button> -->
  </div>
</template>
<script>
import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'SignupEmail',
  props: {
    email: String ,
  },
  methods: {
    sendEmailToken: function() {
      axios({
        method: 'get',
        url: `${SERVER_URL}/user/email-token-send/${this.email}`,
      })
        .then(res => {
          // 인증번호 발송 완료
          console.log(res.data.statusCode)
        })
        .catch((err, res) => {
          console.log(err, res)
          // message 종류에 따라 구분
          // alert(res.message)
        })
    },

    checkEmailToken: function() {
      axios({
        method: 'post',
        url: `${SERVER_URL}/user/email-token-check/`,
        data: {
          email: this.email,
          token: this.token
        }
      })
        .then(res => {
          console.log(res.data.statusCode)
          this.$emit('email-success')
        })
        .catch((err, res) => {
          console.log(err, res)
        })
    }
  },
  // mounted 되었을 때 이메일 인증번호 발송
  mounted: function() {
    this.sendEmailToken()
  },
}
</script>
<style>
  .email-input {
    font-size: 14px;
    box-shadow: 0 2px 5px -2px rgba(0,0,0,0.5);
    border: 2px solid black; 
    text-indent: .5rem;
  }
</style>
