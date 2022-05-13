<template>
  <div>
    <div>여기에 평가하기 ,평가내역 탭 넣기</div>
    <div>우선 평가내역 페이지</div>
    <div>
      <b-card title="이싸피님은 어땟나요?" sub-title="22/2/22 호수공원모임">
        <b-card-text>
          <star-rating />
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
      evalList: [],
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
      this.evalList = res.data;
    },
  },
  mounted: function () {
    this.getevallist();
  },
};
</script>

<style></style>
