import http from '../../util/index';
// import axios from "axios";
const exerciseStore = {
  state: {
    allExercise : [],
  },
  getters: {
  },
  mutations: {
    SET_ALL_EXERCISE_INFO: (state, payload) => {
      state.allExercise = payload;
    },
  },
  actions: {
    async getAllExerciseInfo({ commit }) {
      try {
        let data = localStorage.getItem('vuex');
        let parsedata = JSON.parse(data);
        let token = parsedata.loginStore.jwtToken;
        
        http.defaults.withCredentials = false;
        http.defaults.headers.common['Authorization'] = 'Bearer ' + token;
        http.defaults.headers.common['withCredentials'] = false;
        console.log(http.defaults.headers.common['Authorization']);
        const res = await http.get('api/accounts/myexercise/record/7');
        // let token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaXNzIjoid29vbmFuYSIsImlhdCI6MTY1MjE2NDk4NywiZXhwIjoxNjUyMjUxMzg3fQ.VfPXbqMxqR2Y0rIiBfxSH3byym1lTV7QNrtSosZVteQsLDzXKxAtGY-WiY8ieibO7KkzZz6tmxZlAHaDi0IgMA";
        
        if (res.status === 200) {
          console.log('운동 정보를 가져왔습니다');
          console.log(res);
          commit('SET_ALL_EXERCISE_INFO', res.data) ;
        } else {
          console.log('운동정보 에러발생');
        }
      } catch (e) {
        console.log(e);
      }
      

      
    }
    

  }
  
};

export default exerciseStore;