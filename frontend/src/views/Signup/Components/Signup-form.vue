<template>
  <div>
    <div class="signup-header d-flex justify-content-between">
      <h2 style="margin: 0;">회원가입</h2>
      <div class="logo" :style="{
        /* 'background-image': 'url(' + logo + ')',*/
        height: '33px', width: '33px', 'background-size': 'cover', 'text-indent': '-1e9em'}">
        재시
      </div>
    </div>

    <form class="mt-3 mx-auto" style="width: 90%;" @submit.prevent="userSignup">
      <div class="email">
        <p class="text-start p-0 m-0">이메일<span class="required text-danger">*</span></p>
        <div>
          <input type="text" placeholder="이메일을 입력해주세요" class="w-100 rounded signup-input"
            v-model="userInfo.email" 
            @change="checkEmail" 
            :style="{ 'border-color': checkMessage.email === false ? 'red' : '' }"
          >
          <div v-if="checkMessage.email === true" style="color: #C1D3FE; font-size:14px; text-align: left;"><i class="fas fa-check-circle me-1"></i>올바른 이메일 양식입니다.</div>
          <div v-else-if="checkMessage.email === false" style="color: red; font-size:14px; text-align: left;"><i class="fas fa-exclamation-triangle me-1"></i>올바르지 않은 이메일 양식이거나 계정이 존재합니다.</div>
          <div v-else></div>
        </div>
      </div>
      <div class="password mt-3">
        <p class="text-start p-0 m-0">비밀번호<span class="required text-danger">*</span></p>
        <div>
          <input type="password" placeholder="비밀번호 (영문+숫자+특수문자 8자 이상, 15자 미만)" maxlength="14" class="w-100 rounded signup-input"
            v-model="userInfo.password"
            @keyup="checkPassword" 
            :style="{ 'border-color': checkMessage.password === false ? 'red' : '' }"
          >
          <div v-if="checkMessage.password === true"></div>
          <div v-else-if="checkMessage.password === false" style="color: red; font-size:14px; text-align: left;"><i class="fas fa-exclamation-triangle me-1"></i>비밀번호 양식이 올바르지 않습니다.</div>
        </div>
        <div class="mt-2" style="margin-top: 0.5rem;">
          <input type="password" placeholder="비밀번호 확인" class="w-100 rounded signup-input"
            v-model="userInfo.passwordConfirm" 
            @keyup="checkPasswrodConfirm" 
            :style="{ 'border-color': checkMessage.passwordConfirm === false ? 'red' : '' }"
          >
          <div v-if="checkMessage.passwordConfirm === true" style="color: #C1D3FE; font-size:14px; text-align: left;"><i class="fas fa-check-circle me-1"></i>비밀번호가 일치합니다.</div>
          <div v-else-if="checkMessage.passwordConfirm === false" style="color: red; font-size:14px; text-align: left;"><i class="fas fa-exclamation-triangle me-1"></i>비밀번호가 일치하지 않습니다.</div>
        </div>
      </div>
      <div class="userinfo mt-3">
        <p class="text-start p-0 m-0">닉네임<span class="required text-danger">*</span></p>
        <div>
          <input type="text" placeholder="닉네임을 입력해주세요" class="w-100 rounded signup-input"
            v-model="userInfo.nickname"
            @change="checkNickname" 
            :style="{ 'border-color': checkMessage.nickname === false ? 'red' : '' }"
          >
          <div v-if="checkMessage.nickname === true"></div>
          <div v-else-if="checkMessage.nickname === false" style="color: red; font-size:14px; text-align: left;"><i class="fas fa-exclamation-triangle me-1"></i>중복된 닉네임입니다.</div>
        </div>
        <p class="text-start p-0 m-0 mt-2">이름<span class="required text-danger">*</span></p>
        <div>
          <input type="text" placeholder="이름을 입력해주세요" class="w-100 rounded signup-input"
            v-model="userInfo.name"
            @keyup="checkName"
          >
        </div>
        <p class="text-start p-0 m-0 mt-2">전화번호</p>
        <div>
          <input type="text" placeholder="전화번호를 입력해주세요" class="w-100 rounded signup-input" 
            v-model="userInfo.phone"
            @keyup="formatPhone"
          >
        </div>
        <p class="text-start p-0 m-0 mt-2">주소</p>
        <div class="text-start">
          <!-- 우편번호 검색 -->
          <input type="text" placeholder="우편번호" class="d-inline-block w-25 rounded signup-input"
            v-model="userInfo.zipcode" 
          >
          <div class="ms-3 text-center d-inline-block signup-input rounded cursor-pointer" style="width: 20%; text-indent: 0;" 
            @click="findAddress"
          >
            주소 찾기
          </div>
          <input type="text" placeholder="기본 주소" class="w-100 mt-2 rounded signup-input"
            v-model="userInfo.baseAddress"
          >
        </div>
        <p class="text-start p-0 m-0 mt-2">상세주소</p>
        <div class="text-start">
          <input type="text" placeholder="상세 주소를 입력해주세요 (동, 호수)" class="w-100 rounded signup-input"
            v-model="userInfo.detailAddress"
          >
        </div>
      </div>

      <!-- 전문가 가입 -->
      <div>
        <div v-if="!userInfo.isProfessional" class="w-100 mt-3 rounded signup-input cursor-pointer"
          @click="asProfessional"
        >
          전문가로 가입하기
        </div>
        <div v-else class="w-100 mt-3 rounded" style="box-shadow: 0 2px 5px -2px rgba(0,0,0,0.5); border: 1px solid black;">
          <div class="d-flex justify-content-between py-0 px-3" style="height:52px; line-height: 52px; background-color: #C1D3FE; border-bottom: 1px solid black;">
            <p class="m-0 p-0">전문가 정보 입력</p>
            <i class="far fa-times-circle cursor-pointer" style="font-size: 28px; line-height: 52px;"
              @click="asUser"
            ></i>
          </div>
          <div class="mx-auto mb-3" style="width: 90%;">
            <p class="text-start p-0 m-0 mt-2">업체명</p>
            <div>
              <input type="text" placeholder="업체명을 입력해주세요" class="w-100 rounded signup-input"
                v-model="userInfo.businessName"
              >
            </div>
            <p class="text-start p-0 m-0 mt-2">사업자 등록번호</p>
            <div>
              <input type="text" placeholder="사업자 등록번호를 입력해주세요" class="w-100 rounded signup-input"
                v-model="userInfo.businessNumber"
                @keyup="checkBusinessNumber"
                :style="{ 'border-color': checkMessage.businessNumber === false ? 'red' : '' }"
              >
              <div v-if="checkMessage.businessNumber === true"></div>
              <div v-else-if="checkMessage.businessNumber === false" style="color: red; font-size:14px; text-align: left;"><i class="fas fa-exclamation-triangle me-1"></i>사업자 등록번호 양식이 올바르지 않습니다.</div>
            </div>
            <p class="text-start p-0 m-0 mt-2">업종</p>
            <div>
              <input type="text" placeholder="업종을 입력해주세요" class="w-100 rounded signup-input"
                v-model="userInfo.businessType"
              >
            </div>
          </div>
        </div>

      </div>
      <!-- 이용 약관 -->
      <div class="terms-conditions mt-3 p-3 mb-3 text-start" style="border: 1px solid #C4C4C4;">
        <div>
          <input type="checkbox" name="" id=""> <label for="">모두 동의합니다.</label>
        </div>
        <hr>
        <div>
          <input type="checkbox" name="" id=""> <label for="">만 14세 이상입니다.<span class="required text-danger">(필수)</span></label>
        </div>
        <div>
          <input type="checkbox" name="" id=""> <label for="">서비스 이용약관에 동의합니다.<span class="required text-danger">(필수)</span></label>
        </div>
        <div>
          <input type="checkbox" name="" id=""> <label for="">개인정보 수집/이용에 동의합니다. <span class="required text-danger">(필수)</span></label>
        </div>
        <div>
          <input type="checkbox" name="" id=""> <label for="">이벤트 할인 혜택 알림 수신에 동의합니다. <span class="not-required">(선택)</span></label>
        </div>
        <div>
          <input type="checkbox" name="" id=""> <label for="">장기 미접속 시 계정 활성 상태 유지에 동의합니다.<span class="not-required">(선택)</span></label>
        </div>
      </div>
      <input type="submit" class="w-100 border-0" value="회원가입하기" style="height: 52px; background-color: #C7C7C7; color: white; font-size: 24px;"
        :disabled="!isValid"
        :style="{ 'background-color': isValid ? '#C1D3FE' : '#C7C7C7' }"
      >
    </form>
  </div>
