<template lang="">
  <div>
    <div class="center-box d-flex justify-content-around" style="width: 80%;">
      <div @click="selectCustom(true)">
        <div class="rounded" 
          style="background: no-repeat center url(https://youthpress.net/xe/files/attach/images/9792/175/154/d54625790c9501d92899dd3e22fc66c2.jpg); background-size: auto 100%; width: 300px; height: 300px;"></div>
        <div class="mt-3 fw-bold" style="font-size: 14px;">구매자와 협의 후 제작</div>
      </div>
      <div @click="selectCustom(false)">
        <div class="rounded" 
          style="background: no-repeat center url(https://2.bp.blogspot.com/-qpYE1fmFvoA/WmlTuV8zbQI/AAAAAAAAarw/_QpyCWK5Jp8gzv489znTHIWG3LeYzf6dwCLcBGAs/s1600/%25ED%2583%259D%25EB%25B0%25B0_%25EB%25B6%2584%25EC%258B%25A4_%25EB%25B3%25B4%25EC%2583%2581_%25EC%25B2%25AD%25EA%25B5%25AC_%25EB%25B0%25A9%25EB%25B2%2595_01.jpg); background-size: auto 100%; width: 300px; height: 300px;"></div>
        <div class="mt-3 fw-bold" style="font-size: 14px;">요청사항에 따라 제작</div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'ServiceType',
  methods: {
    selectCustom: function(custom) {
      this.$emit('select-custom', custom)
    }
  },
}
</script>
<style lang="">
  
</style>
