package com.ssafy.woonana.domain.service.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ssafy.woonana.domain.model.entity.user.User;
import com.ssafy.woonana.domain.repository.user.UserRepository;
import com.ssafy.woonana.security.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Value("${kakao.login.redirect_url}")
    private String redirectUrl;

    @Value("${kakao.login.client_id}")
    private String clientId;

    // 카카오 회원 정보를 받아와서 기존에 회원가입 한 회원이면 로그인, 회원가입 안 한 회원이면 회원가입 시켜주는 메소드
    public String userAccess(String code) throws Exception {

        String accessToken = getAccessToken(code);
        String apiUrl = "https://kapi.kakao.com/v2/user/me";
        String headerStr = "Bearer "+accessToken;
        String res = requestToServer(apiUrl, headerStr);

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
        if(jObj.getJSONObject("kakao_account").getBoolean("has_email")) {
            kakaoGender = jObj.getJSONObject("kakao_account").getString("gender");
        }
        log.info("카카오 성별: "+kakaoGender); // 디버깅

        // 생일 선택 동의: 생일 제공에 동의했다면 저장, 동의 안 한 경우에는 null
        String kakaoBirthday = null;
        if(jObj.getJSONObject("kakao_account").getBoolean("has_birthday")) {
            kakaoBirthday = jObj.getJSONObject("kakao_account").getString("birthday");
        }
        log.info("카카오 생일: "+kakaoBirthday); // 디버깅


        // 사용자가 기존에 가입했는지 확인
        Optional<User> user = userRepository.findById(kakaoId);
        User newUser = null;

        if (user.isEmpty()) { // 가입하지 않은 경우, 가입시켜준다.

            newUser = User.builder()
                    .userId(kakaoId)
                    .userNickname(kakaoNickname)
                    .userProfileUrl(kakaoProfileImg)
                    .userEmail(kakaoEmail)
                    .userSex(kakaoGender)
                    .userBirthday(kakaoBirthday)
                    .build();

            userRepository.save(newUser); // 회원 정보 저장
            user = userRepository.findById(kakaoId); // user 객체 가져오기
        }
        else { // 가입한 경우, 회원 정보 중 바뀐 정보가 있으면 갱신해준다.

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
            System.out.println(sb.toString()); // 디버깅

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
        }

        return accessToken;
    }

    // 서버 통신 메소드
    private String requestToServer(String apiURL, String headerStr) throws IOException {
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");

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

}
