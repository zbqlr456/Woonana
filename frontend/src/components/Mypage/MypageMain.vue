<template>
  <div>
    <!-- <div id="mypagenav">
      <b-navbar toggleable="true" type="dark" variant="info">
        <b-navbar-brand>마이페이지</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse is-nav id="nav_collapse">
          <b-navbar-nav>
            <b-nav-item href="#">마이페이지1</b-nav-item>
            <b-nav-item href="#">마이페이지2</b-nav-item>
            <b-nav-item href="#">마이페이지3</b-nav-item>
            <b-nav-item href="#">마이페이지4</b-nav-item>
            <b-nav-item href="#">마이페이지5</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div> -->
    <div class="mypage">
      <b-navbar toggleable="true" type="dark" variant="info">
        <b-navbar-brand href="#">마이페이지</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="#">내 글</b-nav-item>
            <b-nav-item href="#">참여 내역</b-nav-item>
            <b-nav-item href="#">운동 기록</b-nav-item>
            <b-nav-item href="#">평가 페이지</b-nav-item>
            <b-nav-item href="#">고객 센터</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>

    <div class="mypage_content">
      <div class="row">
        <div class="col-md-1">
          <b-img v-bind:src="myinfo.userProfileUrl" rounded="circle" alt="Circle image"></b-img>
        </div>
        <div class="col-md-1">
          <p>{{ myinfo.userNickname }}</p>
        </div>
      </div>

      <div class="row">나와약속</div>
      <div class="row">
        <div class="col-md-1">
          <span class="badge bg-secondary">9: 30 누구랑 약속</span>
        </div>
      </div>
      <div class="row">모집진행중인모임</div>
      <!-- 버튼 모임 유무에따라 활성화, 비활성화 할것!!! -->
      <b-button size="lg">바로가기</b-button>
      <b-button disabled size="lg">바로가기</b-button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      //내정보
      myinfo: {},
      //약속 뿌려줄때 넣을것!!!
      appointment: {},
    };
  },
  methods: {
    //userStore에서 백에 요청해서 가져오는데 현재 http로 요청하면 헤더셋팅에 withCredentials가 안들어감
    getUserInfo: function () {
      this.$store.dispatch("getUserInfo");
    },
    //axios로 해야 withcredentials가 셋팅되서 잘됨,,, 현재 이걸루 하는중
    getUserInfoaxios: function () {
      let token = this.$store.getters.token;
      console.log(token);
      axios.defaults.withCredentials = false;
      axios.defaults.headers.common["withCredentials"] = false;
      axios.defaults.headers.common["Authorization"] = "Bearer " + token;

      axios.get("http://localhost:8081/api/accounts/mypage").then((response) => {
        console.log(response);
        this.myinfo = response.data;
        console.log(this.myinfo);
      });
    },
  },
  beforemount: function () {},
  mounted: function () {
    this.getUserInfoaxios();

    // console.log("userinfo", this.$store.getters.GET_USER_INFO);
    // this.getUserInfoaxios();
  },
};
</script>

<style>
.mypage_content {
  margin: 1%;
}
</style>
