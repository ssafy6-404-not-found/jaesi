<template lang="">
  <div>
    <!-- 데이터 받아와서 그리기 -->
    <div 
      v-for="(item, index) in items" :key="index"
      class="m-3 text-end d-flex flex-row-reverse align-items-end opacity-75" 
      style="height: 160px; background-size: 100% !important;" 
      :style="{ 'background': 'no-repeat right url(' + item.url + ')'}"
      @click="$emit('select-category', item.id)"
    >
      <span class="fw-bold text-white" style="font-size: 36px;">{{ item.name }}</span>
    </div>
  </div>
</template>
<script>
export default {
  name: 'ServiceCategory',
  data: function() {
    return {
      items: [],
    }
  },
  methods: {
    
  },
  mounted: function() {
    // axios로 카테고리 데이터 불러오기
    this.items = [
      {'id':1, 'name': '개발', 'url': 'https://www.zdnet.com/a/img/resize/b52a0b45b5856e860200a6da05f2f32ec9cc48ae/2021/07/19/8a337c80-5ed6-43a1-98fb-b981d420890f/programming-languages-shutterstock-1680857539.jpg?width=1200&height=675&fit=crop&auto=webp'},
      {'id':2, 'name': '디자인', 'url': 'https://www.affde.com/uploads/article/84316/AIqIVaJ3d1v3SWXs.jpg'},
      {'id':3, 'name': '핸드메이드', 'url': 'https://blog.hmgjournal.com/images/contents/article/201605031455-Reissue-hadmade-life-01.jpg'},
    ]
  },
}
</script>
<style lang="">
  
</style>