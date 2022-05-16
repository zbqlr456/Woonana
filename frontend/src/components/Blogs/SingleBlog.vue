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
                    <h5>운동 종목 : </h5>
                    <a v-if="board.exerciseId==1">탁구</a>
                    <a v-if="board.exerciseId==2">배드민턴</a>
                    <a v-if="board.exerciseId==3">캐치볼</a>
                    <a v-if="board.exerciseId==4">산책</a>
                </div>
                <div class="post-share">
                    <h5>모집상태 : {{board.status}} </h5>
                </div>
            </div>
            <!-- <div class="comment-area">
                <h3>Comment (3)</h3>
                <ol class="comment-list">
                    <li>
                        <div class="single-comment">
                            <img src="@/assets/images/single-post/c1.png" alt="">
                            <h5><a href="#">Jason Response</a></h5>
                            <span>April 03, 2019</span>
                            <div class="comment">
                                <p>
                                    He legged it bevvy mush owt to do with me such fibber fa about cup of tea sloshed morish butty bubble. Butty gormless lavatory twit bits and bobs pardon you daft ummm I'm telling.
                                </p>
                            </div>
                            <a class="comment-reply-link" href="#"><i class="fal fa-reply"></i>Reply</a>
                            <div class="c-border"></div>
                        </div>
                        <ul class="children">
                            <li>
                                <div class="single-comment">
                                    <img src="@/assets/images/single-post/c2.png" alt="">
                                    <h5><a href="#">Sue Shei</a></h5>
                                    <span>April 14, 2019</span>
                                    <div class="comment">
                                        <p>
                                            Super chancer cheeky bloke haggle give us a bell well mufty chinwag say bite.
                                            lavatory twit bits and bobs pardon you daft ummm I'm telling. Blatant matie boy say bugger all mate butty gormless
                                        </p>
                                    </div>
                                    <a class="comment-reply-link" href="#"><i class="fal fa-reply"></i>Reply</a>
                                    <div class="c-border"></div>
                                </div>
                            </li>
                            <li>
                                <div class="single-comment">
                                    <img src="@/assets/images/single-post/c3.png" alt="">
                                    <h5><a href="#">Douglas Lyphe</a></h5>
                                    <span>July 27, 2020</span>
                                    <div class="comment">
                                        <p>
                                            Oxford baking cakes dropped clanger fanny around vagabond amongst cor blimey guvnor.
                                            Blatant matie boy say bugger all mate butty gormless
                                            lavatory twit bits and bobs pardon you daft ummm I'm telling. 
                                        </p>
                                    </div>
                                    <a class="comment-reply-link" href="#"><i class="fal fa-reply"></i>Reply</a>
                                    <div class="c-border"></div>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ol>
            </div>
            <div class="comment-form-area">
                <h3>Post a Comment</h3>
                <p>Your email address will not be published. Required fields are marked</p>
                <div class="comment-form">
                    <form class="row" action="#" method="post">
                        <div class="col-md-6">
                            <input type="text" name="name" placeholder="Name">
                        </div>
                        <div class="col-md-6">
                            <input type="email" name="email" placeholder="Email">
                        </div>
                        <div class="col-md-12">
                            <input type="url" name="url" placeholder="Website">
                        </div>
                        <div class="col-md-12">
                            <div class="save-comment">
                                <input id="history-name" type="checkbox">
                                <label for="history-name">
                                    Save my name, email, and website in this browser for the next time I comment.
                                </label>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <textarea placeholder="Coment"></textarea>
                        </div>
                        <div class="col-md-12">
                            <button type="submit">Post Comment</button>
                        </div>
                    </form>
                </div>
            </div> -->
        </div>
</template>

<script>
import http from "@/util/index"
export default {
    data(){
        return{
            board:{},
        }
    },
    created(){
        let data = this.$route.params.data;
        http.get(`/api/main/${data}`).then((response) => {
            this.board = response.data;
            console.log(this.board);
        })
    }
}
</script>

<style>

</style>