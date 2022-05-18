<template>
  <div>
    <div class="mypage_content">
      <div class="profile" id="profile">
        <div class="col-md-1" id="profile-img-box">
          <b-img id="profile-img" v-bind:src="myinfomation.userProfileUrl"></b-img>
        </div>
        <div class="col-md-1">
          <p id="profile-nickname">{{ myinfomation.userNickname }}</p>
        </div>
      </div>
      <div>나의 약속</div>
      <div>
        <div>
          <span v-for="(appoint, index) in myappointment" :key="index" class="badge bg-secondary">{{
            appoint.boardMeetStartDate
          }}</span>
        </div>
      </div>
      <div>모집진행중인모임</div>
      <!-- 버튼 모임 유무에따라 활성화, 비활성화 할것!!! -->
      <b-button v-show="checkBoardid" size="lg" @click="movepost()">바로가기</b-button>
      <b-button v-show="!checkBoardid" disabled size="lg">바로가기</b-button>
    </div>
    <ul class="list-group list-group-flush">
      <li class="my-page-list list-group-item">내 글</li>
      <li class="my-page-list list-group-item">참여 내역</li>
      <li class="my-page-list list-group-item">운동 기록</li>
      <li class="my-page-list list-group-item">평가 페이지</li>
      <li class="my-page-list list-group-item">고객 센터</li>
    </ul>
  </div>
</template>
<script>
import http from "@/util/index";
export default {
  data() {
    return {
      //내정보
      myinfo: {},
      //약속 뿌려줄때 넣을것!!!
      appointment: [],
      boardId: 0,
      myrating: -1,
    };
  },
  methods: {
    //userStore에서 백에 요청해서 가져오는데 현재 http로 요청하면 헤더셋팅에 withCredentials가 안들어감
    getUserInfo: async function () {
      this.myinfo = await this.$store.dispatch("getUserInfo");
    },
    getParticipateInfo: async function () {
      let data = localStorage.getItem("vuex");
      let parsedata = JSON.parse(data);
      let token = parsedata.loginStore.jwtToken;
      http.defaults.headers.common["Authorization"] = "Bearer " + token;
      http.defaults.headers.common["withCredentials"] = false;
      let res = await http.get("/api/accounts/participate");

      console.log(res);
      this.appointment = res.data;
    },
    getBoardId: function () {
      //appointment배열중 오늘 날짜에 해당하거나 오늘날짜에 가장 가까운놈 하나 리턴해서 그 주소로 이동
      let today = new Date();
      let result = today;
      let boardId = -1;
      console.log(today);
      for (let appoint of this.appointment) {
        let date1 = new Date(appoint.boardMeetStartDate);
        console.log("오늘날짜 ", today);
        console.log(" 비교날짜 ", date1);
        if (today >= date1) {
          continue;
        } else if (result > date1) {
          result = date1;
          boardId = appoint.boardId;
        }
      }
      console.log(boardId);
      this.boardId = boardId;
      return boardId;
    },
    movepost: function () {
      //게시글 상세로 이동
      this.$router.push({
        name: "ShowBlog",
        params: { data: this.boardId },
      });
    },
    getMyRating: async function () {
      //내 평점정보 가져옴
      let data = localStorage.getItem("vuex");
      let parsedata = JSON.parse(data);
      let token = parsedata.loginStore.jwtToken;
      http.defaults.headers.common["Authorization"] = "Bearer " + token;
      http.defaults.headers.common["withCredentials"] = false;
      let res = await http.get("/api/accounts/evalue/me");
      console.log(res);
      this.myrating = await res.data.userRatingScore;
      // console.log(this.myrating);
    },
    //axios로 해야 withcredentials가 셋팅되서 잘됨,,, 현재 이걸루 하는중
  },
  beforemount: function () {},
  mounted: async function () {
    await this.getUserInfo();
    await this.getParticipateInfo();
    console.log(this.myinfo);
    console.log("userinfo", this.$store.getters.GET_USER_INFO);
    this.getBoardId();
    this.getMyRating();

    // this.getUserInfoaxios();
  },
  created() {
    this.getBoardId();
  },
  computed: {
    myinfomation: function () {
      return this.$store.getters.GET_USER_INFO;
    },
    myappointment: function () {
      return this.appointment;
    },
    checkBoardid: function () {
      return this.boardId != -1;
      // return false;
    },
  },
};
</script>

<style>
.mypage_content {
  margin: 1%;
}
#profile-and-rating {
  display: flex;
  align-items: baseline;
}
.profile {
  height: 100%;
}
#profile {
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
  margin-top: 60px;
}
#profile-img-box {
  width: 80px;
  height: 80px;
  border-radius: 70%;
  overflow: hidden;
  padding: 0;
}
#profile-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
#profile-nickname {
  font-weight: bold;
}
.my-page-list {
  font-weight: bold;
}
</style>
