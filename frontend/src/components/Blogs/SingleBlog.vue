<template>
        <div>
            <div class="single-post-area Jua">
                <div class="post-thumb">
                    <img :src="board.imageUrl" alt="">
                </div>
                <h4 class="article-title">{{board.title}}</h4>
                <blockquote>
                    <p>
                        {{board.content}}
                    </p>
                    <cite>{{board.userNickname}}</cite>
                </blockquote>
                <div class="post-admin">
                    <!-- <img src="@/assets/images/single-post/author.png" alt=""> -->
                    <a>모집 최대 인원 : {{board.maxNumber}}</a>
                    <span>현재 인원 : {{board.allowedNumber}}</span>
                    <p>
                        만남 장소 : {{board.place}}
                    </p>
                    <p>
                        만남 시작 시간 : {{board.meetStartDate}}
                    </p>
                    <p>
                        만남 종료 시간 : {{board.meetEndDate}}
                    </p>
                </div>
                <div class="post-tags">
                    <h5 class="Jua">운동종목 => </h5>
                    <a v-if="board.exerciseId==1">탁구</a>
                    <a v-if="board.exerciseId==2">배드민턴</a>
                    <a v-if="board.exerciseId==3">캐치볼</a>
                    <a v-if="board.exerciseId==4">산책</a>
                    <h5 class="Jua">모집상태 => </h5>
                    <a>{{board.status}} </a>
                </div>
                <hr>
                    <div class="post-tags">
                        <h5 class="Jua">참여하기 => </h5>
                        <a v-if="(this.board.userNickname != myinfomation.userNickname) || (this.board.allowedNumber < this.board.maxNumber)" @click="join()">신청하기</a>
                        <a @click="viewMember()">멤버조회</a>
                    </div>
                    <br>
                    <div class="post-tags">
                        <h5 class="Jua">멤버 목록 => </h5>
                        <a v-if="this.board.userNickname == myinfomation.userNickname" @click="viewParticipatedMember()">신청자 조회</a>
                        <a @click="viewMember()">멤버조회</a>
                    </div>
                    
                    <br>
                    <div v-if="this.board.userNickname == myinfomation.userNickname" class="post-tags">
                        <br>
                        <h5 class="Jua">글 삭제하기 => </h5>
                        <a @click="blogdelete()">삭제하기</a>
                    </div>
            </div>
        </div>
</template>

<script>
import http from "@/util/index"
export default {
    data(){
        return{
            board:{},
            boardId: ""
        }
    },
    watch(){
        this.checkStatus();
    },
    mounted(){
        this.$store.dispatch("getUserInfo");
        this.boardId = this.$route.params.data;
        console.log("여긴 게시글번호",this.boardId);
        http.get(`/api/main/${this.boardId}`).then((response) => {
            this.board = response.data;
            console.log("여긴 상세내용 : ", this.board);
        })
    },
    computed: {
    myinfomation: function () {
      return this.$store.getters.GET_USER_INFO;
        },
    },
    methods:{
        join(){
        console.log(this.boardId);
        http.post(`/api/participate/${this.boardId}`).then((response) => {
            console.log(response);
            let msg = "신청이 완료되었습니다."
            alert(msg);
            this.board.allowedNumber++;
        }).catch(err=>{
            let msg = "이미 신청하셨거나, 신청이 불가한 상태입니다. 다시 확인해 주세요";
            console.log(err);
            alert(msg);
        })
        },
        blogdelete(){
            http.delete(`/api/main/${this.boardId}`).then((response) => {
                console.log(response);
                let msg = "삭제가 완료되었습니다."
                alert(msg);
                this.$router.push("/allblogs");
            })
        },
        checkStatus(){
            if(this.board.allowedNumber == this.board.maxNumber){
                this.board.status = "closed";
            }
        },
        viewMember: function(){
            this.$router.push({
                name: "MemberList",
                params: {data: this.boardId},
            });
        },
        viewParticipatedMember: function(){
            this.$router.push({
                name: "WaitMemberList",
                params: {data: this.boardId},
            });
        }

        }
    }
</script>

<style>

</style>