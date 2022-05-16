<template>
  <div>
    <!--====== PRELOADER PART START ======-->

    <!-- <div class="loader-wrap">
        <div class="preloader"><div class="preloader-close">Preloader Close</div></div>
        <div class="layer layer-one"><span class="overlay"></span></div>
        <div class="layer layer-two"><span class="overlay"></span></div>        
        <div class="layer layer-three"><span class="overlay"></span></div>        
    </div> -->

    <!--====== PRELOADER PART ENDS ======-->

    <!--====== OFFCANVAS MENU PART START ======-->

    <sidebar-home-one :sidebar="sidebar" @toggleSidebar="toggleSidebar" :menuItems="navs" />

    <!--====== OFFCANVAS MENU PART ENDS ======-->

    <!--====== PART START ======-->

    <header-news @toggleSidebar="toggleSidebar" :menuItems="navs" />

    <!--====== PART ENDS ======-->

    <!--====== APPIE PAGE TITLE PART START ======-->

    <title-area />

    <!--====== APPIE PAGE TITLE PART ENDS ======-->

    <!-- Blog Start -->
    <section class="blogpage-section">
      <div class="container">
        <div class="row">
          <div class="col-lg-8 col-md-7">
            <all-blogs />
          </div>
          <div class="col-lg-4 col-md-5">
            <blog-sidebar />
          </div>
        </div>
      </div>
    </section>
    <!-- Blog End -->

    <!--====== APPIE FOOTER PART START ======-->

    <footer-home-one />

    <!--====== APPIE FOOTER PART ENDS ======-->

    <!--====== APPIE BACK TO TOP PART ENDS ======-->
    <div class="back-to-top">
      <a href="#"><i class="fal fa-arrow-up"></i></a>
    </div>
    <!--====== APPIE BACK TO TOP PART ENDS ======-->
  </div>
</template>

<script>
import SidebarHomeOne from "../HomeOne/SidebarHomeOne.vue";
import HeaderNews from "../News/HeaderNews.vue";
import TitleArea from "../News/TitleArea.vue";
import FooterHomeOne from "../HomeOne/FooterHomeOne.vue";
import AllBlogs from "../Blogs/AllBlogs.vue";
import BlogSidebar from "../BlogSidebar.vue";
export default {
  components: {
    SidebarHomeOne,
    HeaderNews,
    TitleArea,
    FooterHomeOne,
    AllBlogs,
    BlogSidebar,
  },
  data() {
    return {
      sidebar: false,
      navs: [
        {
          name: "home",
          childrens: [
            {
              name: "Home",
              path: "/",
            },
            {
              name: "Home Dark",
              path: "/home-dark",
            },
          ],
        },
        {
          name: "운동목록",
          childrens: [
            {
              name: "운동목록",
              path: "/news",
            },
          ],
        },
        {
          name: "마이페이지",
          path: "/mypage",
          childrens: [
            {
              name: "내정보",
              path: "/mypage/main",
            },
            {
              name: "내 글",
              path: "/mypage/myposts",
            },
            {
              name: "참여내역",
              path: "/mypage/myjoinlog",
            },
            {
              name: "운동기록",
              path: "/mypage/myworkoutlog",
            },
            {
              name: "평가페이지",
              path: "/mypage/myevallog",
            },
            {
              name: "자주묻는질문",
              path: "/mypage/qna",
            },
          ],
        },
      ],
    };
  },
  mounted() {
    document.addEventListener("scroll", this.topToBottom);
  },
  methods: {
    topToBottom() {
      const result = document.querySelector(".back-to-top");
      if (
        document.body.scrollTop > window.innerHeight ||
        document.documentElement.scrollTop > window.innerHeight
      ) {
        result.style.display = "block";
      } else {
        result.style.display = "none";
      }
    },
    toggleSidebar() {
      this.sidebar = !this.sidebar;
    },
  },
};
</script>

<style></style>
