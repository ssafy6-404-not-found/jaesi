<template lang="">
  <div class="w-100 h-100 position-absolute top-0 start-0" style="background-color: rgba(0,0,0,0.25)">
    <div class="bg-white center-box rounded" style="width: 60%; min-height: 658px; max-height:898px;">
      <!-- header -->
      <div>
        <div class="text-start m-3 fw-bold" style="font-size: 24px;">
          새 서비스 등록
        </div>
        <div class="d-flex">
          <!-- 단계 1 -->
          <div class="d-inline-block" style="width: 100px; height: 60px;" @click="changeProgress(0)">
            <div class="mx-auto" style="width: 28px; height:28px; border-radius: 50px; background-color: rgba(193,211,254, 0.2);"></div>
            <div class="fw-bold mt-2" style="font-size: 14px;">카테고리 선택</div>
          </div>
          <!-- 위치 이동? or 여러개 생성? -->
          <div v-show="progress == 0" style="color: #FFB3C6; font-size: 28px;">
            <i class="fas fa-caret-right"></i>
            <i class="fas fa-caret-right"></i>
            <i class="fas fa-caret-right"></i>
          </div>
          <div class="d-inline-block" style="width: 100px; height: 60px;" @click="changeProgress(1)">
            <div class="mx-auto" style="width: 28px; height:28px; border-radius: 50px;" :style="{ 'background-color': completed[0] ? 'rgba(193,211,254, 0.4)' : '#E5E5E5' }"></div>
            <div class="fw-bold mt-2" style="font-size: 14px;">이미지 선택</div>
          </div>
          <div v-show="progress == 1" style="color: #FFB3C6; font-size: 28px;">
            <i class="fas fa-caret-right"></i>
            <i class="fas fa-caret-right"></i>
            <i class="fas fa-caret-right"></i>
          </div>
          <div class="d-inline-block" style="width: 100px; height: 60px;" @click="changeProgress(2)">
            <div class="mx-auto" style="width: 28px; height:28px; border-radius: 50px;" :style="{ 'background-color': completed[1] ? 'rgba(193,211,254, 0.6)' : '#E5E5E5' }"></div>
            <div class="fw-bold mt-2" style="font-size: 14px;">정보 입력</div>
          </div>
          <div v-show="progress == 2" style="color: #FFB3C6; font-size: 28px;">
            <i class="fas fa-caret-right"></i>
            <i class="fas fa-caret-right"></i>
            <i class="fas fa-caret-right"></i>
          </div>
          <div class="d-inline-block" style="width: 100px; height: 60px;" @click="changeProgress(3)">
            <div class="mx-auto" style="width: 28px; height:28px; border-radius: 50px;" :style="{ 'background-color': completed[2] ? 'rgba(193,211,254, 0.8)' : '#E5E5E5' }"></div>
            <div class="fw-bold mt-2" style="font-size: 14px;">작업 방식 선택</div>
          </div>
          <div v-show="progress == 3" style="color: #FFB3C6; font-size: 28px;">
            <i class="fas fa-caret-right"></i>
            <i class="fas fa-caret-right"></i>
            <i class="fas fa-caret-right"></i>
          </div>
          <div class="d-inline-block" style="width: 100px; height: 60px;" @click="changeProgress(4)">
            <div class="mx-auto" style="width: 28px; height:28px; border-radius: 50px;" :style="{ 'background-color': completed[3] ? 'rgba(193,211,254, 1)' : '#E5E5E5' }"></div>
            <div class="fw-bold mt-2" style="font-size: 14px;">완료</div>
          </div>
          
        </div>
        <!-- 닫기 버튼 -->
        <div class="position-absolute" style="top: 0; right: 0; width: 40px;" @click="closeServiceModal">
          <i class="fas fa-times" style="height: 40px; line-height: 40px; font-size: 24px;"></i>
        </div>

      </div>

      <hr />
      <div class="p-2">
        <service-category
          v-show="progress == 0"
          @select-category="selectCategory"
        />
        <service-image 
          v-show="progress == 1"
          @select-image="selectImage"
        />
        <service-detail 
          v-show="progress == 2"
          :url="serviceInfo.img"
          @change-to-image="changeProgress(1)"
          @complete-detail="completeDetail"
        />
        <service-custom 
          v-show="progress == 3"
          @select-custom="selectCustom"
        />
        <service-complete 
          v-show="progress == 4"
          @create-service="createService"
          @cancle-service="closeServiceModal"
        />
      </div>
    </div>
  </div>
</template>
<script>
import { computed } from "vue"

import ServiceCategory from './Components/Service-category.vue'
import ServiceImage from './Components/Service-image.vue'
import ServiceDetail from './Components/Service-detail.vue'
import ServiceCustom from './Components/Service-custom.vue'
import ServiceComplete from './Components/Service-complete.vue'

import axios from 'axios'

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'ServiceModal',
  components: {
    ServiceCategory,
    ServiceImage,
    ServiceDetail,
    ServiceCustom,
    ServiceComplete,
  },
  data: function() {
    return {
      progress: 0,
      completed: [false, false, false, false],
      // {
      //   category: false,
      //   image: false,
      //   detail: false,
      //   type: false,
      // },
      serviceInfo: {
        category: -1,
        title: '',
        content: '',
        price: 0,
        img: '',
        custom: false,
        productionPeriod: 0
      }
    }
  },
  methods: {
    closeServiceModal: function() {
      this.$emit('close-service')
    },
    changeProgress: function(idx) {
      // 만약, 이동하려는 곳의 조건이 부합하지 않을 경우 alert 메시지
      // if(idx) {
      //   if(!this.completed[idx-1]) {
      //     alert('아직 선택되지 않은 단계가 있습니다.')
      //     return
      //   }
      // }
      this.progress = idx
    },
    selectCategory: function(categoryId) {
      this.serviceInfo.category = categoryId
      this.completed[0] = true
      this.changeProgress(1)
    },
    selectImage: function(url) {
      this.serviceInfo.img = url
      this.completed[1] = true
      this.changeProgress(2)
    },
    completeDetail: function(serviceDetail) {
      this.completed[2] = true
      this.serviceInfo = {
        ...this.serviceInfo,
        ...serviceDetail
      }

      this.changeProgress(3)
    },
    selectCustom: function(custom) {
      this.completed[3] = true
      this.serviceInfo = {
        ...this.serviceInfo,
        custom
      }
      this.changeProgress(4)
    },
    createService: function() {
      const userId = computed(() => { return this.$store.state.Login.userInfo }).value.userId
      

      if(!userId) {
        alert('유저 정보가 없습니다.')
        this.closeServiceModal()
        if (!useStore().showLogin) { useStore().dispatch('showLoginModal') }
        return
      }

      this.completed.forEach(element => {
        if(!element) { alert('완료되지 않은 부분이 있습니다.'); return false }
      })

      axios({
        method: "post",
        url: `${SERVER_URL}/product`,
        headers: {
          Authorization: `Bearer ${localStorage.getItem('jwt')}`
        },
        data: { 
          user: userId,
          ...this.serviceInfo,
        },
      })
        .then((res) => {
          console.log(res.data)
          // product id 받아서 front 서비스 목록 데이터에 추가 (vuex)

          // close modal, 데이터 초기화
          this.closeServiceModal()
        })
        .catch((err) => {
          console.log(err)
        })
    },
  },
}
</script>
<style>
  .box {
    color: red;
  }

  .center-box {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
</style>
