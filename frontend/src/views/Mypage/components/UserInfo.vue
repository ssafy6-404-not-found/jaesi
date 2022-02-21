<template>
<div class="mypage-container">
  <div class="mypage-wrapper">
    <!-- 마이페이지 사이드바 -->
    <side-bar></side-bar>
    <!-- 마이페이지 첨 접속하면 회원정보로 들어온다. -->
    <div class="main-content">
      <h1>내 정보</h1>
      <div class="row-direction">
        <div class="userinfo-profile-img">
          <img v-if="!!userInfo.profilePhoto" :src="userInfo.profilePhoto">
          <img v-else src="/img/unKnown.d7563ab1.png">
          <label class="userinfo-edit-img" for="profileImage">
            <a @change="select"><i class="fas fa-camera"></i></a>
          </label>
          <input id="profileImage" class="d-none" type="file" accept="image/png, image/jpeg" @change="selectImage">
        </div>
        <div class="userinfo-form">
          <form class="userinfo-form-wrapper">          
            <div>
              <label class="text-bold" for="">이메일</label>
              <input class="input-disabled" disabled v-model="userInfo.email">
            </div>
            <div>
              <label class="text-bold" for="">이름</label>
              <input class="input-disabled" disabled v-model="userInfo.name">
            </div>
            <div>
              <label class="text-bold">닉네임</label>
              <input type="text" v-model="nickname" @keyup="checkNickname">
              <div v-if="isUsing == true">중복된 닉네임입니다.</div>
            </div>
            <!-- <div>
              <label class="text-bold" for="">비밀번호</label>
              <input placeholder="현재 비밀번호" type="password">
              <input placeholder="새 비밀번호 (영문+숫자+특수문자 8자 이상)" type="password">
              <input placeholder="새 비밀번호 확인" type="password">
            </div> -->
            <!-- api -->
            <div>
              <label class="text-bold">우편번호</label>
              <div>
                <input type="text" v-model="userInfo.zipcode" disabled  class="postcode">
                <a @click="mypageFindAddress" class="postcode-find">우편번호 찾기</a>
              </div>
            </div>
            <div><label class="text-bold">기본주소</label><input type="text" v-model="userInfo.baseAddress" disabled></div>
            <div><label class="text-bold">상세주소</label><input type="text" v-model="userInfo.detailAddress"></div>
            <div>
              <label class="text-bold" for="">전화번호</label>
              <input type="text" v-model="userInfo.phone" @keyup="formatPhone">
            </div>
            <div v-if="isProfessional">
              <label class="text-bold">개인/사업자 번호</label>
              <input type="text" v-model="userInfo.businessNumber">
            </div>
            <div v-if="isProfessional">
              <label class="text-bold">상호</label>
              <input type="text" v-model="userInfo.businessName">
            </div>
            <div v-if="isProfessional">
              <label class="text-bold">업종</label>
              <input type="text" v-model="userInfo.businessType">
            </div>
          </form>
          <!-- <div class="row">
            <a class="main-color-button offset-8 col-2">정보수정</a>
            <a class="main-color-button col-2">취소</a>
            <a class="main-color-button offset-10  col-2">회원탈퇴</a>
          </div> -->
          <div class="d-flex justify-content-between userinfo-button-wrapper">
            <div class="userinfo-edit-button-wrapper">
              <a 
              class="main-color-button" @click="updateUserInfo">정보수정</a>
              <a class="main-color-button" @click="cancelUpdate">취소</a>
            </div>
            <a class="main-color-button background-color-gray" @click="deleteUser">회원탈퇴</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<loading-spinner :class="{display_none:isNotLoading}"></loading-spinner>
</template>

<script>
import { computed, ref } from "vue"
import { useStore } from "vuex"
import axios from 'axios'
import firebase from 'firebase/app'
import 'firebase/storage'

