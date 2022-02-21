<template lang="">
  <div style="width: 100%; height:120px; display: grid; grid-template-columns: 100px 1fr; background-color: rgba(193, 211, 254, 0.25);">
    <div>
      <img :src="chat.targetProfile == null ? unKnown : chat.targetProfile" style="width: 80px; height: 80px; margin-left: 20px; margin-top: 20px; border-radius:70%;">
    </div>
    <div style="display: grid; grid-template-rows: 60px 60px;">
      <div style="height: 40px; font-weight: 700; display: flex; justify-content: space-between; padding: 20px 1rem;">
        <div style="display: flex;">
          <span style="font-size: 20px; margin-right: .25rem;">{{ chat.targetNickname }}</span>
          <!-- <div style="position: relative;">
            <div style="position: absolute;
                top: 10%;
                font-size: 14px;
                line-height: 15px;
                border-radius: 50%;
                width: 14px;
                height: 14px;
                background-color: red;
                border: 4px solid $n;
                color: white;
                text-align: center;">
                1
            </div>
          </div> -->
        </div>
        <div style="font-size: 14px;">
          &nbsp;
        </div>
      </div>
      <div style="text-align: left; padding: .25rem 1rem; font-size: 16px;">
        <!-- {{ messages[messages.length-1].text }} -->
      </div>
    </div>
  </div>
</template>
<script>
import unKnown from './unKnown.png'

export default {
  name: "ChatCard",
  props: {
    chat: Object,
  },
  data: function() {
    return {
      unKnown,
    }
  },
}
</script>
<style lang="">
  /* hover 효과 */
  
</style>