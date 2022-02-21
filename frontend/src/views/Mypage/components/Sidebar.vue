<template>
  <div class="sidebar">
    <div class="profile-wrapper">
      <div class="profile-img-wrapper">
        <img :src="[profilePhoto ? profilePhoto : '/img/unKnown.d7563ab1.png']" alt="">
        <div v-if="isProfessional" class="expert text-bold">전문가</div>
        <div v-else class="general text-bold">일반회원</div>
      </div>
      <div class="text-bold profile-username">{{ userInfo.name }}</div>
      <!-- 일반 계정이면 안 보이도록 -->
      <div :class="{visibility_hidden:!isUserProfessional}">
        <div v-if="isProfessional" @click="routeToUserInfo"><a class="general profile-member-type text-bold"><i class="fas fa-exchange-alt"></i>일반회원으로 전환</a></div>
        <div v-else @click="routeToUserInfo"><a class="expert profile-member-type text-bold"><i class="fas fa-exchange-alt"></i>전문가로 전환</a></div>
      </div>
    </div>
    <h3 class="text-bold">마이 페이지</h3>
    <div class="sidebar-list-wrap d-flex flex-column">
      <!-- routerlink로 해야함!!!  -->
      <router-link v-if="!isProfessional" to="/mypage/general" class="sidebar-list-item">회원정보</router-link>
      <router-link v-if="!isProfessional" to="/mypage/orders" class="sidebar-list-item">주문내역</router-link>
      <router-link v-if="!isProfessional" to="/mypage/bookmarks" class="sidebar-list-item">찜 목록</router-link>

      <router-link v-if="isProfessional" class="sidebar-list-item" to="/mypage/professional">회원정보</router-link>
      <router-link v-if="isProfessional" to="/mypage/expert/order" class="sidebar-list-item">주문관리</router-link>
      <router-link v-if="isProfessional" to="/mypage/reviews" class="sidebar-list-item">리뷰관리</router-link>
      <router-link v-if="isProfessional" to="/mypage/myservice" class="sidebar-list-item">내 서비스</router-link>
    </div>
  </div>
</template>

<script>
import { computed } from "vue"
import { useStore } from "vuex"
import router from '@/router'

export default {
  name: 'MyPageSidebar',
  setup() {
    
    const store = useStore()
    const userInfo = computed(() => store.state.userInfo)
    // console.log(userInfo)
    // component 에서 사용할대에는 state.moduleName.stateName
    // 지금 내가 위치한 my page 회원타입 상태  
    const isProfessional = computed(() => store.state.Mypage.isMypageProfessional)
    const isUserProfessional = computed(() => store.state.Login.userInfo.isProfessional)
    const profilePhoto = computed(()=>store.state.Login.userInfo.profilePhoto)
    // 회원타입 변경
    const changeUserType  = () => store.dispatch('changeUserType')
    // 회원 타입 전환 및 페이지 이동
    // 일반 회원 페이지에서 전문가 회원 아닌 사람은 전문가 회원정보로 이동이 아닌 전문가 가입 페이지로 이동해야 함
    const routeToUserInfo = function () {
      // 전문가 회원으로 가입되어 있다면
      if (store.state.Login.userInfo.isProfessional) {
        // 현재 회원 상태 변경하고
        changeUserType()
        // 현재 상태에 따라 이동
        isProfessional.value ? router.push('/mypage/professional') : router.push('/mypage/general')
      } else {
        // 전문가 가입 페이지로 이동 - 주소 넣기
        router.push('/')
      }
    }
    return { isProfessional, routeToUserInfo, userInfo, isUserProfessional, profilePhoto}
  },
}
</script>

<style>
.sidebar {
  float: left;
  width: 200px;
  padding: 30px 0;
}
.sidebar a {
  text-decoration: none;
}
.profile-wrapper {
  /* border-top: 1px solid rgb(151, 151, 151); */
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 15px;
  flex-direction: column;
}
.profile-img-wrapper {
  margin: 10px;
  position: relative;
}
.profile-img-wrapper > img {
  height: 100px;
  width: 100px;
  border-radius: 70%;
  object-fit: cover;
}
.sidebar .profile-img-wrapper > div {
  position:absolute; 
  bottom: 0; 
  right: 0;
  font-size: 10px;
  border-radius: 3px;
  padding: 1.5px;
  padding-top: 2px;
}
.profile-member-type {
  border-radius: 3px;
  font-size: 11px;
  padding: 3px;
}
.expert {
  background-color: #FFEE93;
  /* background-color: #FFB3C6; */
  color: black;
}
.general {
  background-color: #C1D3FE;
  color: white;
  /* font-weight: bold; */
}
.profile-username { font-size: 14px; }
.sidebar h3 {
  font-size: 20px;
  border-bottom: 1px solid rgb(151, 151, 151);
  border-top: 1px solid rgb(151, 151, 151);
  padding: 15px;
  margin-top: 10px;
}
.sidebar-list-item {
  padding: 15px;
  text-decoration: none;
  color: #5F6368;
}
.sidebar-list-item:hover { color: #004BFE;}
.sidebar-list-item.router-link-exact-active {
  color: #C1D3FE;
}
.profile-member-type {     
  /* pointer-events: none; */
  cursor: pointer;
}
.visibility_hidden {
  visibility: hidden;
}

</style>