<template>
  <div id="nav">
    <div class="nav-top d-flex justify-content-between align-middle" style="height: 50px; line-height: 50px;">
      <div class="logo">
        <router-link to="/"><img src="./assets/logo_square.png" alt="로고" style="height: 100%;"></router-link>
      </div>
      <div class="menus d-flex">
        <!-- <div class="search-box me-3 position-relative">
          <input type="text" class="search-input" style="width: 360px; height: 40px; border: 1px solid #C1D3FE; border-radius: 50px; text-indent: 1rem; font-size: 14px;">
          <div class="position-absolute" style="top: 4px; right: 1rem; color: #C1D3FE;">
            <i class="fas fa-search" style="font-size: 20px; line-height: 40px;"></i>
          </div>
        </div> -->
        <div>
          <div class="d-flex justify-content-between">
            <!-- 전문가 라이브 -->
            <i v-if="isProfessional" class="fa-video fas me-3" style="font-size: 24px; line-height: 50px;" @click.prevent="toggleLiveSetting"></i>
            <!-- 채팅 -->
            <i v-if="isLoggedIn" class="fa-comment me-3" :class="{ fas: showChat, far:!showChat }" style="font-size: 24px; line-height: 50px;" @click.prevent="toggleChat"></i>
            <!-- 찜목록 -->
            <router-link v-if="isLoggedIn" to="/mypage/bookmarks"><i class="far fa-heart me-3" style="font-size:24px; line-height: 50px;"></i></router-link>
          

            <!-- 로그인 -->
            <i v-if="!isLoggedIn" class="far fa-user" style="font-size: 24px; line-height: 50px;" @click="toggleLogin"></i>
            <!-- 마이페이지 -->
            <router-link v-else to="/mypage/general" style="height: 50px;">
              <img :src="[profileImage ? profileImage : '/img/unKnown.d7563ab1.png']" width="24" style="    height: 27px;
    width: 27px;
    border-radius: 70%;
    -o-object-fit: cover;
    object-fit: cover;">
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <div class="category" style="font-size: 18px; height: 40px; line-height: 40px;">
      <div>
        <router-link to="/" class="me-3 text-bold">홈</router-link>
        <router-link :to="{ name: 'Product', params: { categoryId: 1 }}" class="me-3 text-bold">개발</router-link>
        <router-link :to="{ name: 'Product', params: { categoryId: 2 }}" class="me-3 text-bold">디자인</router-link>
        <router-link :to="{ name: 'Product', params: { categoryId: 3 }}" class="me-3 text-bold">핸드메이드</router-link>
        <router-link to="/live" class="me-3 text-bold">전문가라이브</router-link>
      </div>
      <div v-if="isLoggedIn" class="nav-logout d-none d-md-block" @click="userLogout"><div>로그아웃</div></div>
    </div>
  </div>

  <router-view
    @open-chat="toggleChat"
    />

  <chat-modal 
    v-if="showChat" 
    @close-chat-modal="toggleChat"
  />
  <service-modal 
    v-if="showService" 
  />
  <live-setting-modal 
    v-if="showLiveSetting" 
    @close-live-setting="toggleLiveSetting"
  />
  <login-modal v-if="showLogin" @toggleLogin="toggleLogin"/>

</template>
<script>
import { ref, computed } from 'vue'
import { useStore } from 'vuex'
// import logo from './views/Signup/Components/logo.png'
import ChatModal from './components/Chat/Chat.vue'
import ServiceModal from './components/Service/Service.vue'
// import profileImage from './components/Chat/Components/unKnown.png'
import LoginModal from './components/LoginModal.vue'
import LiveSettingModal from './components/Live/LiveSetting.vue'

export default {
  components: {
    ChatModal,
    ServiceModal,
    LoginModal,
    LiveSettingModal,
  },
  data: function() {
    return {
      // logo,
      // profileImage,
      showChat: false,
      showService: false,
      showLiveSetting: false,
    }
  },
  setup() {
    const profileImage = computed(()=>store.state.Login.userInfo.profilePhoto)
    const store = useStore()
    const showLogin = ref(false)
    const toggleLogin = () => {
      // console.log('toggleLogin in App.vue')
      showLogin.value = !showLogin.value
    }
    // 로그아웃
    const userLogout = () => {
      // store.dispatch('setIndexUserInfo', Object)
      store.dispatch('userLogout')
    }
    return { showLogin, toggleLogin, userLogout, profileImage }
  },
  computed: {
    isLoggedIn: function() {
      return !!this.$store.state.Login.token
    },
    isProfessional: function() {
      if(this.$store.state.Login.userInfo) {
        return !!this.$store.state.Login.userInfo.isProfessional
      }
      return null
    },
  },
  methods: {
    toggleChat: function() {
      this.showChat = !this.showChat
    },
    toggleService: function() {
      this.showService = !this.showService
    },
    changeLoggedIn: function() {
      this.isLoggedIn = !this.isLoggedIn
    },
    toggleLiveSetting: function() {
      this.showLiveSetting = !this.showLiveSetting
    },
  },
}
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic:wght@400;700;800&display=swap');
html {
  overflow-y: scroll;
}
#app {
  font-family: 'Nanum Gothic', sans-serif;
/* font-family: 'Nanum Gothic Coding', monospace;  */
/* font-family: Avenir, Helvetica, Arial, sans-serif; */
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  max-width: 1130px;
  margin: 0 auto;
}
#nav {
  /* padding: 30px; */
  /* width: 1152px; */
  padding: .5rem .1rem .5rem .1rem;
  /* border-bottom: 2px solid black; */
}
#nav a {
  /* font-weight: bold; */
  color: #2c3e50;
  text-decoration: none;
}
.nav-logout {
  margin-top: 10px;
  padding: 0px 5px 0px 6px;
  color :white;
  background-color: #c7c7c7;
  border-radius: 18px;
  font-size: 12px;
  height: 30px;
  width: 67px;
  position: relative;
  display: flex;
}
.nav-logout:hover {
  background-color: #727272;
  cursor: pointer;
}
.nav-logout > div {
  height: 100%;
  position: absolute;
  top: -4px;
  right: 9px;
}
.category {
  display: flex;
  justify-content: space-between;
}
.no-content {
  margin: 10px 0px 0px 5px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 50px;
  width: 80%;
}
/* #nav a.router-link-exact-active { */
  /* color: #C1D3FE; */
/* } */
@media ( max-width: 768px ) {
  #app {
    max-width: 100%;
  }
  .category {
    display: none;
  }

}
</style>
