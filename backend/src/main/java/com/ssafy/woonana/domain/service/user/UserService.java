package com.ssafy.woonana.domain.service.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.woonana.domain.model.dto.board.response.BoardListResponse;
import com.ssafy.woonana.domain.model.dto.user.response.LikeExcerciseResponse;
import com.ssafy.woonana.domain.model.dto.user.response.MyPageInfoResponse;
import com.ssafy.woonana.domain.model.dto.user.response.UserEvaluateResponse;
import com.ssafy.woonana.domain.model.dto.user.response.UserParticipateResponse;
import com.ssafy.woonana.domain.model.entity.board.Board;
import com.ssafy.woonana.domain.model.entity.evaluation.Evaluation;
import com.ssafy.woonana.domain.model.entity.exercise.Exercise;
import com.ssafy.woonana.domain.model.entity.participation.Participation;
import com.ssafy.woonana.domain.model.entity.user.User;
import com.ssafy.woonana.domain.repository.board.BoardRepository;
import com.ssafy.woonana.domain.repository.board.ExerciseRepository;
import com.ssafy.woonana.domain.repository.evaluation.EvaluationRepository;
import com.ssafy.woonana.domain.repository.participation.ParticipationRepository;
import com.ssafy.woonana.domain.repository.user.UserRepository;
import com.ssafy.woonana.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Value("${kakao.login.redirect_url}")
    private String redirectUrl;

    @Value("${kakao.login.client_id}")
    private String clientId;

    // 카카오 회원 정보를 받아와서 기존에 회원가입 한 회원이면 로그인, 회원가입 안 한 회원이면 회원가입 시켜주는 메소드
    public String userAccess(String code) throws Exception {

        String accessToken = getAccessToken(code);
        log.info("userAccess에서 access token check: ", accessToken);
        String apiUrl = "https://kapi.kakao.com/v2/user/me";
        String headerStr = "Bearer "+accessToken;
        String res = requestToServer(apiUrl, headerStr, "GET");
        log.info("res: ", res);

        if(res==null){ // 서버로 사용자 정보를 요청했는데 null이 온 경우
            log.warn("res: ", res);
            throw new Exception("카카오 로그인 에러가 발생했습니다.");
        }

        JSONObject jObj = new JSONObject(res);
        log.info("회원 정보(res) : "+res); // 디버깅

        Long kakaoId = jObj.getLong("id");
        log.info("카카오 아이디: "+kakaoId); // 디버깅

        String kakaoNickname = jObj.getJSONObject("kakao_account").getJSONObject("profile").getString("nickname");
        log.info("카카오 닉네임: "+kakaoNickname); // 디버깅

        String kakaoProfileImg = jObj.getJSONObject("kakao_account").getJSONObject("profile").getString("profile_image_url");
        log.info("카카오 프로필: "+kakaoProfileImg); // 디버깅

        String kakaoEmail = jObj.getJSONObject("kakao_account").getString("email");
        log.info("카카오 이메일: "+kakaoEmail); // 디버깅

        // 성별 선택 동의: 성별 제공에 동의했다면 저장, 동의 안 한 경우에는 null
        String kakaoGender = null;
        if(!jObj.getJSONObject("kakao_account").getBoolean("gender_needs_agreement")) {
            kakaoGender = jObj.getJSONObject("kakao_account").getString("gender");
        }
        log.info("카카오 성별: "+kakaoGender); // 디버깅

        // 생일 선택 동의: 생일 제공에 동의했다면 저장, 동의 안 한 경우에는 null
        String kakaoBirthday = null;
        if(!jObj.getJSONObject("kakao_account").getBoolean("birthday_needs_agreement")) {
            kakaoBirthday = jObj.getJSONObject("kakao_account").getString("birthday");
        }
        log.info("카카오 생일: "+kakaoBirthday); // 디버깅


        // 사용자가 기존에 가입했는지 확인
        Optional<User> user = userRepository.findByKakaoId(kakaoId);
        User newUser = null;

        if (user.isEmpty()) { // 가입하지 않은 경우, 가입시켜준다.

            newUser = User.builder()
                    .kakaoId(kakaoId)
                    .userNickname(kakaoNickname)
                    .userProfileUrl(kakaoProfileImg)
                    .userEmail(kakaoEmail)
                    .userSex(kakaoGender)
                    .userBirthday(kakaoBirthday)
                    .accessToken(accessToken) // access token 저장
                    .build();

            userRepository.save(newUser); // 회원 정보 저장
            user = userRepository.findByKakaoId(kakaoId); // user 객체 가져오기
        }
        else { // 가입한 경우, 회원 정보 중 바뀐 정보가 있으면 갱신해준다.

            // access 토큰 갱신
            user.get().setAccessToken(accessToken);

            // 사용자 닉네임이 바뀐 경우
            if(!kakaoNickname.equals(user.get().getUserNickname())) {
                user.get().setUserNickname(kakaoNickname);
            }

            // 사용자 프로필 사진이 바뀐 경우
            if(!kakaoProfileImg.equals(user.get().getUserProfileUrl())){
                user.get().setUserProfileUrl(kakaoProfileImg);
            }

            // 사용자 이메일이 바뀐 경우
            if(!kakaoEmail.equals(user.get().getUserEmail())){
                user.get().setUserEmail(kakaoEmail);
            }

            // 사용자 성별 정보 바뀐 경우
            if(kakaoGender!=null && !kakaoGender.equals(user.get().getUserSex())){
                user.get().setUserSex(kakaoGender);
            }

            // 사용자 생일 정보 바뀐 경우
            if(kakaoBirthday!=null && !kakaoBirthday.equals(user.get().getUserBirthday())){
                user.get().setUserBirthday(kakaoBirthday);
            }
        }

        // 토큰 생성
        final String token = tokenProvider.create(user.get());
        return token; // 토큰 리턴해주기

    }

    // code를 받아서 access token을 요청해서 받아오는 메소드
    // 이 때 받은 access token은 회원에 대한 정보를 가져올 때 쓰인다
    public String getAccessToken(String code) {

        String accessToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //헤더셋팅
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            //HttpURLConnection 설정 값 셋팅
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // buffer 스트림 객체 값 셋팅 후 요청
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + clientId);  //앱 KEY VALUE
            sb.append("&code=" + code);
            sb.append("&redirect_uri=" + redirectUrl); // 앱 CALLBACK 경로

            bw.write(sb.toString());
            bw.flush();
            log.debug("sb 정보; ", sb.toString());

            //  RETURN 값 result 변수에 저장
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String br_line = "";
            String result = "";

            while ((br_line = br.readLine()) != null) {
                result += br_line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            log.info("kauth 요청 결과: "+result);

            // 토큰 값 저장 및 리턴
            accessToken = element.getAsJsonObject().get("access_token").getAsString();

            br.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
            log.warn("getAccessToken에서 IOException 발생->accessToken이 빈 값이 됨");
        }

        return accessToken;
    }

    // 인자로 들어오는 apiUrl을 통해 카카오와 통신하는 메소드
    private String requestToServer(String apiURL, String headerStr, String requestMethod) throws IOException {
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
//        con.setRequestMethod("GET");
        con.setRequestMethod(requestMethod);

        if(headerStr != null && !headerStr.equals("") ) {
            con.setRequestProperty("Authorization", headerStr);
        }

        int responseCode = con.getResponseCode();
        BufferedReader br;

        if(responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        String inputLine;
        StringBuilder res = new StringBuilder();

        while ((inputLine = br.readLine()) != null) {
            res.append(inputLine);
        }

        br.close();

        if(responseCode==200) {
            return res.toString();
        } else {
            System.out.println(res.toString());
            return null;
        }
    }

    // 회원 탈퇴시키는 메소드
    public boolean userDelete(Long loginId){

        User loginUser = userRepository.findById(loginId).get();
        try {
            deleteKakaoInfo(loginUser.getAccessToken());
        }
        catch(IOException e){
            e.printStackTrace();
            return false; // 에러 생기면 회원 정보를 삭제하지 않고 false 리턴한다.
        }

        userRepository.delete(loginUser); // 회원 정보 삭제

        if(userRepository.findById(loginId).isEmpty()) { // 회원 정보가 없다면 탈퇴 성공
            return true;
        }

        return false; // 회원 정보가 있다면 탈퇴 실패함
    }

    // 카카오에서 사용자와 앱의 연결 해제
    public void deleteKakaoInfo(String accessToken) throws IOException {
        String apiUrl = "https://kapi.kakao.com/v1/user/unlink";
        String headerStr = "Bearer "+accessToken;
        requestToServer(apiUrl, headerStr, "POST");
    }

    // 인자로 들어온 회원의 회원 정보 리턴하기
    public MyPageInfoResponse selectUserInfo(Long userId){
        User user = userRepository.findById(userId).get();
        MyPageInfoResponse mypageInfo = new MyPageInfoResponse(user);
        return mypageInfo;
    }

    // 인자로 들어온 사용자의 운동 선호도 조회
    public LikeExcerciseResponse getLikeExcercise(Long userId){

        User user = userRepository.findById(userId).get();
        

        return null;
    }

    // 평가하기
    public void evaluate(Long loginId, Long userId, int rating){

        User loginUser=userRepository.findById(loginId).get(); // 평가한 사람
        User user = userRepository.findById(userId).get(); // 평가받은 사람

        // 평가 테이블에 추가하기
        Evaluation eval = Evaluation.builder()
                .evaluationRatingScore(rating)
                .evaluationUser(loginUser)
                .evaluationTarget(user)
                .build();
        evaluationRepository.save(eval);

        // 평가받은 유저의 rating score 업데이트
        user.setUserRatingScore(user.getUserRatingScore()+rating); // 평가 컨셉이 안 잡혀서 일단은 점수를 기존 유저 점수에 더해주도록 함
    }

    // 특정 유저가 평가한 모든 사용자를 보여준다
    public List<UserEvaluateResponse> getEvaluationList(Long userId){

        User user = userRepository.findById(userId).get(); // 특정 유저
        List<Evaluation> evaluations = evaluationRepository.findEvaluationsByEvaluationUser(userId);
        List<UserEvaluateResponse> result = new ArrayList<>();

        // 사용자 리스트를 dto 형태로 바꿔준다
        for(Evaluation eval: evaluations){
            result.add(new UserEvaluateResponse(eval));
        }

        return result;
    }

    // userId에 해당하는 유저가 참여한 게시글 정보 리턴
    public List<UserParticipateResponse> getParticipationList(Long userId){

        User user=userRepository.findById(userId).get();

        // 참여(participation) 조회
        List<Participation> participations = participationRepository.findParticipationsByUser(user);
        List<UserParticipateResponse> result = new ArrayList<>();

        for(Participation par: participations){
            Board participatedBoard = boardRepository.findById(par.getBoard().getId()).get();
            result.add(new UserParticipateResponse(participatedBoard));
        }

        return result;
    }



}