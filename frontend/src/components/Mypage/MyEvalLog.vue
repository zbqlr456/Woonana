<template>
  <div>
    <b-tabs content-class="mt-3" fill>
      <b-tab title="평가내역" active>
        <div v-for="(item, index) in evalLists" :key="index">
          <b-card
            v-bind:title="item.userId + '님에게 준 평점'"
            sub-title="22/2/22 호수공원모임 "
            style="max-width: 25rem"
            class="mb-2"
          >
            <b-card-text>
              <star-rating v-bind:rating="item.rating" :read-only="true" :increment="1" />
            </b-card-text>

            <!-- <b-button href="#" variant="primary">평가확정</b-button> -->
          </b-card>
        </div></b-tab
      >
      <b-tab title="평가하기">
        <div v-for="(item, index) in evalLists" :key="index">
          <b-card
            v-bind:title="item.userId + '님은 어떠셨나요?'"
            sub-title="22/2/22 호수공원모임 "
            style="max-width: 25rem"
            class="mb-3"
          >
            <b-card-text>
              <star-rating v-bind:rating="3" @rating-selected="setRating" :increment="1" />
            </b-card-text>
          </b-card></div
      ></b-tab>
    </b-tabs>
  </div>
</template>

<script>
import http from "../../util/index";
export default {
  data() {
    return {
      evalLists: [],
      evalpostLIst: [],
      rating: 0,
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
    setRating: function (rating) {
      this.rating = rating;
      this.postRating(rating, 6);
    },
  },
  mounted: function () {
    this.getevallist();
    // this.postRating(6, 2);
  },
};
</script>

<style></style>
