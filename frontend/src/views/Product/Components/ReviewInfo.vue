<template>
  <!-- <div v-for="rev in review" :key="rev.id">{{ rev.content }}</div> -->
  <review-card
     v-for="rev in review"
     :key="rev.id"
     :review="rev"
     :detail="true">
  </review-card>
</template>


<script>
import ReviewCard from "../../../components/Mypage/ReviewCard.vue"

export default {
  name:'ReviewInfo',
  props: {
    review: Object,
  },
  components: {
    ReviewCard
  }
}
</script>