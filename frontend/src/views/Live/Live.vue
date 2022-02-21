<template>
  <div class="live-index-container">
    <div class="live-page-title">전문가 라이브</div>
    <div class="live-content-container">
      <!-- 핫한 라이브 방송 -->
      <section class="popular-live-container">
        <h6>🔥핫한 라이브 방송🔥</h6>
        <div class="popular-live cursor-pointer" @click="routeToLiveRoom">
          <div class="live-preview-wrapper">
            <span class="onair-badge">LIVE</span>
            <span class="live-list-viewers"><i class="fas fa-eye"></i>{{hotLive.realTimeViewers}}명</span>
            <!-- <div class="live-preview"></div> -->
            <img :src="hotLive.thumbnail ? hotLive.thumbnail : 'http://afreehp.kr/update/bnr/bnr_penalty_type_1.png'" class="live-preview">
          </div>
          <div class="popular-info">
            <div class="caster-profile-container">
              <img class="caster-profile" src="https://www.clipartmax.com/png/full/171-1717870_stockvader-predicted-cron-for-may-user-profile-icon-png.png">
              <div class="title-overflow">
                <div class="hot-live-video-title">{{ hotLive.title }}</div>
                <div class="hot-live-video-caster ellipsis">{{ hotLive.hostNickname }}</div>
              </div>
            </div>
            <div class="live-video-explanation">{{ hotLive.description }}</div>
          </div>
        </div>
      </section>

      <section class="live-list-container">
        <div class="live-list-title-wrapper">
          <h6>라이브 목록</h6>
          <select class="live-list-category-sort" v-model="selected" @change="sortVideos(selectedCategoryId)">
            <option v-for="option in options" :value="option.value" 
              :key="option.value">{{option.title}}</option>
          </select>          
        </div>

        <!-- 목록 정렬 -->
        <div class="live-category-wrapper">
          <div class="row-direction">
            <div class="live-category" @click="filterCategory(0)" :class="{ current : isAll}">전체</div>
            <div class="live-category" @click="filterCategory(1)" :class="{ current : isDevelop}">개발</div>
            <div class="live-category" @click="filterCategory(2)" :class="{ current : isDesign}">디자인</div>
            <div class="live-category" @click="filterCategory(3)" :class="{ current : isHandmade}">핸드메이드</div>      
          </div>
        </div>
        
        <div class="live-videos-container">
          <live-card v-for="video in liveVideos" :video="video" :key="video.id" @select-video="selectVideo" />
        </div>
      </section>
      <section class="mobile-live-list-container">
        <div class="mobile-live-videos-container">
          <live-card v-for="video in liveVideos" :video="video" :key="video.id" @select-video="selectVideo"></live-card>
        </div>
        <pagination
          :totalPage="totalPage"
          :currPage="currPage"
          @selectPage="changeCurrPage($event, pageNum)"
          @next="nextPagination($event, pageNum)"
          @pervious="perviousPagination($event, pageNum)">
        </pagination>
        <div :class="[liveVideos.length == 0? '': 'display_none']">진행 중인 라이브가 없습니다.</div>
      </section>

    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useStore } from 'vuex'
import { mapGetters, mapActions } from 'vuex' 
import axios from 'axios'

import LiveCard from './Components/Live-card.vue'
import Pagination from '@/views/Product/Components/Pagination.vue'

