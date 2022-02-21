<template lang="">
  <div>
    <!-- 서비스 등록하기 축하메시지 등 -->
    <div class="center-box d-flex justify-content-around" style="width: 80%;">
      <!-- 환영 메시지 -->
      <div>
        서비스 등록을 축하합니다 !
      </div>
      <!-- 버튼 - 완료, 취소 -->

    </div>
    
    <div class="float-end">
      <div class="btn btn-sm btn-outline-success" @click="this.$emit('create-service')">
        완료
      </div>
      <div class="btn btn-sm btn-outline-danger" @click="this.$emit('cancle-service')">
        취소
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "ServiceComplete",
}
</script>
<style lang="">
  
</style>
