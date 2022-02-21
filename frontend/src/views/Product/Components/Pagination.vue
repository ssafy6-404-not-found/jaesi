<template>
  <ul class="pagination-container">
    <li v-if="currPage > 10" @click="pervious()">이전</li>
    <li
      class="pagination-page text-bold"
      v-for="pageNum in pages"
      :key="pageNum"
      :class="[pageNum === currPage ? 'selected-page': '']"
      @click="$emit('selectPage', pageNum)"
    >
      {{ pageNum }}
    </li>
    <li 
      v-if="parseInt((currPage-1)/10)!==parseInt((totalPage-1)/10)" 
      @click="next()">다음</li>
  </ul>
</template>
  
  <script>
  export default {
    name: 'Pagination',
    props: {
      totalPage: Number,
      selectPage: Function,
      currPage: Number,
    },
    // 현재 페이지 // 10 
    computed: {
      startPage() {
        return parseInt((this.currPage-1)/10)*10+1
      },
      endPage() {
        return parseInt((this.currPage-1)/10)*10+10 < this.totalPage ? parseInt((this.currPage-1)/10)*10+10 : this.totalPage
      },
      pages() {
        // console.log([...Array(this.startPage, this.endPage+1)].map(i=>i))
        let temp = []
        for (let i=this.startPage; i<=this.endPage; ++i) {
          temp.push(i)
        }
        return temp
      }
    },
    methods: {
      next() {
        this.$emit('next', (parseInt((this.currPage-1)/10)+1)*10+1)
      },
      pervious() {
        this.$emit('pervious', parseInt((this.currPage-1)/10)*10)
      }
    }
  }
  </script>
  
  <style>
  .pagination-container {
    margin-top: 10px;
    /* height: 10%;
    width: 100%; */
    display: flex;
    align-items: center;
    justify-content: center;
    list-style: none;
    padding: 0px;

    /* 드래그 막기 */
    -ms-user-select: none; 
    -moz-user-select: -moz-none;
    -khtml-user-select: none;
    -webkit-user-select: none;
    user-select: none;
  }
  .pagination-container > li {
    margin: 0 3px 0 3px;  
    font-size: 12px;
    cursor: pointer;
  }
  .pagination-page {
    padding-top: 3.5px;
    margin-top: 2px;
    width: 25px;
    height: 25px;
    font-size: 13px;
  }
  .selected-page { 
    background-color: #C1D3FE;
    color: white;  
  }
  </style>