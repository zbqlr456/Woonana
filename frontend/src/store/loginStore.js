import axios from 'axios';

const loginStore = {
	namespaced: false,
  state: {
    jwtToken: '',
    memberid: '',
	},
  getters: {
    isLogin(state) {
      return state.jwtToken == '' ? false : true;
  }
	},
  mutations: {
    SET_JWT_TOKEN: (state, payload) => {
      state.jwtToken = payload
  },
  SET_MEMBER_ID: (state, memberid) => {
      state.memberid = memberid
  },
  reset(state) {
      state.memberid = "";
      state.jwtToken = "";
  }
	},
  actions: {
  //   async doLogin({ commit }, code) {
  //     let result = false;
  //     let resultErr = null;
  //     try {
  //         let res = await axios.post("http://localhost:8081/api/accounts/signup?" + "code=" + code);
  //         if (res.data.state == 200) {
  //             console.log("로그인되었습니다.");
  //             commit('SET_JWT_TOKEN', res.data.token);
  //             result = true;
  //         } else {
  //             console.log("로그인되지 않았습니다.");
  //             let err = new Error("Request failed with status code 401");
  //             err.response = {data:{"success":false, "errormessage":"로그인되지 않았습니다."}};
  //             resultErr = err;
  //         }
  //     } catch(err) {
  //         console.log(err);
  //         if (!err.response) {
  //             err.response = {data:{"success":false, "errormessage":err.message}};
  //         }
  //         resultErr = err;
  //     }
  //     return new Promise((resolve, reject) => {
  //         if (result) {
  //             resolve();
  //         } else {
  //             reject(resultErr);
  //         }
  //     });
  // },
  // 로그아웃합니다.\
    
    doLogin({ commit }, code) { 
      axios
        .post("http://localhost:8081/api/accounts/signup?code=" + code)
        .then((res) => {
          //http://localhost:9000/api/accounts/signup?code=00ADMLwPHz8OKUsTiiiZfa6Hf2oSNx8nPX0yuMBs0YOdK33FN52iSvitpsapqX-SH_5_JAo9dZsAAAGAbrxCtQ
          console.log(res);
          commit("SET_JWT_TOKEN", res.data.token);
          console.log(this.$store.state.jwtToken);
          alert(this.$store.state.jwtToken);
          // window.location.href = "http://localhost:8080";
        })
        .catch((err) => {
          console.log(err);
        });
    },
    
  doLogout({commit}) {
      commit('reset');
  }
	}
};

export default loginStore;