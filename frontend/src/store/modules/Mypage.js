import axios from 'axios'

const state = {
  // 로그인시 전문가 회원이라면 true로 전환
  isMypageProfessional: false,
  // 전문가 회원의 경우 판매 서비스 정보 
  myService: [],
  // 구매자의 주문 내역
  orders: [],
  // 회원정보에서 저장하는 내용
  bookmarks: [],
  // 전문가에게 들어온 주문 내역
  myServiceOrders: [],
  // 내 리뷰들
  reviews: [],
  detailUserInfo: Object,
  pagination: {
    last: Number, first: Number, totalPages: 0,
  },
}

const getters = {
}

const mutations = {
  CHANGE_USER_TYPE: (state) => state.isMypageProfessional = !state.isMypageProfessional,
  SET_ORDERS: (state, orders) => state.orders = orders,
  SET_MY_SERVICE: (state, myService) => state.myService = myService,
  DEFAULT_USER_TYPE: (state) => state.isMypageProfessional = false,
  SET_DETAIL_USER_INFO: (state, detailUserInfo) => state.detailUserInfo = detailUserInfo,
  SET_MY_SERVICE_ORDERS: (state, myServieOrders) => state.myServiceOrders = myServieOrders,
  SET_REVIEWS: (state, reviews) => state.reviews = reviews,
  SET_PAGINATION: (state, info) => {
    state.pagination.last = info.last
    state.pagination.first = info.first
    state.pagination.totalPages = info.totalPages
  },
  SET_BOOKMARKS: (state, bookmarks) => state.bookmarks = bookmarks
}
const SERVER_URL = process.env.VUE_APP_SERVER_URL
const actions = {
  mypageLogout({commit}) {
    console.log('mypagelogout')
    commit('SET_ORDERS', Array)
    commit('SET_MY_SERVICE', Array)
    commit('SET_DETAIL_USER_INFO', Array)
    commit('SET_MY_SERVICE_ORDERS', Array)
    commit('SET_DETAIL_USER_INFO', Array)
    commit('SET_REVIEWS', Array)
    commit('SET_PAGINATION', {last: Number, first: Number, totalPages: Number})
    commit('DEFAULT_USER_TYPE')
    commit('SET_INDEX_USER_INFO', Object)
  },
  changeUserType({commit}) {
    commit('CHANGE_USER_TYPE')
  },
  setBookmarks({commit}, currPage) {
    axios({
      method: 'get',
      url: `${SERVER_URL}/product/bookmark/${this.state.Login.userInfo.userId}`,
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      },
      params: {
        currPage: currPage,
        perPage: 10,
        userId: this.state.Login.userInfo.userId,
      }    
    })
      .then(res => {
        commit('SET_BOOKMARKS', res.data.content)
        commit('SET_PAGINATION', res.data)
      })
      .catch(err => console.log(err))
  },
  setOrders({commit}, currPage) {
    axios({
      method: 'get',
      url: `${SERVER_URL}/order/customer/${this.state.Login.userInfo.userId}`,
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      },
      params: {
        currPage: currPage,
        perPage: 10,
        buyerId: this.state.Login.userInfo.userId,
      }
    })
      .then((res) => {
        // console.log(res)
        commit('SET_ORDERS', res.data.content)
        commit('SET_PAGINATION', res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  setMyServiceOrders({commit}, currPage) {
    axios({
      method: 'get',
      url: `${SERVER_URL}/order/expert/${this.state.Login.userInfo.userId}`,
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      },
      params: {
        currPage: currPage,
        perPage: 10,
        sellerId: this.state.Login.userInfo.userId
      }
    })
      .then((res) => {
        // console.log(res)
        commit('SET_MY_SERVICE_ORDERS', res.data.content)
        commit('SET_PAGINATION', res.data)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  setMyService({commit}, currPage) {
    axios({
      method: 'get',
      url: `${SERVER_URL}/product/expert/${this.state.Login.userInfo.userId}`,
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      },
      params: {
        currPage: currPage,
        perPage: 3,
        sellerId: this.state.Login.userInfo.userId
      }
    })
      .then((res) => {
        // console.log(res.data.content)
        commit('SET_MY_SERVICE', res.data.content)
        commit('SET_PAGINATION', res.data)
      })
      .catch((err) => {
        console.log(err)
      })    
  },
  setDetailUserInfo({commit}, detailUserInfo) {
    delete detailUserInfo.statusCode
    delete detailUserInfo.message
    // delete detailUserInfo.socialType
    commit('SET_DETAIL_USER_INFO', detailUserInfo)
  },
  setReviews({commit}) {
    axios({
      method: 'get',
      url: `${SERVER_URL}/review/expert/${this.state.Login.userInfo.userId}`,
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      },
      params: {
        currPage: 1,
        perPage: 5,
        sellerId: this.state.Login.userInfo.userId,
      }
    })
      .then(res=>{
        // console.log(res.data.content)
        commit('SET_REVIEWS', res.data.content)
      })
  },
}

export default {
  state, getters, mutations, actions
}