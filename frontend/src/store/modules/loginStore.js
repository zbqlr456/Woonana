import http from '../../util/index';

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
    async doLogin({ commit }, code) {
      let result = false;
      let resultErr = null;
      try {
          let res = await http.post( '/api/accounts/signup?' +"code=" + code);
          if (res.status === 200) {
              console.log("로그인되었습니다.");
              
            commit('SET_JWT_TOKEN', res.data.token);
              result = true;
          } else {
              console.log("로그인되지 않았습니다.");
              let err = new Error("Request failed with status code 401");
              err.response = {data:{"success":false, "errormessage":"로그인되지 않았습니다."}};
              resultErr = err;
          }
      } catch(err) {
          console.log(err);
          if (!err.response) {
              err.response = {data:{"success":false, "errormessage":err.message}};
          }
          resultErr = err;
      }
      return new Promise((resolve, reject) => {
          if (result) {
              resolve();
          } else {
              reject(resultErr);
          }
      });
  },
  // 로그아웃합니다.
  doLogout({commit}) {
      commit('reset');
  },
	}
};

export default loginStore;