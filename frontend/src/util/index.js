import axios from "axios";

// axios 객체 생성
export default axios.create({
  withCredentials: false,
<<<<<<< HEAD
  // baseURL: "http://localhost:8081",
=======
>>>>>>> e47531e7abd82cf5203ef106190ce533375e49bd
  baseURL: process.env.VUE_APP_BACK_URI,
  headers: {
    "Content-type": "application/json",
  },
});
