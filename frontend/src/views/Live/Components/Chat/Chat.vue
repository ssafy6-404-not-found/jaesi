<template>
  <div class="d-grid rounded p-1" :style="styleObject">

    <!-- header -->
    <div class="text-start px-2" style="border-bottom: 1px solid black;">
      <span class="d-inline-block" style="line-height: 40px; font-size: 15px;">채팅창</span>
      <!-- buttons -->
      <div class="float-end">
        <i class="fa-comment me-2 far" style="font-size: 20px; line-height: 40px;" @click.prevent="toggleChat"></i>
        <i class="fas fa-times" style="font-size: 20px; line-height: 40px;"></i>
      </div>
    </div>

    <!-- 메시지 리스트 -->
    <div id="message-container" style="overflow: auto;">
      <message v-for="(msg, index) in msgs" :key="index" :msg="msg" :hostId="hostId" :myUserId="myUserId" style="color:black" />
    </div>

    <!-- 메시지 발송 -->
    <div id="live-chat-form" class="position-relative border-0 rounded" style="height: 50px;">
      <form @submit.prevent="send" style="">
        <textarea class="w-100 h-100 position-absolute start-0 ps-1 pt-1 border-0 rounded" placeholder="메시지 입력"
          style="font-size: 12px; resize: none; background-color: rgba(0,0,0,0.1); padding-right: 50px;"
          v-model="message"
          @keyup.enter.prevent="send"
        >
        </textarea>
        <input class="position-absolute h-100 border-0 pe-2 fw-bold end-0 bg-transparent" style="font-size: 12px; color: rgba(0,0,0,0.37);" type="submit" value="보내기">
      </form>
    </div>

  </div>
</template>

<script>
import Message from "./Components/Message"
export default {
  name: "MessageList",
  components: {
    Message,
  },
  data: function() {
    return {
      message: '',
    }
  },
  props: {
    msgs: Array,
    hostId: String,
    myUserId: Number,
    myUserNickname: String,
  },
  setup(props) {
    var myUserNickname = props.myUserNickname
    if(myUserNickname == undefined) {
      myUserNickname = '익명'
    }

    const maxHeight = window.innerHeight-32 + 'px'

    const styleObject = { 'grid-template-rows' : '40px 1fr 50px', border : '1px solid rgba(0, 0, 0, 0.7)', 'max-height' : maxHeight }

    return { myUserNickname, styleObject }
  },
  methods: {
    send: function() {
      const msg = this.message.trim()
      
      if (msg != "") {
        // userId까지 추가해서 보내기
        this.$emit("send-message", `[${this.myUserNickname}-${this.myUserId}] : ${msg}`)
      }

      this.message = ""
    },
  },
  watch: {
    msgs: function() {
      setTimeout(() => {
        var container = this.$el.querySelector("#message-container")
        container.scrollTop = container.scrollHeight+1000;
      }, 500);
      return this.msgs
    }
  }
};
</script>

<style scoped>
  textarea:focus {
    outline: none;
  }
  textarea::-webkit-scrollbar {
    border: 0;
  }
/* .container {
  height: 700px;
  width: 100%;
  background: rgba(207, 190, 207, 0.61);
  overflow: scroll;
} */
</style>
