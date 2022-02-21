<template lang="">
  <div class="w-100 h-100 position-absolute top-0 start-0" style="background-color: rgba(0, 0, 0, 0.25);">
    <div class="bg-white center-box rounded p-3" style="width: 480px; min-height: 720px; max-height:898px;">
      <div class="text-start" style="height: 40px; line-height: 40px;">
      <!-- 채팅방 리스트인지 -->
        <div style="font-size: 24px;">
          방송 시작하기
        </div>
        <!-- 닫기 버튼 -->
        <div style="position: absolute; top: 0; right: 0; width: 40px;" @click="closeLiveSetting">
          <i class="fas fa-times" style="height: 40px; line-height: 40px; font-size: 32px;"></i>
        </div>
      </div>
      <div>
        <div class="category">
          <p class="text-start p-0 m-0" style="font-size: 16px;">카테고리</p>
          <div>
            <select v-model="liveInfo.categoryId" class="w-100 rounded live-input">
              <option value="1">개발</option>
              <option value="2">디자인</option>
              <option value="3">핸드메이드</option>
            </select>
          </div>
        </div>

        <div class="title mt-3">
          <p class="text-start p-0 m-0" style="font-size: 16px;">방송 제목<span class="required text-danger">*</span></p>
          <div>
            <input type="text" placeholder="방송 제목을 입력해주세요" class="w-100 rounded live-input"
              v-model="liveInfo.title" 
              @change="checkTitle"
              :style="{ 'border-color': checkMessage.title === false ? 'red' : '' }"
            >
            <div v-if="checkMessage.title === false" style="color: red; font-size:14px; text-align: left;"><i class="fas fa-exclamation-triangle me-1"></i>방송 제목을 입력해주세요.</div>
            <div v-else></div>
          </div>
        </div>


        <div class="description mt-3">
          <p class="text-start p-0 m-0" style="font-size: 16px;">방송 내용<span class="required text-danger"></span></p>
          <div class="position-relative" style="height: 120px;">
            <textarea class="w-100 position-absolute start-0 h-100 ps-2 pt-2 rounded" placeholder="방송 내용을 입력해주세요"
              style="font-size: 14px; border: 2px solid black; box-shadow: 0 2px 5px -2px rgba(0,0,0,0.5); resize: none;"
              v-model="liveInfo.description">
            </textarea>
          </div>
        </div>

        <div class="description mt-3">
          <p class="text-start p-0 m-0" style="font-size: 16px;">공개 여부</p>
          <div>
            <select class="w-100 rounded live-input" v-model="isPublic">
              <option :value="true">
                공개
              </option>
              <option :value="false">
                비공개
              </option>
            </select>
          </div>
        </div>

        <div class="description mt-3">
          <p class="text-start p-0 m-0" style="font-size: 16px;">비밀번호 설정<span class="required text-danger"></span></p>
          <div>
            <input type="text" placeholder="비밀번호를 입력해주세요 (4~12자 이내)" class="w-100 rounded live-input"
              v-model="liveInfo.password" 
              :disabled="isPublic"
            >
          </div>
        </div>
      </div>

      <div class="position-absolute bottom-0 end-0 m-3">
        <button class="me-3 bg-white" style="width: 100px; height: 35px; line-height: 35px; font-size: 14px; border:1px solid black; border-radius: 30px;" @click="createLiveRoom">
          방송시작
        </button>
        <button class="bg-white" style="width: 100px; height: 35px; line-height: 35px; font-size: 14px; border:1px solid black; border-radius: 30px;" @click="closeLiveSetting">
          취소
        </button>
      </div>
    </div>
  </div>
</template>
<script>
import axios from "axios"

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "LiveSetting",
  data: function() {
    return {
      liveInfo: {
        title: '',
        description: '',
        password: '',
        categoryId: 1,
      },
      isPublic: true,
      checkMessage: {
        title: '',
      },

    }
  },
  methods: {
    closeLiveSetting: function() {
      this.$emit('close-live-setting')
    },
    createLiveRoom: function() {
      // 빈값 체크
      const reg = /.{1,}/;
      if (!reg.test(this.liveInfo.title)) {
        return alert("방송 제목을 입력하세요")
      }
      if (!reg.test(this.liveInfo.description)) {
        return alert("방송 설명을 입력하세요")
      }

      const userInfo = { ...this.$store.state.Login.userInfo }

      const userId = userInfo.userId
      const hostNickname = userInfo.nickname
      const isProfessional = userInfo.isProfessional

      if (!isProfessional) {
        alert('전문가만 방송을 진행할 수 있습니다')
        return false
      }

      axios({
        method: "post",
        url: `${SERVER_URL}/live`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('jwt')}`
        },
        data: { 
          hostId: userId,
          ...this.liveInfo,
        },
      })
        .then((res) => {
          console.log(res)
          // location.href=`/live/${userId}`
          this.closeLiveSetting()
          this.$router.push({ name: "LiveRoom", params: { ...this.liveInfo, hostNickname, liveRoomId: res.data.liveRoomId, hostId: userId } })
        })
        .catch((err) => {
          // 에러 종류 2개
          console.log(err)
          alert("이미 라이브를 진행중입니다")
        })
    },
  },
}
</script>
<style>
  .live-input {
    height: 40px;
    line-height: 40px;
    font-size: 14px;
    box-shadow: 0 2px 5px -2px rgba(0,0,0,0.5);
    border: 2px solid black; 
    text-indent: .5rem;
  }
  .center-box {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
</style>
