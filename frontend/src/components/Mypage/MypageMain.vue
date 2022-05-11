<template>
  <div>
    <h1>mypage main</h1>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      myinfo: {
        userEmail: "tjrdnjs1609@hanmail.net",
        userNickname: "김석원",
        userBirthday: "0815",
        userSex: null,
        userRatingScore: 0,
        userProfileUrl:
          "http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg",
      },
    };
  },
  methods: {
    getUserInfo: function () {
      this.$store.dispatch("getUserInfo");
    },
    getUserInfoaxios: function () {
      let token =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaXNzIjoid29vbmFuYSIsImlhdCI6MTY1MjE1OTAzNiwiZXhwIjoxNjUyMjQ1NDM2fQ.9G3juVIU-sUf7LsWixScRSgpJHCtTHQd2wAJbnOcjv5caxexWKyz1Clbuk-8bqjSEqzQuXJreqE6__EoAA1qZg";
      console.log(token);
      axios.defaults.headers.common["Authorization"] =
        "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaXNzIjoid29vbmFuYSIsImlhdCI6MTY1MjIzNTk1NywiZXhwIjoxNjUyMzIyMzU3fQ.QL8RJhVvg-ROl8igeFLAJT3NP14kvNRkE_C3Pb5RWXELDUReoXDuG1zgpN9qUiObCbGa_brigtBsCYyyyqQldg";
      // axios.defaults.headers.common["host"] = "http://localhost:8080";
      // axios.defaults.headers.common["Access-Control-Allow-Origin"] = "*";
      // axios.defaults.headers.common["Access-Control-Allow-Credentials"] = true;
      // axios.defaults.headers.common["Access-Control-Allow-Methods"] = "GET,HEAD,OPTIONS,POST,PUT";
      axios.defaults.withCredentials = true;
      // axios.defaults.headers.common["Authorization"] = "Bearer " + token;
      axios.get("http://localhost:8081/api/accounts/mypage").then((response) => {
        console.log(response);
      });
    },
  },
  beforemount: function () {},
  mounted: function () {
    this.getUserInfoaxios();
    // this.getUserInfoaxios();
  },
};
</script>

<style></style>
