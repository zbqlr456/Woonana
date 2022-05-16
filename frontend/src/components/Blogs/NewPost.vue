<template>
  <div class="col-md-8">
        <div class="contact-form">
            <h4>게시글 등록</h4>
            <p>함께 운동할 사람을 찾아보세요.</p>
            <form action="#" method="post" class="row">
                <div class="col-md-6">
                    <input type="text" name="f-name" placeholder="제목을 입력하세요" v-model="board.title">
                </div>
                <div class="col-md-12">
                    <textarea name="message" placeholder="내용을 입력하세요" v-model="board.content"></textarea>
                </div>
                <div class="col-md-6">
                  <label>운동 시작 날짜</label>
                  <input type="date" name="시작 날짜" v-model="board.meetStartDate">
                </div>
                <div class="col-md-6">
                    <label>운동 종료 날짜</label>
                    <input type="date" name="종료 날짜" v-model="board.meetEndDate">
                </div>
                <div class="col-md-6">
                    <label>최대 인원 설정</label>
                    <input type="number" name="maxNumber" min="1" max="6" v-model="board.maxNumber">
                </div>
                <div class="col-md-12">
                    <input type="radio" id="산책" name="산책" value="4" v-model="board.exerciseId">
                    <label for="산책">산책</label>
                    <input type="radio" id="캐치볼" name="캐치볼" value="3" v-model="board.exerciseId">
                    <label for="캐치볼">캐치볼</label>
                    <input type="radio" id="탁구" name="탁구" value="1" v-model="board.exerciseId">
                    <label for="탁구">탁구</label>
                    <input type="radio" id="배드민턴" name="배드민턴" value="2" v-model="board.exerciseId">
                    <label for="배드민턴">배드민턴</label>
                </div>
                <div>
    <div
      ref="searchWindow"
      :style="searchWindow"
      style="border:1px solid;width:500px;margin:5px 0;position:relative"
    >
      <img
        src="//t1.daumcdn.net/postcode/resource/images/close.png"
        id="btnFoldWrap"
        style="cursor:pointer;position:absolute;right:0px;top:-1px;z-index:1"
        @click="searchWindow.display = 'none'"
        alt="close"
      >
    </div>
    <input type="text" placeholder="우편번호" v-model="postcode">
    <input type="button" value="우편번호 찾기" @click="execDaumPostcode">
    <br>
    <input type="text" v-model="address" placeholder="주소">
    <br>
    <input type="text" v-model="extraAddress" ref="extraAddress" placeholder="상세주소">
  </div>
                <div class="col-md-12">
                    <input @change="upload" type="file" id="file" class="img"/>
                    <label for="file">사진 업로드</label>
                </div>
                <div class="col-md-6 text-right">
                    <input type="submit" name="submit" value="등록하기">
                </div>
            </form>
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return{
            board:{
                title:"",
                content: "",
                allowedNumber: 0,
                imageUrl:"",
                maxNumber: 0,
                meetEndDate: "",
                meetStartDate: "",
                place:"",
                postcode:"",
                address:"",
                extraAddress: "",
                status : "",
                userEmail : "",
                userNickName: "",
                createdDate : "",
                exerciseId: 0,
            }
        }
    },
     methods: {
    execDaumPostcode() {
      new window.daum.Postcode({
        oncomplete: (data) => {
          if (this.extraAddress !== "") {
            this.extraAddress = "";
          }
          if (data.userSelectedType === "R") {
            // 사용자가 도로명 주소를 선택했을 경우
            this.address = data.roadAddress;
          } else {
            // 사용자가 지번 주소를 선택했을 경우(J)
            this.address = data.jibunAddress;
          }
 
          // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
          if (data.userSelectedType === "R") {
            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
              this.extraAddress += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== "" && data.apartment === "Y") {
              this.extraAddress +=
                this.extraAddress !== ""
                  ? `, ${data.buildingName}`
                  : data.buildingName;
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (this.extraAddress !== "") {
              this.extraAddress = `(${this.extraAddress})`;
            }
          } else {
            this.extraAddress = "";
          }
          // 우편번호를 입력한다.
          this.postcode = data.zonecode;
        },
      }).open();
    },
  },
}
</script>

<style>
.img{
    display: none;
}
</style>