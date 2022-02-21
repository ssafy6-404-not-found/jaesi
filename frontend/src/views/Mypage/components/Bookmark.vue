<template>
<div class="mypage-container">
  <div class="mypage-wrapper">
    <sidebar></sidebar>
    <div class="main-content bookmarks">
      <h1>찜 목록</h1>
      <div v-if="bookmarks.length != 0">
        <div class="bookmark-container">
          <bookmark-card
            v-for="bookmark in bookmarks"
            :key="bookmark.productId"
            :bookmark="bookmark"
            @refresh="changeBookmark($event)"></bookmark-card>
        </div>
        <pagination
          :totalPage="totalPage"
          :currPage="currPage"
          @selectPage="changeCurrPage($event, pageNum)"
          @next="nextPagination($event, pageNum)"
          @pervious="perviousPagination($event, pageNum)"></pagination>
      </div>
      <div v-else class="no-content no-bookmark">
        <img src="@/assets/no_content_bookmark.png">
        <div><span>찜한 상품</span>이 없습니다.</div>
        <div class="font-size-14">마음에 드는 상품을 <span>찜해보세요.</span></div>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import Sidebar from './Sidebar.vue'
import BookmarkCard from '../../../components/Mypage/BookmarkCard.vue'
import Pagination from "../../Product/Components/Pagination.vue"

import { useStore } from 'vuex'
import { computed, ref } from 'vue'

export default {
  name: 'Bookmark',
  components: {
    Sidebar,
    BookmarkCard,
    Pagination,
  },
  setup() {
    const store = useStore()

    const currPage = ref(1)
    store.dispatch('setBookmarks', currPage.value)
    const bookmarks = computed(()=>store.state.Mypage.bookmarks)
    const totalPage = computed(()=>store.state.Mypage.pagination.totalPages)


    const changeBookmark = function () {
      // console.log('parentnode')
      store.dispatch('setBookmarks', currPage.value)
    }

    const changeCurrPage = function (pageNum) {
      if (currPage.value !== pageNum) {
        store.dispatch('setBookmarks', pageNum)
        currPage.value = pageNum
      }
    }
    const nextPagination = function (pageNum) {
      changeCurrPage(pageNum)
      currPage.value = pageNum
    }
    const perviousPagination = function (pageNum) {
      changeCurrPage(pageNum)
      currPage.value = pageNum
    }    
    return { totalPage, bookmarks, changeCurrPage, nextPagination, perviousPagination, currPage, changeBookmark }
  }
}
</script>

<style>
.bookmark-container {
  display: flex;
  flex-wrap: wrap;
  width: 860px;
}
.no-bookmark > img {
  width: 200px;
  margin-bottom: 10px;
}
.no-bookmark > div {
  color: gray;
}
.no-bookmark > div > span {
  color:#FFB3C6;
  font-weight: 600;
}
.font-size-14 { font-size: 14px;}
</style>