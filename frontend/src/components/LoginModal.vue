<template>
  <div class="modal-mask">
    <div class="modal-wrapper">
      <div class="modal-container">
        <!-- 모달 닫기  -->
        <a class="d-flex justify-content-end">
          <i class="fas fa-times" @click="closeModal"></i>
        </a>

        <!-- 로그인 기능 -->
        <div class="modal-body">
          <div class="login-main-container">
            <div class="login-main-wrapper">
            <!-- 로그인 입력창 -->
              <section class="login-section-wrapper" @keyup.enter="userLogin">
                <div class="login-modal-title text-bold">로그인</div>
                <div class="login-input-wrapper">
                  <input class="login-input" @keyup="checkEmail" placeholder="이메일을 입력해주세요." type="email" v-model="credentials.email">
                  <i v-if="isEmailValidated === true" class="fas fa-check-circle icon-absolute fa-lg confirmed"></i>
                  <i v-else-if="isEmailValidated === false" class="fas fa-times-circle icon-absolute fa-lg unconfirmed"></i>
                </div>
                <div class="login-input-wrapper">
                  <input id="loginPw" @keyup="checkPassword" placeholder="비밀번호를 입력해주세요." class="login-input" type="password" v-model="credentials.password">
                  <i v-if="isPwValidated === true" class="fas fa-check-circle icon-absolute fa-lg confirmed"></i>
                  <i v-else-if="isPwValidated === false" class="fas fa-times-circle icon-absolute fa-lg unconfirmed"></i>
                </div>
                <div class="login-err-message" >					
                  <div v-if="hasErrorOccurred">입력한 정보와 일치하는 계정이 존재하지 않습니다.</div>
                  <div v-if="hasErrorOccurred">이메일 또는 비밀번호를 다시 확인해주세요.</div>
                </div>
                <div class="login-signup-button-wrapper">
                  <!-- isEmailValidated, isPwValidated 둘다 true 면 로그인 버튼 활성화 -->
                  <button v-if="isEmailValidated && isPwValidated === true" @click="userLogin" class="activated">로그인</button>
                  <button v-else disabled class="unactivated">로그인</button>
                </div>

            </section>

            <!-- 회원가입 버튼 -->
            <section class="login-section-wrapper">
              <div class="signup-title">아직 계정이 없으신가요?</div>
							<a @click="routeToSignup" class="border-focused signup-button">이메일로 회원가입</a>
              <!-- <div class="login-signup-button-wrapper">
                <button @click="routeToSignup" class="border-focused">이메일로 회원가입</button>
              </div> -->
            </section>

          </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useStore } from  'vuex'
import router from '@/router'
import axios from 'axios'

export default {
  props:{
    toggleLogin: Function,
  },
  setup(props, {emit}) {
    // 로그인창 닫기
    const closeModal = function () {
      emit('toggleLogin')
    }

    // 이메일, 비밀번호 형식이 검증 확인 
		const credentials = ref({ email:'', password:'' })		
    const isEmailValidated = ref('null')
		const isPwValidated = ref('null')
    // 에러 메시지 반환 체크용
		const hasErrorOccurred = ref(false) 

		// 1. 이메일 유효성 검증 - 리팩토링
		const checkEmail = function() {
			const mailFormat = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i
			if (credentials.value.email == '') {
				isEmailValidated.value = 'null'
				return 'null'
			} else {
				if (mailFormat.test(credentials.value.email)) {
					isEmailValidated.value = true
					return true
				} else {
					isEmailValidated.value = false
					return false
				}}}

		// 2. 비밀번호 길이 검증 - 리팩토링
		const checkPassword = function () {
			if(credentials.value.password === '') {
				isPwValidated.value = 'null'
				return 
			}
			if(credentials.value.password.length >= 8) {
				isPwValidated.value = true
			} else {
				isPwValidated.value = false
			}
		}		
		
		// 일반 로그인 
		const SERVER_URL = 'https://i6a404.p.ssafy.io:8000'
    const store = useStore()

		const userLogin = function() {
			axios({
				method: 'post',
				url: `${SERVER_URL}/user/login`,
				data: credentials.value
			})
				.then(res => {
					emit('toggleLogin')
					window.localStorage.setItem('jwt', res.data.accessToken)
					store.dispatch('setToken', res.data.accessToken)
					store.dispatch('setUserInfo', res.data)
					store.dispatch('setIndexUserInfo', res.data)
					if (!store.state.Login.userInfo.emailCertified) {
						router.push('/signup')
					}
					console.log('>>>>>>>>>>>>',props)
				})
				.catch(err => {
					console.log(err)
					hasErrorOccurred.value = true
					// document.getElementById("loginPw").focus()
				})
		}	

		// 로그인 모달 닫고 회원가입으로 이동
		const routeToSignup = function () {
      emit('toggleLogin')
			router.push('/signup')
		} 		
		
		return { credentials, hasErrorOccurred,
		userLogin, routeToSignup, checkEmail, checkPassword, isPwValidated, isEmailValidated, closeModal }
  }
}
</script>

