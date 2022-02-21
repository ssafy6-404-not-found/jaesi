<template lang="">
  <div>
    <div style="width: 90%; margin: 0 auto;">
      <div style="text-align: center; color: #B4B4B4; font-size: 12px; font-weight: bold; margin: 1rem auto;">
        재시를 통하지 않고 <span style="color: red;">전문가에게 직접 결제 시<br>
        서비스 불이행 / 환불 거부 / 연락두절</span> 등의 문제가 발생할 수 있습니다<br>
        <span style="color: blue; text-decoration: underline;">직접 결제 피해 안내</span>
      </div>
      <!-- v-for chat message -->
      <!-- user instance가 없기 때문에 렌더링중 오류 발생, 로그인 진행 후 인스턴스 받아오기 -->
      <!-- :isMe="userId === user?.uid" -->
      <chat-message
        v-for="{ id, text, userId } in messages"
        :key="id"
        :text="text"
        :isMe="userId === myUserId"
      >
      </chat-message>
    </div>
    <div ref="bottom" class="mt-20" />
    <div class="bottom pb-1" style="position: absolute; bottom: 0; width: 100%; height:40px; padding-bottom: .25rem;">
      <div class="container-sm">
        <!-- v-if="isLogin" -->
        <form @submit.prevent="send" style="display:grid; grid-template-columns: 1fr 80px; padding: .25rem; padding-top: 0;">
          <input v-model="message" placeholder="메시지를 입력하세요. (Enter: 전송)" required style="border: 0; border-bottom: 1px solid black;" />
          <button type="submit" style="width:80px; height:40px; border: 0; background-color: #E8E8E8; border-radius: 5px; font-size: 16px; color: #979797;">전송
          </button>
        </form>
      </div>
    </div>

  </div>
</template>
<script>
import ChatMessage from './Chat-message.vue'
import { ref, watch, nextTick } from 'vue'
import { useChat } from '@/firebase'
import { useStore } from 'vuex'

export default {
  name: "ChatRoom",
  components: {
    ChatMessage
  },
  props: {
    chatId: String,
  },
  setup: function(props) {
    const { messages, sendMessage } = useChat('messageRoom' + props.chatId)
    const bottom = ref(null)
    watch(
      messages,
      () => {
        nextTick(() => {
          // bottom.value?.scrollIntoView({ behavior: 'smooth' })
        })
      },
      { deep: true }
    )
    const message = ref('')
    const myUserId = useStore().state.Login.userInfo.userId
    const send = () => {
      sendMessage(message.value, myUserId)
      message.value = ''
    }
    return { messages, bottom, message, myUserId, send }
  },
}
  // messages: [
  //   { id: 1, text: '안녕하세요', userId: 1 },
  // ]

</script>
<style lang="">
  
</style>