export default {
  name: 'LiveList',
  components: {
    LiveCard,
    Pagination  
  },
  data() {
    return {
      videoList: Array,
      selectedVideo: null,
    }
  },
  methods: {
    ...mapActions([
      'setLiveVideoList',
      'setHotLive'
    ]),
    selectVideo: function(video) {
      this.seletedVideo = video
    },
    leaveLive: function() {
      this.selectVideo = null
    },
    routeToLiveRoom: function() {
      this.$router.push({name: 'LiveRoom', params: { ...this.hotLive, liveRoomId: this.hotLive.hostId } })
    },
    changeCurrPage(pageNum){
      if(this.currPage !== pageNum) {

        this.liveVideoList(this.selectedCategoryId, this.currPage, this.selected)
        this.getCatProduct(this.$route.params.categoryId, pageNum)
        this.currPage = pageNum
      }
    },
    nextPagination(pageNum) {
      this.currPage = pageNum
    },
    perviousPagination(pageNum) {
      this.currPage = pageNum
    },
  },
  computed: {
    ...mapGetters([
      'getLiveVideoList',
      'getHotLive'
    ]),
    latest() {
      return JSON.parse(JSON.stringify(this.getLiveVideoList))
    },
    viewers() {
      return JSON.parse(JSON.stringify(this.getLiveVideoList))
    },
    hotLive() {
      return this.getHotLive
    }
  },
  setup() {
    const liveVideos = ref(Object)
    const currPage = ref(1)
    const liveVideoList = function (categoryId, currPage, sortType) {
    const SERVER_URL = process.env.VUE_APP_SERVER_URL

      axios({
        method: 'get',
        url: `${SERVER_URL}/live/list`,
        params: {
          category: categoryId,
          currPage: currPage,
          perPage: 10,
          sortType: sortType,
        }
      })
        .then(res => {
          console.log(res.data.content)
          liveVideos.value = res.data.content
          totalPage.value = res.data.totalPages
        })
        .catch(err => {
          console.log(err)
        })    
    }
    const totalPage = ref(Number)
    liveVideoList(0, currPage.value, 0)
    useStore().dispatch('setHotLive')

    // ***** 정렬 *****
    // 최신순
    const selected = ref(1)     // default: 시청순
    const options = [
      { title: '시청순', value: 1 },
      { title: '최신순', value: 0 },
    ]
    // 카테고리 필터
    const selectedCategoryId = ref(0)
    // 선택된 카테고리 색깔 
    const isAll = ref(true)
    const isDevelop = ref(false)
    const isDesign = ref(false)
    const isHandmade = ref(false)

    const allCat = [isAll, isDevelop, isDesign, isHandmade]
    const preCat = ref(0)
    // click으로 호출되면 해당 카테고리 아이디 저장
    const filterCategory = function(categoryId) {
      // 색 변경 - 선택은 true 이전은 false
      allCat[categoryId].value = true
      allCat[preCat.value].value = false
      
      preCat.value = categoryId
      selectedCategoryId.value = categoryId
      currPage.value = 1
      liveVideoList(categoryId, currPage.value, selected.value)
    }
    const sortVideos = function(categoryId) {
      liveVideoList(categoryId, currPage.value, selected.value)
    }
    
  return { 
    selected, options, selectedCategoryId, 
    isAll, isDevelop, isDesign, isHandmade, 
    filterCategory, totalPage, liveVideos, currPage, sortVideos}
  },
}
</script>