import SideBar from "./Sidebar.vue"
import LoadingSpinner from '@/components/Loading.vue'
// import LoadingSpinner from '../../../components/Loading.vue'

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'UserInfo',
  components: {
    SideBar,
    LoadingSpinner
  },
  methods: {
    mypageFindAddress: function() {
      new window.daum.Postcode({
        oncomplete: data => {
          var roadAddr = data.roadAddress
  
          this.userInfo.zipcode = data.zonecode.toString()
          this.userInfo.baseAddress = roadAddr
        }
      }).open();
    }
  },
  setup() {
    const store = useStore()
    // state 도 같이 변경되지 않도록 deep copy
    // const userInfo = {...computed(()=>store.state.Mypage.detailUserInfo)}
    // const userInfo = toRefs(props.userInfo)
    const email = useStore().state.Login.userInfo.email
    const userInfo = ref(Object)
    const defaultUserInfo = ref(Object)
    const nickname = ref(Object)

    axios({
      method: 'get',
      url: `${SERVER_URL}/user/${email}`,
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      },
    })
      .then(res=>{
        store.dispatch('setDetailUserInfo', res.data)
        userInfo.value = res.data
        nickname.value = res.data.nickname
        defaultUserInfo.value = {...res.data}
        delete userInfo.value.nickname
      })
      .catch(err=>console.log(err))

    const isProfessional = computed(()=> store.state.Mypage.isMypageProfessional)

    // 전화번호 형식
    const formatPhone = function() {
      var number = userInfo.value.phone
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
      userInfo.value.phone = formatString
    }

    // 우편주소 찾기 - 회원가입 참조
    // 에러 남
    // const mypageFindAddress = function() {
    //   new window.daum.Postcode({
    //     oncomplete: data => {
    //       var roadAddr = data.roadAddress

    //       // 우편번호와 주소 정보를 해당 필드에 넣는다.
    //       userInfo.value.zipcode = data.zonecode
    //       userInfo.value.baseAddress = roadAddr
    //     }
    //   }).open()
    // }

    // ***** 정보수정 - 비밀번호 수정은 제외 *****
    // 402 error 
    // const updateUserInfo = function() {
    //   axios.put('https://i6a404.p.ssafy.io:8000/user',
    //   {baseAddress:'test'})
    //     .then(res=>console.log(res.data))
    //     .catch(err=>console.log(err))
    // }

    const updateUserInfo = function() {
      if (!!nickname.value == false) {
        return alert("닉네임은 빈값이 될 수 없음")
      }
      // 닉네임 중복 거르기
      if (isUsing.value == true) {
        return alert("닉네임 중복") 
      } else {
        if (confirm("변경 사항을 저장하시겠습니까?")){
          const requestData = ref(Object)
          // 닉네임이 변경됐다면 
          console.log(isUsing.value)
          if (isUsing.value == false) {
            requestData.value = {...userInfo.value, nickname: nickname.value}
          } else {
            requestData.value = {...userInfo.value}
          }
          axios({
            method: 'put',
            url: `${SERVER_URL}/user`,
            data: requestData.value,
            headers: {
              Authorization: `Bearer ${localStorage.getItem('jwt')}`
            },
          })
            .then(() => {
              // state 값도 수정해주기
              store.dispatch('setDetailUserInfo', requestData.value)
              store.dispatch('setProfile', requestData.value.profilePhoto)
              // nickname 날리기
              defaultUserInfo.value = {...requestData}
              alert('변경 성공')              
            })
            .catch( err => {
              console.log(err)
              alert("수정 실패")
            })      
        } 
      }
    }

    // ***** 취소 *****
    const cancelUpdate = function () {
      // store.dispatch('setDetailUserInfo', defaultUserInfo)
      userInfo.value = defaultUserInfo.value
    }

    // ***** 회원탈퇴 *****
    const deleteUser = function() {
      if (confirm('탈퇴하면 복구 불가능. 탈퇴할거임?')) {
        // Save it!
        axios({method: 'delete', url: `${SERVER_URL}/user`,
          headers: {
            Authorization: `Bearer ${localStorage.getItem('jwt')}`
          },
        })
          .then(() => {
            store.dispatch('userLogout')
          })
          .catch(err => console.log(err))
      }
    }

    const isUsing = ref(Boolean)
    
    const checkNickname = function () {
      if(nickname.value.length > 1) {
        axios({
          method: 'get',
          url: `${SERVER_URL}/user/nickname-check/${nickname.value}`
        })
          .then(res => {
            // 사용되고 있으면 true, 아니면 false 로 
            isUsing.value = res.data.isUsing
          })
          .catch(err=>console.log(err))
      }
    }

    const isNotLoading = ref(true)

    const selectImage = function(event) {
      // 이미지 등록 프로세스, url 가져오기
      const imageData = event.target.files[0];
      let fileName = imageData.name
      const storageRef = firebase.storage().ref(`images/${fileName}`).put(imageData)
      storageRef.on("state_changed", 
      snapshot => {
        console.log(snapshot)
        isNotLoading.value = false
        // 업로드 중입니다 loading pin
        // upload percentage = (snapshot.bytesTransferred/snapshot.totalBytes)*100
      },
      error => {
        console.log(error)
        isNotLoading.value = true
      },
      () => {
        storageRef.snapshot.ref.getDownloadURL().then( url => {
          console.log("성공")
          userInfo.value.profilePhoto = url
        })
        .then(()=>isNotLoading.value = true)
      })
    }
    return { 
      userInfo, 
      isProfessional, 
      updateUserInfo, 
      cancelUpdate, deleteUser, formatPhone, 
      isUsing, checkNickname,
      nickname, selectImage, isNotLoading,
      }
  },
}
</script>

