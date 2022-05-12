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

import FooterHomeOne from "../HomeOne/FooterHomeOne.vue";
// import MypageQna from "./MypageQna.vue";
import TitleArea from "./TitieArea.vue";
import Mypagenav from "./Mypagenav.vue";
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
