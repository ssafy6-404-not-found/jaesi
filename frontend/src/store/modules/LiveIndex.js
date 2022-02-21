import axios from 'axios'

const state = {
  liveVideoList: [],
  hotLive: Object,
}

const getters = {
  getLiveVideoList: state => state.liveVideoList,
  getHotLive: state => state.hotLive,
}

const mutations = {
  SET_LIVE_VIDEO_LIST: (state, liveVideoList) => state.liveVideoList = liveVideoList,
  SET_HOT_LIVE: (state, hotLive) => state.hotLive = hotLive,
}
const SERVER_URL = process.env.VUE_APP_SERVER_URL

const actions = {
  setLiveVideoList({commit}, {cateogryId, currPage, sortType}) {
    axios({
      method: 'get',
      url: `${SERVER_URL}/live/list`,
      data: {
        category: cateogryId,
        currPage: currPage,
        perPage: 10,
        sortType: sortType,
      }
    })
      .then(res => {
        console.log(cateogryId, currPage, sortType)
        commit('SET_PAGINATION', res.data)
        const liveVideoList = res.data.content
        commit('SET_LIVE_VIDEO_LIST', liveVideoList)
      })
      .catch(err => {
        console.log(err)
      })    
  },
  setHotLive({commit}) {
    axios({
      method: 'get',
      url: `${SERVER_URL}/live/hot`,
    })
      .then(res => {
        const hotLive = res.data
        commit('SET_HOT_LIVE', hotLive)
      })
      .catch(err => {
        console.error(err)
        const hotLive = {}
        commit('SET_HOT_LIVE', hotLive)
      })       
  }
}

export default {
  state, getters, mutations, actions
}