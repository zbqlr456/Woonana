<template>
  <div>
      <div class="single-post-area Jua">
        <!-- <blockquote v-for="user in users" :key="user.id">
            <p>{{user.userNickname}}</p>
            <p>{{user.userSex}}</p>
            <cite>{{user.userNickname}}님의 날씨 : </cite>
        </blockquote> -->
        <h4 class="article-title Jua">참여자 목록</h4>
        <div class="post-admin" v-for="user in users" :key="user.id">
            <img
            v-bind:src="user.userProfileUrl"
            >
            <a>이름 : {{user.userNickname}}</a>
            <span>성별 : {{user.userSex}}</span>
            <p>
               {{user.userNickname}}님의 날씨 : 
               <i v-if="user.userRatingScore <= 1" class="fas fa-bolt"></i>
               <i v-if="user.userRatingScore <= 2 && user.userRatingScore > 1" class="fas fa-cloud-rain"></i>
               <i v-if="user.userRatingScore <= 3 && user.userRatingScore > 2" class="fas fa-cloud"></i>
               <i v-if="user.userRatingScore <= 4 && user.userRatingScore > 3" class="fas fa-cloud-sun"></i>
               <i v-if="user.userRatingScore <= 5 && user.userRatingScore > 4" class="fas fa-sun"></i>
            </p>
            </div>
      </div>
      <button type="button" class="btn btn-light Jua" style="background-color: #FF0000" @click="gohome()">홈으로</button>
  </div>
</template>

<script>
import http from "@/util/index"
export default {
    data(){
        return{
            users:[],
            boardId: "",
            weather: ""
        }
    },
    mounted(){
        this.boardId = this.$route.params.data;
        http.get(`api/main/${this.boardId}/members`).then((response) =>{
            this.users = response.data;
            console.log(this.users);
        })
        .catch((err) => {
          alert(err);
      });
    },
    methods:{
      gohome(){
          this.$router.push("/allblogs");
      }
    },
}
</script>

<style>

</style>