<style>
.mypage-container {
  box-sizing: border-box;
  list-style: none;
  display: flex;
  justify-content: center;
  width: 1152px;
}
.mypage-wrapper {
  display: flex;
  width: 100%;
}
.column-direction { display: flex; }
.text-bold { font-weight: bold; }
.row-direction {
  display:flex; 
  flex-direction: row;
}
.main-content {
  height: 100%;
  width: calc(100% - 202px);
  padding: 40px 0px 120px 35px ;
  margin-right: 0px;
}
.main-content > h1 {
  display: flex;
  width: 918px;
  font-size: 26px;
  font-weight: 700;
}
.main-content .row-direction {
  margin-top: 20px;
  padding: 10px;
  border-bottom: 1px solid gray;
}
.main-content .row-direction .userinfo-profile-img {
  position: relative;
  width: 250px;
  height: 250px;
}
.main-content .row-direction .userinfo-profile-img > img {
  height: 250px;
  width: 250px;
  border-radius: 70%;
  object-fit: cover;
}
.userinfo-edit-img {
  background-color: #fff;
  border-radius: 50%;
  border: 0.5px solid #C4C4C4;
  position:absolute; 
  bottom: 0; 
  right: 50px;
  padding: 1.5px;
}
.userinfo-edit-img i {
  color: #000;
  margin: 3px;
}
.userinfo-form { flex: 1; }
.userinfo-form-wrapper {
  margin-left: 70px;
}
.userinfo-form-wrapper > div {
  display:flex; 
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 10px;
} 
.userinfo-form-wrapper > div > input {
  margin-top: 4px;
  width: 450px;
  height: 32px; 
  font-size: 14px; 
  /* box-shadow: rgba(0, 0, 0, 0.5) 0px 2px 5px -2px;  */
  border: 1px solid #696969; 
  border-radius: 5px;
  padding: 10px;
}
.postcode {
  margin-top: 4px;
  width: 200px;
  height: 32px; 
  font-size: 14px; 
  /* box-shadow: rgba(0, 0, 0, 0.5) 0px 2px 5px -2px;  */
  border: 1px solid #696969; 
  border-radius: 5px;
  padding: 10px;
}
.postcode-find {
  text-decoration: none;
  color: #C1D3FE;
  margin-left: 10px;
  border: 1px solid #C1D3FE;
  padding: 5px;
}
.postcode-find:hover {
  color: #004BFE;
  border-color: #004BFE;
  cursor:pointer;
}
.userinfo-form-wrapper .input-disabled {
  border-color: lightgray;
  color: gray;
}
.userinfo-button-wrapper { width: 623px; }
.userinfo-edit-button-wrapper {
  display: flex;
  justify-content: flex-end;
  width: 520px;
}
.userinfo-edit-button-wrapper > a { margin: 0px 0px 0px 15px; } 
.main-color-button {
  width: 90px;
  background-color:#C1D3FE;
  color: white;
  display: inline-block;
  font-weight: 400;
  line-height: 1.5;
  text-align: center;
  text-decoration: none;
  vertical-align: middle;
  cursor: pointer;
  user-select: none;
  border: 1px solid transparent;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  border-radius: 0.25rem;
  transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
}
.main-color-button:hover {
  color:white;
  background-color: #7da1f5;
}
.background-color-gray { background-color: #D8D8D8;}
.background-color-gray:hover { background-color: #C4C4C4; }
</style>