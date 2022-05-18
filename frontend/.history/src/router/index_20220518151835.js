import Vue from "vue";
import VueRouter from "vue-router";
import MainPage from "../views/MainPage";
import LoginCallback from "../components/Login/MemberLoginCallback.vue";
import ChatRoomList from "../components/Chat/ChatRoomList.vue";
import ChatRoom from "../views/ChatRoom.vue";
import ChatList from "../components/Chat/ChatList.vue";
import ChatMessage from "../components/Chat/ChatMessage.vue";
import Mypage from "../views/Mypage.vue";
import MypageMain from "../components/Mypage/MypageMain.vue";
import ShowBlog from "../components/Blogs/ShowBlog.vue";
import MypageQna from "../components/Mypage/MypageQna.vue";
import MyEvalLog from "../components/Mypage/MyEvalLog.vue";
import MyJoinLog from "../components/Mypage/MyJoinLog.vue";
import MyPosts from "../components/Mypage/MyPosts.vue";
import MyWorkoutLog from "../components/Mypage/MyWorkoutLog.vue";
import NewPost from "../components/Blogs/NewPost.vue";
import AllBlogs from "../components/Blogs/index.vue";
import MemberList from "../components/Blogs/MemberList.vue";
import WaitMemberList from "../components/Blogs/WaitMemberList.vue";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "MainPage",
    component: MainPage,
  },
  {
    path: "/oauth/callback",
    name: "LoginCallback",
    component: LoginCallback,
  },
  {
    path: "/chat/chatroom",
    name: "ChatRoom",
    component: ChatRoom,
  },
  {
    path: "/chat/chatlist",
    name: "ChatList",
    component: ChatList,
  },
  {
    path: "/newpost",
    name: "NewPost",
    component: NewPost,
  },
  {
    path: "/chat/chatroomlist",
    name: "ChatRoomList",
    component: ChatRoomList,
  },
  {
    path: "/chat/chatmessage",
    name: "ChatMessage",
    component: ChatMessage,
  },
  {
    path: "/mypage",
    component: Mypage,
    children: [
      {
        path: "",
        component: MypageMain,
      },
      {
        path: "main",
        component: MypageMain,
      },
      {
        // /mypage/qna 과 일치 할 때
        // mypageqna가 mypage <router-view> 내에 렌더링 됩니다.
        path: "qna",
        component: MypageQna,
      },
      {
        path: "myevallog",
        component: MyEvalLog,
      },
      {
        path: "myjoinlog",
        component: MyJoinLog,
      },
      {
        path: "myposts",
        component: MyPosts,
      },
      {
        path: "myworkoutlog",
        component: MyWorkoutLog,
      },
    ],
  },
  {
    path: "/showblog",
    name: "ShowBlog",
    component: ShowBlog,
  },
  {
    path: "/allblogs",
    name: "AllBlogs",
    component: AllBlogs,
  },
  {
    path: "/memberlist",
    name: "MemberList",
    component: MemberList,
  },
  {
    path: "/waitmemberlist",
    name: "WaitMemberList",
    component: WaitMemberList,
  },
  // {
  //   path: '/about',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  // stop stop cache
  scrollBehavior() {
    document.getElementById("app").scrollIntoView();
  },
});

export default router;
