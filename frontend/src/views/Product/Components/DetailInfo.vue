<template>
  <div class="detailInfo">{{ content }}</div>
</template>
<script>
export default {
  name:'DetailInfo',
  props: {
    content: String,
  }
}
</script>
<style>
.detailInfo {
  text-align: left;
  margin-top: 15px;
  margin-left: 10px;
}
</style>