</template>
<script>
// import logo from '@/views/Signup/Components/logo_square.png'
import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'SignupForm',
  data: function() {
    return {
      // logo: logo,
      userInfo: {
        email: '',
        password: '',
        passwordConfirm: '',
        nickname: '',
        name:  '',
        phone: '',
        zipcode: '',
        baseAddress: '',
        detailAddress: '',
        isProfessional: false,
        businessName: '',
        businessNumber: '',
        businessType: '',
      },
      checkMessage: {
        // 기본값 : 보이지 않음
        email: '',
        password: '',
        passwordConfirm: '',
        nickname: '',
        name: '',
        businessNumber: '',
      },
      isValid: false,
    }
  },
  methods: {
    asProfessional: function() {
      this.userInfo.isProfessional = true
    },
    asUser: function() {
      if(confirm('전문가 가입을 취소하시겠습니까?')){
        this.userInfo.isProfessional = false
      }
    },
    userSignup: function() {
      // form validation check
      if(!this.isValid) {
        alert('필수(*)값을 입력해주세요.')
        return false
      }

      axios({
        method: 'post',
        url: `${SERVER_URL}/user/`,
        data: this.userInfo,
      })
        .then(res => {
          console.log(res.data.statusCode)
          // 인증번호 입력 화면으로 이동
          this.$emit('form-success', this.userInfo)
        })
        .catch((err, res) => {
          console.log(err)
          // message 종류에 따라 구분
          alert(res.message)
        })
    },
    formatPhone: function() {
      var number = this.userInfo.phone
      var numberPattern = /\d+/g;
      try {
          number = number.match( numberPattern ).join('');
      } catch (e) {
          return
      }
      var formatString = ''
      if (number.length <= 3){
          formatString = number.substr(0, 3)
      }else if (number.length >= 4 && number.length < 8){
          formatString = number.substr(0, 3) + '-' + number.substr(3, 4)
      }else{
          formatString = number.substr(0, 3) + '-' + number.substr(3, 4) + '-' + number.substr(7, 4)
      }
      this.userInfo.phone = formatString
    },
    changebusinessNumberFormat: function() {
      var number = this.userInfo.businessNumber
      var numberPattern = /\d+/g;
      try {
          number = number.match( numberPattern ).join('');
      } catch (e) {
          return
      }
      var formatString = ''
      if (number.length <= 3){
          formatString = number.substr(0, 3)
      }else if (number.length >= 4 && number.length < 6){
          formatString = number.substr(0, 3) + '-' + number.substr(3, 2)
      }else{
          formatString = number.substr(0, 3) + '-' + number.substr(3, 2) + '-' + number.substr(5, 5)
      }
      this.userInfo.businessNumber = formatString

      return number
    },
    checkBusinessNumber: function() {
      // model값에 filter 적용
      var number = this.changebusinessNumberFormat()
      if(number.length < 10){
        // 특정 element 변경
        this.checkMessage.businessNumber = ''
      }

      var numberMap = number.replace(/-/gi, '').split('').map(function (d){
          return parseInt(d, 10);
      });
      
      if(numberMap.length == 10){
        var keyArr = [1, 3, 7, 1, 3, 7, 1, 3, 5];
        var chk = 0;
        
        keyArr.forEach(function(d, i){
          chk += d * numberMap[i];
        });
        
        chk += parseInt((keyArr[8] * numberMap[8])/ 10, 10);
        
        if(Math.floor(numberMap[9]) === ( (10 - (chk % 10) ) % 10)){
          this.checkMessage.businessNumber = true
        }
        else{
          this.checkMessage.businessNumber = false
        }
      }
    },
    findAddress: function() {
      
      new daum.Postcode({
        oncomplete: data => {
          var roadAddr = data.roadAddress

          // 우편번호와 주소 정보를 해당 필드에 넣는다.
          this.userInfo.zipcode = data.zonecode
          this.userInfo.baseAddress = roadAddr
        }
      }).open();
    },
    checkEmail: function() {
      // 이메일 중복확인, 이메일 양식 체크
      
      if(this.userInfo.email == '') {
        this.checkMessage.email = ''
        return
      }

      // 이메일 유효성 체크
      if(validateEmail(this.userInfo.email)) {
        // 중복확인
        axios({
          method: 'get',
          url: `${SERVER_URL}/user/email-check/${this.userInfo.email}`,
        })
          .then(res => {
            if(!res.data.isUsing){
              this.checkMessage = {
                ...this.checkMessage,
                email: true
              }
              return false
            }
            // 이메일이 중복일 때
          })
          .catch((err, res) => {
            console.log(err, res)
          })
      }
      
      this.checkMessage = {
        ...this.checkMessage,
        email: false
      }
    },
    checkNickname: function() {
      if(this.userInfo.nickname == '') {
        this.checkMessage = {
          ...this.checkMessage,
          nickname: ''
        }
        return
      }

      // 닉네임 유효성 체크
      if(this.userInfo.nickname.length > 1) {
        // 중복확인
        axios({
          method: 'get',
          url: `${SERVER_URL}/user/nickname-check/${this.userInfo.nickname}`,
        })
          .then(res => {
            if(!res.data.isUsing){
              this.checkMessage = {
                ...this.checkMessage,
                nickname: true
              }
              return false
            }
          })
          .catch((err, res) => {
            console.log(err, res)
          })
      }

      this.checkMessage = {
        ...this.checkMessage,
        nickname: false
      }
    },
    checkPassword: function() {
      var password = this.userInfo.password

      // 비밀번호가 비어있을 경우 메시지를 보여주지 않음
      if(password == "") {  
        this.checkMessage.password = ''
        return false
      }

      if(password.length < 8) {
        this.checkMessage.password = false
        return false
      }
      var passwordOptions = {
        alpha:    1, /* lower + upper */
        numeric:  1,
        special:  1,
        length: [7, 15],
      }
      if(validatePassword(password, passwordOptions)) {
        this.checkMessage = {
          ...this.checkMessage,
          password: true
        }
      } else {
        this.checkMessage = {
          ...this.checkMessage,
          password: false
        }
      }
    },
    checkPasswrodConfirm: function() {
      if(this.userInfo.passwordConfirm == '') {
        this.checkMessage.passwordConfirm = ''
      }
      if(this.userInfo.password !== this.userInfo.passwordConfirm) {
        // password 불일치
        this.checkMessage = {
          ...this.checkMessage,
          passwordConfirm: false
        }
      } else {
        // password 일치
        this.checkMessage = {
          ...this.checkMessage,
          passwordConfirm: true
        }
      }
    },
    checkName: function() {
      if(this.userInfo.name == '') {
        this.checkMessage = {
          ...this.checkMessage,
          name: ''
        }
        return
      }
      if(this.userInfo.name.length > 1){
        this.checkMessage = {
          ...this.checkMessage,
          name: true
        }
      } else {
        this.checkMessage = {
          ...this.checkMessage,
          name: false
        }
      }
    }
  },
  watch: {
    checkMessage: function() {
      for (const [key, value] of Object.entries(this.checkMessage)) {
        if(key!='businessNumber') { 
          if(!value) { 
            this.isValid = false
            return this.checkMessage 
          }
        }
      }
      this.isValid = true
      return this.checkMessage
    }
  }
}
function validatePassword(pw, options) {
  // default options (allows any password)
  var o = {
    lower:    0,
    upper:    0,
    alpha:    0, /* lower + upper */
    numeric:  0,
    special:  0,
    length:   [0, Infinity],
    custom:   [ /* regexes and/or functions */ ],
    badWords: [],
    badSequenceLength: 0,
    noQwertySequences: false,
    noSequential:      false
  }
 
  for (var property in options)
    o[property] = options[property]
 
  var re = {
    lower:   /[a-z]/g,
    upper:   /[A-Z]/g,
    alpha:   /[A-Z]/gi,
    numeric: /[0-9]/g,
    special: /[\W_]/g
  },
  rule, i

  // enforce min/max length
  if (pw.length < o.length[0] || pw.length > o.length[1])
    return false
  
  // enforce lower/upper/alpha/numeric/special rules
  for (rule in re) {
    if ((pw.match(re[rule]) || []).length < o[rule])
      return false
  }
 
  // enforce word ban (case insensitive)
  for (i = 0; i < o.badWords.length; i++) {
    if (pw.toLowerCase().indexOf(o.badWords[i].toLowerCase()) > -1)
      return false
  }
 
  // enforce the no sequential, identical characters rule
  if (o.noSequential && /([\S\s])\1/.test(pw))
    return false
 
  // enforce alphanumeric/qwerty sequence ban rules
  if (o.badSequenceLength) {
    var lower   = "abcdefghijklmnopqrstuvwxyz",
    upper   = lower.toUpperCase(),
    numbers = "0123456789",
    qwerty  = "qwertyuiopasdfghjklzxcvbnm",
    start   = o.badSequenceLength - 1,
    seq     = "_" + pw.slice(0, start);
    for (i = start; i < pw.length; i++) {
      seq = seq.slice(1) + pw.charAt(i);
      if (
        lower.indexOf(seq)   > -1 ||
        upper.indexOf(seq)   > -1 ||
        numbers.indexOf(seq) > -1 ||
        (o.noQwertySequences && qwerty.indexOf(seq) > -1)
      ) {
        return false
      }
    }
  }

  // enforce custom regex/function rules
  for (i = 0; i < o.custom.length; i++) {
    rule = o.custom[i]
    if (rule instanceof RegExp) {
      if (!rule.test(pw))
        return false
    } else if (rule instanceof Function) {
      if (!rule(pw))
        return false
    }
  }
 
  // great success!
  return true
}

const validateEmail = (email) => {
  return String(email)
    .toLowerCase()
    .match(
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    );
};
</script>
<style>
  .signup-input {
    height: 52px;
    line-height: 52px;
    font-size: 14px;
    box-shadow: 0 2px 5px -2px rgba(0,0,0,0.5);
    border: 2px solid black; 
    text-indent: .5rem;
  }
</style>