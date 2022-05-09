<template>
  <div id="app">
    <!-- <div class="kmap" ref="map"></div> -->
    <KakaoMap class="kmap" :options="mapOption" />
    <img alt="Vue logo" src="./assets/logo.png" />
    <router-view></router-view>
  </div>
</template>

<script>
import KakaoMap from './components/map/KakaoMap.vue'

export default {
  name: "App",
  components: {
   KakaoMap
  },
  data(){
    return {
      mapOption:{
        center : {
          lat : 33.450701,
          lng : 126.570667,
        },
        level : 8,
      }
    }
  },
  mounted() {
    
  },
};
</script>

<style>

</style>
