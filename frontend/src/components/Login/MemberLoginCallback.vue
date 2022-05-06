<template>
  <div id="test">Login callbackpage입니다.</div>
</template>

<script>
import axios from "axios";
const URLSearch = new URLSearchParams(location.search);
let code;
export default {
  name: "MemberLoginCallback",

  methods: {
    callback: function () {
      code = URLSearch.get("code");
      //백엔드로 코드 전송???
      axios.post("http://localhost:8081/api/accounts/signup?" + "code=" + code).then((res) => {
        //http://localhost:9000/api/accounts/signup?code=00ADMLwPHz8OKUsTiiiZfa6Hf2oSNx8nPX0yuMBs0YOdK33FN52iSvitpsapqX-SH_5_JAo9dZsAAAGAbrxCtQ
        console.log(res);
        this.$store.commit("SET_JWT_TOKEN", res.data.token);
        console.log(this.$store.state.jwtToken);
      });
      //http://localhost:8081/api/accounts/signup?code=B7LKb7ZG5t9bfK2GFjAIQDfVJDXk1Ajjtb0AfxOxwBdsUep0KnupqLEqGQlG_f7POWL_cworDKYAAAGAadlYow
      // http://localhost:9000/app/users/kakao
    },
  },
  beforeMount() {},
  mounted() {
    this.callback();
    // alert(code);
  },
};
</script>

<style></style>
