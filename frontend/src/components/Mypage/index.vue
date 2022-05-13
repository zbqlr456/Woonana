<template>
  <div>
    <!--====== OFFCANVAS MENU PART START ======-->

    <!--====== OFFCANVAS MENU PART ENDS ======-->
    <!--====== APPIE HEADER PART START ======-->

    <header-home-one @toggleSidebar="toggleSidebar" :menuItems="navs" />

    <!--====== APPIE HEADER PART ENDS ======-->
    <!--====== APPIE PAGE TITLE PART START ======-->

    <!-- reference by news -->
    <title-area />
    <!-- <star-rating
      v-bind:increment="0.5"
      v-bind:max-rating="5"
      inactive-color="#000"
      active-color="#f00"
      v-bind:star-size="90"
    >
    </star-rating> -->
    <mypagenav />
    <!--====== APPIE PAGE TITLE PART ENDS ======-->
    <router-view />

    <!--====== APPIE FOOTER PART START ======-->
    <footer-home-one />

    <!--====== APPIE FOOTER PART ENDS ======-->
  </div>
</template>

<script>
import HeaderHomeOne from "../HomeOne/HeaderHomeOne.vue";

import Mypagenav from "./Mypagenav.vue";
import FooterHomeOne from "../HomeOne/FooterHomeOne.vue";
// import MypageQna from "./MypageQna.vue";
import TitleArea from "./TitieArea.vue";

export default {
  components: {
    HeaderHomeOne,
    FooterHomeOne,
    // MypageQna,
    TitleArea,
    Mypagenav,
  },
};
</script>

<style>
.faq-wrapper[data-v-5d9392b3] {
  margin: auto;
}
</style>
