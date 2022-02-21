<template lang="">
  <div class="chat-modal" style="width: 480px; height: 80%; background-color: white; border: 1px solid #c9c9c9; position: absolute; right: 5%; top: 120px; z-index: 3000;">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">

    <!-- draggable -->
    <!-- 헤더는 이곳에서 관리, emit으로 채팅리스트인지 채팅방인지 체크, 채팅방 어떤 데이터인지 넘겨주기 props로-->
    <div style="height: 40px; background-color: #C1D3FE; line-height: 40px;">
      <!-- 채팅방 리스트인지 -->
      <div v-if="isList" style="font-size: 18px;">
        채팅목록
      </div>
      <!-- 채팅방인지 -->
      <div v-if="isRoom" style="display: flex;">
        <div style="width: 40px; text-align: center;" @click="getChatList">
          <i class="fas fa-chevron-left" style="line-height: 40px; font-size: 28px;"></i>
        </div>
        <img :src="unKnown" style="width: 32px; height: 32px; margin-left: 4px; margin-top: 4px;">
        <div style="margin-left: 1rem; font-size: 18px;">
          {{ chatNickname == '' ? '' : chatNickname }}
          <!-- 채팅방 메뉴 오픈 -->
          <i class="fas fa-chevron-down" style="line-height: 40px;"></i>
        </div>
      </div>
      
      <!-- 닫기 버튼 -->
      <div style="position: absolute; top: 0; right: .25rem; width: 40px;" @click="closeChat">
        <i class="fas fa-times" style="height: 40px; line-height: 40px; font-size: 32px;"></i>
      </div>
    </div>

    <!-- 채팅목록 -->
    <!-- 내용물만 변경 -->
    <div>
      <div v-if="isList" class="chat-list" style="overflow-y: scroll;">
        <!-- 1. 채팅목록 -->
        <!-- chat-list -->
        <chat-list 
          :chatList="chatList"
          @get-chat-room="getChatRoom"
        />
      </div>
      <div v-if="isRoom">
        <chat-room 
          :chatId="chatId"
          />
        <!-- 2. 채팅방 -->
        <!-- chat-room -->
      </div>

    </div>

  </div>
</template>
<script>
import ChatRoom from '@/components/Chat/Components/Chat-room'
import ChatList from '@/components/Chat/Components/Chat-list'
import unKnown from './Components/unKnown.png'

import axios from 'axios'

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'ChatModal',
  components: {
    ChatRoom,
    ChatList
  },
  props: {
    isShow: Boolean
  },
  data: function() {
    return {
      isList: true,
      isRoom: false,
      chatId: '',
      chatNickname: '',
      chatList: [],
      unKnown,
    }
  },
  methods: {
    closeChat: function() {
      this.$emit('close-chat-modal')
    },
    getChatRoom: function(chatId, chatNickname) {
      this.isList = false
      this.isRoom = true
      // chatId 전달
      this.chatId = chatId
      this.chatNickname = chatNickname
    },
    getChatList: function() {
      axios({
        method: "get",
        url: `${SERVER_URL}/chat/${this.$store.state.Login.userInfo.userId}`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('jwt')}`
        },
      })
        .then((res) => {
          this.chatList = res.data
        })
        .catch((err) => {
          // 에러 종류 2개
          console.log(err)
        })
      this.isList = true
      this.isRoom = false
    },
  },
  mounted: function() {
    if(localStorage.getItem('jwt')){
      this.getChatList()
    }
    document.getElementsByClassName('chat-list')[0].style.height = 
      document.getElementsByClassName('chat-modal')[0].offsetHeight - 40 + "px"
  },
}
</script>
<style lang="">
  
</style>