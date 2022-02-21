<template>
  <div class="p-2">
    <div class="d-grid" style="grid-template-columns: 40px 1fr;">
      <div class="position-relative">
        <img class="center-box" :src="profileImage" alt="" width="30" height="30">
      </div>
      <!-- colorbar -->
      <div class="d-flex">
        <div class="" style="width: 1px; border: 2px solid black;" :style="{ 'border-color': msgColor }">&nbsp;</div>
        <div class="d-grid text-start ps-2" style="grid-template-rows: 23px 4px 1fr;">
          <span style="font-size: 13px; line-height: 23px;">{{ userNickname }} <span :style="{ color: msgColor }">{{ msgType }}</span></span>
          <span></span>
          <span style="font-size: 15px;">{{ parsedMessage }}</span>
        </div>
        
      </div>
    </div>
  </div>
</template>

<script>
import profileImage from '@/components/Chat/Components/unKnown.png'
export default {
  name: "Message",
  data: function() {
    return {
      userNickname: '',
      userType: 0,
      profileImage,
    }
  },
  props: {
    msg: String,
    hostId: String,
    myUserId: String,
  },
  computed: {
    parsedMessage: function() {
      let userPart = this.msg.match(/(?<=\[)((?!\]).)*(?=\])/)[0].split('-')
      let nickname = userPart[0]
      let userId = userPart[1]

      // 호스트 여부
      if(userId == this.hostId) {
        this.userType = 1
      }
      // 본인 여부
      else if(userId == this.myUserId) {
        this.userType = 2
      }

      let msgPart = this.msg.match(/(?<=\: ).*/)[0]
      this.userNickname = nickname
      return msgPart
    },
    msgColor: function() {
      return this.userType == 0 ? '#FCE1E4' : this.userType == 1 ? '#C1D3FE' : '#69F0AE'
    },
    msgType: function() {
      return this.userType == 0 ? '' : this.userType == 1 ? '(호스트)' : '(본인)'
    }
  }
};
</script>

<style scoped>
  .center-box {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
</style>