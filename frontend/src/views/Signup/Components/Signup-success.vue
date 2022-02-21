<template>
  <div>
    <div class="signup-header">
      <div class="logo">
        재능마켓
      </div>
    </div>

    <div>
      <h2 style="font-size: 48px;">환영합니다!</h2>

      <p>{{ name }}님, 회원가입을 축하합니다.</p>
      <p>가입 이메일 주소 : {{ email }}</p>

      <div>
        기타 가이드라인
      </div>

      <button class="w-100 border-0 mt-3 text-white" style="height: 52px; background-color: #C1D3FE; font-size: 24px;" @click="$router.push( { name:'Home' })">시작하기</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SignupSuccess',
  props: {
    name: String,
    email: String,
  },
  data: function() {
    return {

    }
  },
}
</script>
<style>
  
</style>
