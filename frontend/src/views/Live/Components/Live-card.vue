<template>
<div class="video-route">
  <div class="live-list-video-container cursor-pointer" @click="routeToLiveRoom">
    <div class="live-list-video-preview">
      <img :src="video.thumbnail ? video.thumbnail : 'http://afreehp.kr/update/bnr/bnr_penalty_type_1.png'">
      <span class="onair-badge">LIVE</span>
      <span class="live-list-viewers ellipsis"><i class="fas fa-eye"></i>{{video.realTimeViewers}}명</span>
    </div>
    <div class="live-list-video-info-container">
      <div class="live-list-title ellipsis">{{video.title}}</div>
      <div class="live-list-video-info ellipsis">
        <span>{{video.hostNickname}}</span>
        <span>{{categoryName}}</span>
      </div>
    </div>
  </div>  
</div>
</template>

<script>
import { computed, toRefs } from 'vue'
export default {
  props: {
    video: Object,
  },
  setup(props) {
    // 받은 비디오
    const { video } = toRefs(props)

    // 카테고리 아이디를 카테고리명으로 변경
    const categoryId = video.value.categoryId
    const categories = {1: '개발', 2: '디자인', 3: '핸드메이드'}
    const categoryName = computed( () => categories[categoryId])
    return { categoryName }
  },
  methods: {
    routeToLiveRoom: function() {
      this.$router.push({name: 'LiveRoom', params: { ...this.video, liveRoomId: this.video.hostId } })
    }
  },
}
</script>

<style>
.video-route {
  text-decoration: none;
  color: black;
}
.live-list-video-container {
  width: 211px;
  /* height: 268px; */
  height: 170px;
  /* background-color: #c7c7c7; */
  /* position: relative; */
  margin: 0px 19px 30px 0px;
  display: flex;
  flex-direction: column;
}
.live-list-video-container:hover {
  box-shadow: 3px 3px 2px 1px rgba(193, 211, 254, .3);
}
.live-list-video-preview {
  /* width: 302px; */
  width: 211px;
  /* height: 170px; */
  height: 119px;
  /* background-color: #727272; */
  position: relative;
}
.live-list-video-preview > img {
  width: 100%;
  height: 100%;
}
.live-list-viewers {
  font-size: 11px;
  margin-left: 10px;
  position: absolute;
  left: 41px;
  top: 9px;
  /* background-color: rgb(46, 46, 46); */
  opacity: 0.5;
  color: black;
  padding-left: 1px;
  border-radius: 5px;
}
.live-list-viewers > i {
  margin-right: 2px;
}
.live-list-video-info-container {
  flex: 1;
  width: 100%;
  padding: 1px;
}
.live-list-video-info-container > div {
  display: flex;
  align-items: flex-end;
}
.live-list-title {
  font-size: 16px;
  font-weight: bold;
  margin-top: 2px;
}
.live-list-video-info {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #c4c4c4;
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
</style>