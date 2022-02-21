<template lang="">
  <div>
    <div>
      <!-- height 조절.. 정사각형으로 -->
      <label for="serviceImage">
      <div class="mx-auto my-3 fw-bold position-relative" style="width: 600px; height: 600px; border: 2px dashed black;">
        <div class="center-box" style="font-size: 24px;">
          <span style="font-size: 36px;">+</span><br>
          이미지 등록하기
        </div>
      </div>
      </label>
      
    </div>
    <input id="serviceImage" class="d-none" type="file" accept="image/png, image/jpeg" @change="selectImage">
  </div>
</template>
<script>
import firebase from 'firebase/app'
import 'firebase/storage'

// import { getStorage, ref, uploadBytesResumable, getDownloadURL } from "firebase/storage"

export default {
  name: 'ServiceImage',
  data: function() {
    return {
      
    }
  },
  methods: {
    selectImage: function(event) {
      // 이미지 등록 프로세스, url 가져오기
      const imageData = event.target.files[0];
      let fileName = imageData.name
      const storageRef = firebase.storage().ref(`images/${fileName}`).put(imageData)
      storageRef.on("state_changed", 
      snapshot => {
        console.log(snapshot);
        // 업로드 중입니다 loading pin
        // upload percentage = (snapshot.bytesTransferred/snapshot.totalBytes)*100
      },
      error => {
        console.log(error);
      },
      () => {
        storageRef.snapshot.ref.getDownloadURL().then( url => {
          // console.log("성공");
          this.$emit('select-image', url)
        })
      })
    }
  },
}
</script>
<style lang="">
  
</style>