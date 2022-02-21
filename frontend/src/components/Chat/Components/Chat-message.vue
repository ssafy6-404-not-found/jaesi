<template>
  <div class="message mt-1">
    <!-- class화 후 div태그 통합하기 -->
    <div v-if="isMe" style="display: flex; flex-direction: row-reverse;">
      <!-- bg 상대 E8E8E8, 나 C1D3FE 25% -->
      <div class="text" style="padding: .25rem; background-color: rgba(193, 211, 254, 0.25); border-radius: 5px;" :class="isMe ? 'bg-green-800' : 'bg-gray-700'">
        {{ text }}
      </div>
    </div>
    <div v-else style="display: flex;">
      <div class="text" style="padding: .25rem; background-color: #E8E8E8; border-radius: 5px;" :class="isMe ? 'bg-green-800' : 'bg-gray-700'">
        {{ text }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    isMe: { type: Boolean, default: false },
    text: { type: String, default: '' },
  }
}
</script>