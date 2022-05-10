import http from '../../util/index';
const userStore = {
  state: {
    userInfo: {
      userEmail : '',
      userNickname: '',
      userBirthday: '',
      userSex: '',
      userRatingScore: '',
      userProfileUrl: '',
    },
  },
  getters: {

  },
  mutations: {
    SET_USER_INFO: (state, payload) => {
      state.userInfo = payload;
    },
  },
  actions: {
    getUserInfo({ commit }) {
      try {
        http.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('vuex').loginStore.jwtToken;
        const res = http.get('api/accounts/mypage');
        // let token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaXNzIjoid29vbmFuYSIsImlhdCI6MTY1MjE2NDk4NywiZXhwIjoxNjUyMjUxMzg3fQ.VfPXbqMxqR2Y0rIiBfxSH3byym1lTV7QNrtSosZVteQsLDzXKxAtGY-WiY8ieibO7KkzZz6tmxZlAHaDi0IgMA";
        
        if (res.state === 200) {
          console.log('유저 정보를 가져왔습니다');
          console.log(res);
          commit('SET_USER_INFO', res.data) ;


        } else {
          console.log('유저정보 에러발생');
        }
      } catch (e) {
        console.log(e);
      }
      

      
    }
    

  }
  
};

export default userStore;