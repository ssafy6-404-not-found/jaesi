<template>
  <div class="loading"><div class="spinner"></div></div>
</template>

<script>
export default {

}
</script>

<style>
.loading {
  /* display: none; */
	width: 100%;
	height: 100%;
	position: absolute;
	top:0;
	left:0;
}
@keyframes spinner {
  from {transform: rotate(0deg); }
  to {transform: rotate(360deg);}
}
 
.loading .spinner {
  box-sizing: border-box;
  position: absolute;
  top: 50%;
  left: 50%;
  width: 64px;
  height: 64px;
  margin-top: -32px;
  margin-left: -32px;
  border-radius: 50%;
  border: 8px solid transparent;
  border-top-color: #C1D3FE;
  border-bottom-color: #C1D3FE;
  animation: spinner .8s ease infinite;
}
</style>