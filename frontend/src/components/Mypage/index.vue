<template>
  <div>
    <!--====== OFFCANVAS MENU PART START ======-->

    <sidebar-home-one :sidebar="sidebar" @toggleSidebar="toggleSidebar" :menuItems="navs" />

    <!--====== OFFCANVAS MENU PART ENDS ======-->
    <!--====== APPIE HEADER PART START ======-->

    <header-home-one @toggleSidebar="toggleSidebar" :menuItems="navs" />

    <!--====== APPIE HEADER PART ENDS ======-->
    <!--====== APPIE PAGE TITLE PART START ======-->

    <!-- reference by news -->
    <title-area />

    <!--====== APPIE PAGE TITLE PART ENDS ======-->
    <mypage-qna />

    <!--====== APPIE FOOTER PART START ======-->
    <footer-home-one />

    <!--====== APPIE FOOTER PART ENDS ======-->
  </div>
</template>

<script>
import HeaderHomeOne from "../HomeOne/HeaderHomeOne.vue";
import SidebarHomeOne from "../HomeOne/SidebarHomeOne.vue";
import FooterHomeOne from "../HomeOne/FooterHomeOne.vue";
import MypageQna from "./MypageQna.vue";
import TitleArea from "./TitieArea.vue";
export default {
  components: {
    SidebarHomeOne,
    HeaderHomeOne,
    FooterHomeOne,
    MypageQna,
    TitleArea,
  },
};
</script>

<style>
.faq-wrapper[data-v-5d9392b3] {
  margin: auto;
}
</style>