<style>
.ellipsis {
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.cursor-pointer {
  cursor: pointer;
}
.live-index-container {
  /* 화면 크기 설정이 1152px이라 이렇게 설정해둠 */
  width: 1152px;
  display: flex;
  flex-direction: column;
  margin: 0px;
  margin-left: auto;
  margin-right: auto;
}
.live-page-title {
  font-size: 36px;
  text-align: start;
  font-weight: bolder;  
}
.live-content-container {
  margin: 10px 0px 0px 20px;
}
.popular-live-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.live-content-container > section h6 {
  font-size: 24px;
  /* width: 1112px; */
  width: 100%;
  margin-left: 10px;
  font-weight: bold;
  text-align: start;
}
.popular-live {
  width: 835px;
  /* width: 74%; */
  /* height: 341px; */
  /* background-color: #c7c7c7; */
  display: flex;
  justify-content: flex-start;
  background-color: #fafcff;
  border-radius: 2px;
}
.popular-live:hover {
  box-shadow: 3px 3px 2px 1px rgba(193, 211, 254, .3);
}
.live-preview-wrapper {
  width: 586px;
  /* height: 100%; */
  /* background-color: #727272; */
  position: relative;
}
.onair-badge {
  left: 10px;
  position: absolute;
  top: 7px;
  background-color: red;
  border-radius: 7px;
  padding: 0px 4px;
  color: white;
  font-weight: bold;
  font-size: 13px;
}
.live-preview {
  width: 586px;
}
.popular-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  margin: 25px 0px 0px 25px;
  margin-right: 16px;
  width: 208px;
  border-radius: 2px;
}
.popular-info div {
  text-align: start;
}
.caster-profile-container { display: flex;}
.caster-profile {
  margin-top: 6px;
  margin-right: 6px;
  width: 47px;
  height: 47px;
}
.hot-live-video-title {
  font-size: 24px;
  display: block;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: start;
  width: 158px;
}
.hot-live-video-caster {
  padding-left: 2px;
  font-size: 14px;
  color: #727272;
  width: 158px;
}
.hot-live-video-explanation {
  margin-top: 15px;
  font-size: 12px;
  height: 200px;
}
.live-list-container { margin-top: 40px; }
.live-list-title-wrapper { 
  display: flex;
  justify-content: space-around;  
  position: relative;
  height: 37px;
}
.live-list-title-wrapper > select {
  width: 85px;
  height: 28px;
}
.live-videos-container {
  display: flex;
  flex-wrap: wrap;
  /* width: 1152px; */
  width: 102%;
}
.live-category-wrapper {
  /* position: absolute; */
  /* top: 0;
  left: 0;
  white-space: nowrap;
  will-change: transform; */
  display: flex;
  justify-content: space-between;
}
.live-category-wrapper > a {
  /* position: relative; */
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  display: inline-block;
  height: 33px;
  padding: 6px 11px 6px 33px;
  border: 1px solid #e6e6ea;
  border-radius: 16.5px;
  line-height: 1.27;
  font-size: 15px;
  color: #767678;
  vertical-align: top;
  letter-spacing: -0.25px;
}
.live-category {
  border: 1.5px solid #c4c4c4;
  color: #969696;
  padding: 2px 16px;
  border-radius: 16px;
  font-size: 14px;
  margin: 0px 6px 12px 0px;
}
.live-category:hover {
  border: 1.5px solid #C1D3FE;
  color: #C1D3FE;
  cursor: pointer;
}
.live-list-category-sort {
  width: 75px;
  height: 28px;
  margin-right: 3px;
}
.live-list-category-sort > option:hover {
  background-color: transparent;
}
.current {
  border-color: #C1D3FE;
  color: #C1D3FE;
  font-weight: bold;
}
.mobile-live-videos-container { display:none; }

/* 768x 이하 작은 화면에서는 */
/* 768px 미만 모바일 1024 미만 테블릭 1024 이상 데스크탑 */
@media ( max-width: 768px ) {
  /* .live-index-container { width: 200px;} */
  .live-index-container {
    width: 100%;
  }
  /* .live-index-container {display: hi;} */
  .live-page-title{ display: none; }
  .live-content-container { margin: 0;}
  .live-content-container > section h6 {
    font-size: 16px;
    margin-left:0;
  }
  .popular-live {
    width: 100%;
    flex-direction: column;
    /* height: 0; */
    background-color: transparent;
  }
  .live-preview-wrapper { width: 100% }
  .live-preview {
    width: 100%;
    height: 0;
    position: relative;
    overflow: hidden;
    padding-bottom: 56.25%;
  }
  .popular-info { 
    width: 100%;
    margin: 0; 
  }
  .caster-profile { display: none;}
  .hot-live-video-title {
  font-size: 16px;
  font-weight: bold;
  margin-top: 2px;
  }
  .hot-live-video-caster {
    width: 100%;
    color: #c4c4c4;
    padding-left: 0;
    margin-top: 2px;
  }
  .hot-live-video-explanation { display: none;}
  /* .live-list-container { display: none;} */
  /* .mobile-live-list-container {display: block;} */
  .live-videos-container {
    flex-wrap: nowrap;
    overflow-x: scroll;
    white-space: nowrap;
}
}
</style>