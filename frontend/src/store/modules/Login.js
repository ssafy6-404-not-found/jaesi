import router from '@/router'

const state = {
  token: null,
  userInfo: Object,
}
const getters = {
}
const mutations = {
  SET_TOKEN: (state, token) => state.token = token,
  SET_USER_INFO: function (state, userInfo) {
    state.userInfo = userInfo
    // console.log(state.userInfo)
  },
  SET_PROFILE: (state, profilePhoto) => {
    console.log('mutation',state.userInfo )
    state.userInfo.profilePhoto = profilePhoto
    console.log('after mutation',state.userInfo )
  },
}

const actions = {
  setToken({ commit }, token) {
    commit('SET_TOKEN', token)
  },
  userLogout({ commit, dispatch }) {
    localStorage.removeItem('jwt')
    localStorage.removeItem('vuex')
    commit('SET_TOKEN', null)
    commit('SET_USER_INFO', null)
    dispatch('mypageLogout')
    router.push('/')
  },
  setUserInfo( { commit }, userInfo) {
    delete userInfo.accessToken
    delete userInfo.statusCode
    delete userInfo.message
    // console.log(userInfo)
    commit('SET_USER_INFO', userInfo)
  },
  setProfile({commit}, profilePhoto) {
    commit('SET_PROFILE', profilePhoto)
  }
}

export default {
  state, getters, mutations, actions
}