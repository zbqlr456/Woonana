<template>
  <div>
    <div>여기에 평가하기 ,평가내역 탭 넣기</div>
    <div>우선 평가내역 페이지</div>
    <div v-for="(item, index) in evalLists" :key="index">
      <b-card
        v-bind:title="item.userId + '님은 어떠셨나요?'"
        sub-title="22/2/22 호수공원모임 "
        style="max-width: 20rem"
        class="mb-2"
      >
        <b-card-text>
          <star-rating v-bind:rating="item.rating" :read-only="true" :increment="1" />
        </b-card-text>

        <b-button href="#" variant="primary">평가확정</b-button>
      </b-card>
    </div>
  </div>
</template>

<script>
import http from "../../util/index";
export default {
  data() {
    return {
      evalLists: [],
      city: ["서울", "대전", "대구", "부산"],
    };
  },
  methods: {
    getevallist: async function () {
      let data = localStorage.getItem("vuex");
      let parsedata = JSON.parse(data);
      let token = parsedata.loginStore.jwtToken;

      http.defaults.withCredentials = false;
      http.defaults.headers.common["Authorization"] = "Bearer " + token;
      http.defaults.headers.common["withCredentials"] = false;
      let res = await http.get("/api/accounts/evalue");

      console.log("result", res);
      this.evalLists = res.data;
    },
    postRating: async function (rate, userId) {
      let data = localStorage.getItem("vuex");
      let parsedata = JSON.parse(data);
      let token = parsedata.loginStore.jwtToken;
      http.defaults.withCredentials = false;
      http.defaults.headers.common["Authorization"] = "Bearer " + token;
      http.defaults.headers.common["withCredentials"] = false;
      let param = { rating: 0, userId: 0 };
      param.rating = rate;
      param.userId = userId;

      let res = await http.post("/api/accounts/evalue", param);
      console.log(res);
    },
  },
  mounted: function () {
    this.getevallist();
    // this.postRating(6, 2);
  },
};
</script>

<style></style>
