<template lang="">
  <div>
    <chat-card 
      v-for="(chat, index) in chatTargetList" :key="chat.id"
      :chat="chat"
      @click="$emit('get-chat-room', chat.id, chat.targetNickname)"
    />
  </div>
</template>
<script>
import { useStore } from 'vuex'
import ChatCard from './Chat-card.vue'

export default {
  name: 'ChatList',
  components: {
    ChatCard
  },
  props: {
    chatList: Array,
  },
  computed: {
    chatTargetList: function() {
      const myUserId = useStore().state.Login.userInfo.userId

      console.log(this.chatList)

      return this.chatList.map(item => {
        var targetId = 0
        var targetNickname = ''
        var targetProfile = ''
        if(item.receiveUserId == myUserId){
          targetId = item.sendUserId
          targetNickname = item.sendUserNickname
          targetProfile = item.sendUserProfile
        }
        else {
          targetId = item.receiveUserId
          targetNickname = item.receiveUserNickname
          targetProfile = item.receiveUserProfile
        }
        return { id: item.chatId, targetId, targetNickname, targetProfile }
      })
    }
  }
}
</script>
<style lang="">
  
</style>