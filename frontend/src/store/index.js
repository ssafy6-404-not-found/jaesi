import { createStore } from 'vuex'

import Mypage from './modules/Mypage'
import Login from './modules/Login'
import LiveIndex from './modules/LiveIndex'
// 새로고침 시에도 유지
import createPersistedState from "vuex-persistedstate"

export default createStore({
  modules: {
    Mypage,
    Login,
    LiveIndex,
  },
  state: {
    // 유저 정보 - 로그인 시 저장.
    userInfo: {},
    // 로그인 모달
    showLogin: false,
  },
  mutations: {
    // updateUserInfo: (state, userInfo) => state.userInfo = userInfo,
    SET_INDEX_USER_INFO: (state, userInfo) => state.userInfo = userInfo,
  },
  actions: {
    setIndexUserInfo( { commit }, userInfo) {
      delete userInfo.accessToken
      delete userInfo.statusCode
      delete userInfo.message
      // console.log(userInfo)
      commit('SET_INDEX_USER_INFO', userInfo)
    }    
  },
  // modules: {
  // }
  plugins: [ 
    createPersistedState({
      paths: [
        'userInfo',
        'Login.userInfo',
        'Login.token',
      ]
    }), 
  ],
})
