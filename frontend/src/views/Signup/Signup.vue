<template lang="">
  <div class="position-absolute top-0 start-0 w-100 h-100" style="background-color: #f5f6f7;">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
    <!-- <div style="display: flex; width: 540px; margin: 0 auto; padding: 1rem; justify-content: space-around;">
      <div @click="this.isForm=true; this.isEmail=false; this.isSuccess=false;">임시폼</div>
      <div @click="this.isForm=false; this.isEmail=true; this.isSuccess=false;">임시인증</div>
      <div @click="this.isForm=false; this.isEmail=false; this.isSuccess=true;">임시완료</div>
      <router-link to="/">home</router-link>
    </div> -->
    <!-- 홈 화면 이동 할 수 있도록 해야하나? -->

    <div class="signup-container" style="width: 540px; margin: 1rem auto; border: 1px solid black; padding: 1rem;">
      <signup-form v-if="isForm"
        @form-success="completeForm"
      />
      <signup-email v-if="isEmail"
        :email="email"
        @email-success="completeEmail"/>
      <signup-success v-if="isSuccess"
        :email="email"
        :name="name"
      />
    </div>
  </div>
</template>
<script>
import { computed } from 'vue'
import { useStore } from 'vuex'

import SignupForm from './Components/Signup-form'
import SignupEmail from './Components/Signup-email'
import SignupSuccess from './Components/Signup-success'

export default {
  name: 'Signup',
  data: function() {
    return {
      isForm: true,
      isEmail: false,
      isSuccess: false,
      email: '',
      name: '',
    }
  },
  components: {
    SignupForm,
    SignupEmail,
    SignupSuccess,
  },
  // 회원가입 이동시  로그인모달 끄기 
  mounted: function() {
    if (!useStore().showLogin) { useStore().dispatch('showLoginModal') }

    // 로그인 후 userInfo 있을 시 이메일 인증 확인
    var userInfo = { ...computed(()=> { return useStore().state.userInfo }).value }
    
    if (userInfo.name) {
      this.name = userInfo.name
      this.email = userInfo.email
      if (!userInfo.emailCertified) {
        this.isForm = false
        this.isEmail = true
      } else {
        // 이메일 인증되어있을 경우 홈화면으로 이동
        location.href="/"
      }
    }
  },
  methods: {
    completeForm: function({ name, email }) {
      this.name = name
      this.email = email

      console.log(this.isForm, this.isEmail)
      this.isForm = false
      this.isEmail = true
      console.log(this.isForm, this.isEmail)
    },
    completeEmail: function() {
      if(this.$store.state.userInfo){
        console.log('here')
        this.$store.state.userInfo.emailCertified = true
      }
      
      this.isEmail = false
      this.isSuccess = true
    }
  }
}
</script>
<style>

</style>