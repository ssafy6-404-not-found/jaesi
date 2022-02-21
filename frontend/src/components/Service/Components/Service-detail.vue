<template lang="">
  <div>
    <div class="d-grid" style="grid-template-columns:1fr 10px 2fr;">
      <div>
        <img :src="url" class="w-100">
        <div class="fw-bold" style="font-size: 16px;" @click="$emit('change-to-image')">+ 이미지 변경하기</div>
      </div>
      <div />
      <div>
        <!-- 서비스 detail -->
        <div class="position-relative me-3 mb-3" style="height: 40px; line-height: 40px;">
          <input class="w-100 position-absolute start-0" style="font-size: 14px; padding-left: 100px; box-shadow: 0 2px 5px -2px rgba(0,0,0,0.5); border: 1px solid #C9C9C9; border-radius: 5px;"
            placeholder="서비스를 잘 드러낼 수 있는 제목을 입력해주세요"
            v-model="title" @keyup="limitTitle" 
          >
          <span class="position-absolute text-start ps-3 fw-bold start-0">제목</span>
          <span class="position-absolute text-end pe-2 fw-bold end-0" style="color: #9D9D9D;">{{ title.length }}/30</span>
        </div>
        <div class="position-relative me-3 mb-3" style="height: 40px; line-height: 40px;">
          <input class="w-100 position-absolute start-0" style="font-size: 14px; padding-left: 100px; box-shadow: 0 2px 5px -2px rgba(0,0,0,0.5); border: 1px solid #C9C9C9; border-radius: 5px;"
            placeholder="서비스의 가격을 입력해주세요"
            v-model="price" @keyup="limitTitle" 
          >
          <span class="position-absolute text-start ps-3 fw-bold start-0">가격</span>
        </div>
        <div class="position-relative me-3 mb-3" style="height: 40px; line-height: 40px;">
          <input class="w-100 position-absolute start-0" style="font-size: 14px; padding-left: 100px; box-shadow: 0 2px 5px -2px rgba(0,0,0,0.5); border: 1px solid #C9C9C9; border-radius: 5px;"
            placeholder="서비스의 제작 기간을 입력해주세요"
            v-model="productionPeriod" @keyup="limitTitle"
          >
          <span class="position-absolute text-start ps-3 fw-bold start-0">제작기간</span>
        </div>

        <div class="position-relative me-3 mb-3" style="height: 360px;">
          <!-- TextField -->
          <textarea class="w-100 position-absolute start-0 h-100 ps-3" :placeholder="guide"
            style="font-size: 14px;padding-top: 40px; box-shadow: 0 2px 5px -2px rgba(0,0,0,0.5); border: 1px solid #C9C9C9; border-radius: 5px; resize: none;"
            v-model="content">
          </textarea>
          <!-- <input class="w-100 position-absolute start-0" style="font-size: 14px; padding-left: 100px; box-shadow: 0 2px 5px -2px rgba(0,0,0,0.5); border: 1px solid #C9C9C9; border-radius: 5px;"
            placeholder="서비스의 내용을 자세하게 입력해주세요"
            v-model="content" @keyup="limitContent" 
          > -->
          <span class="position-absolute text-start ps-3 fw-bold start-0" style="line-height:40px;">내용</span>
          <span class="position-absolute text-end pe-2 fw-bold end-0 bottom-0" style="color: #9D9D9D;">{{ content.length }}/3,000</span>
        </div>
        <button class="btn btn-sm btn-outline-info float-end me-3 mb-3" @click="completeDetail">다음</button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'ServiceDetail',
  props: {
    url: { type: String, default:'' },
  },
  data: function() {
    return {
      title: '',
      content: '',
      price: '',
      productionPeriod: '',
      guide: "서비스의 내용을 자세하게 입력해주세요\n\n몇 가지 가이드라인 제공\n •  문의 시 기획서나 아이디어를 상제히 적어주세요\n •  구매 시 요청사항에 이니셜을 입력해주세요"
    }
  },
  methods: {
    limitTitle: function() {
      if(this.title.length > 30){
        this.title = this.title.slice(0, 30)
        return
      }
    },
    checkPrice: function() {
      // 숫자만 입력할 수 있습니다.
    },
    limitContent: function() {
      if(this.content.length > 3000){
        this.content = this.content.slice(0, 3000)
        return
      }
    },
    completeDetail: function() {
      var title = this.title
      var content = this.content
      var price = this.price
      var productionPeriod = this.productionPeriod

      // form check
      if(title.trim()==''){
        alert('제목을 입력해주세요')
        return false
      }
      if(content.trim()==''){
        alert('내용을 입력해주세요')
        return false
      }
      if(price.trim()==''){
        alert('가격을 입력해주세요')
        // 가격은 숫자만 입력해주세요
        return false
      }
      if(productionPeriod.trim()==''){
        alert('제작 기간을 입력해주세요')
        return false
      }
      
      this.$emit('complete-detail', { title, content, price, productionPeriod })
    },
  },
}
</script>
<style scoped>
  input:focus {outline:none;}
  textarea:focus {outline:none;}
</style>