<style>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}
.modal-wrapper {
  display: table-cell;
  vertical-align: middle;    
  top: 100px;
  left: 50%;
  transform: translate(-50%);
  position: absolute;
}
.modal-container {
  width: 500px;
  height: 468px;
  margin: 0px auto;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  font-family: Helvetica, Arial, sans-serif;
}
.modal-container > a {
  text-decoration: none;
  color: black;
}
.modal-container > a > i {
  cursor: pointer;
}
.modal-header h3 {
  margin-top: 0;
  color: #42b983;
}
.modal-body {
  margin-bottom: 30px;
  padding-top: 10px;
}
.modal-default-button {
  float: right;
}
/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */
.modal-enter {
  opacity: 0;
}
.modal-leave-active {
  opacity: 0;
}
.modal-enter .modal-container,
.modal-leave-active .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
:root{
	--body-background-color: #f5f6f7;
	--font-color: #4e4e4e;
	--border-color : #cfcfcf;
	--default-color: #C1D3FE;
	--default-border-color: #C1D3FE;
}
*{
	margin:0;
	padding:0;
}
body{	background:var(--body-background-color); }
.login-main-container{
	width:100%;
	display:flex;
	flex-direction:column;
	align-items:center;
	/* margin-top: 21px; */
}
.login-main-container .login-main-wrapper{ width:768px; }
.login-section-wrapper{
	/* padding-top: 60px; */
	display: flex;
	flex-direction: column;
	align-items: center;
}
.login-modal-title {
	/* margin-bottom: 13px; */
	width: 420px;
	display: flex;
	font-size: 24px;
}
.login-input-wrapper {
	position: relative;
}
.login-input{
	/* margin-top: 13px; */
	margin-top: 13px;
	width: 420px;
	height :52px;
	border: solid 1px var(	--border-color );
	background: white;
	border-radius: 5px;
	padding: 10px;
}
.login-input:focus {
	outline: none !important;
	border: 1px solid #5C89F4;
}
.login-err-message {
	margin-top: 5px;
	margin-bottom: 3px;
	width: 420px;
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	font-size: 12px;
	color: red;
}
.login-signup-button-wrapper button{
	margin-top: 3px;
	width: 420px;
	height :48px;
	font-size: 18px;
	border-radius: 5px;
}
.signup-button {
	font-size: 18px;
	border-radius: 5px;
	border: 2px solid #5C89F4;
	padding: 10.5px 135px;
	text-decoration: none;
}
.signup-button:hover {
	color: #004BFE;
	border: 2px solid #004BFE;
}
.activated {
	margin-top: 5px;
	background: var(--default-color);
	border: solid 1px var(--default-border-color);
	color: white;
}
.unactivated {
	margin-top: 5px;
	background: var(--border-color);
	border: solid 1px var(--border-color);
}
/* .login-stay-sign-in{
	width: 465px;
	height: 52px;
	display: flex;
	font-size: 15px;
	color: #4e4e4e;
	align-items: center;
	justify-content: space-between;
	border-bottom: solid 1px var(--border-color);
} */
/* .login-stay-sign-in i{
	font-size: 25px;
	color: #9ba1a3;
} */
/* .login-stay-sign-in span{
	padding-left: 5px;
	line-height: 25px;
} */
/* .Easy-sign-in-wrap{
	display: flex;
	flex-direction: column;
	align-items: center;
	padding-top: 40px;
} */
/* .Easy-sign-in-wrap h2{ font-size: 20px; } */
/* .Easy-sign-in-wrap .sign-button-list {
	padding-top:10px;
	list-style: none;
	width: 465px;
	display: flex;
	flex-direction: row;
	align-items: center;
  justify-content: center;
  padding-left: 0;
} */
/* .Easy-sign-in-wrap .sign-button-list li{
	padding-bottom: 10px;
  padding: 0 25px 10px 25px;
} */
/* .Easy-sign-in-wrap .sign-button-list li a{
	border: solid 1px var(--border-color);
	text-align: left;
	background: white;
  padding-left: 20px;
	font-size: 18px;
} */
/* .Easy-sign-in-wrap > ul > li > a { 
	width: 50px;
	height: 50px;
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 10px;
	border-radius: 50px;
	background: white;
	font-size: 20px;
	box-shadow: 3px 3px 3px rgb(0 0 0 / 40%), -3px -3px 5px rgb(0 0 0 / 10%);
} */
.signup-title {
	width: 420px;
	display: flex;
	font-size: 12px;
	color: #656161;
	margin-top: 20px;
}
.icon-absolute {
	position: absolute;
	top: 30px;
	right: 12px;
}
.confirmed { color: #5C89F4;}
.unconfirmed { color:red;}
.border-focused { 
	color:#5C89F4;
	background-color: white;
	border: 2px solid #5C89F4;
}

</style>