package com.ssafy.woonana.controller.user;

import com.ssafy.woonana.domain.model.dto.user.response.MyPageInfoResponse;
import com.ssafy.woonana.domain.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@Controller
@RequestMapping("api/accounts")
public class UserController {

    @Autowired
    UserService userService;

    // 유저가 회원이면 로그인, 유저가 회원이 아니라면 회원가입을 시키고 나서 로그인한다.
    @PostMapping("/signup")
    public ResponseEntity<HashMap<String, String>> userLogin(@RequestParam String code) throws Exception {

        HashMap<String, String> result = new HashMap<>(); 
        String token = userService.userAccess(code); // 회원 가입 or 로그인 후 토큰 생성 
        result.put("token", token);
        
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/mypage")
    public ResponseEntity<HashMap<String, String>> userDelete(@AuthenticationPrincipal Long userId) throws Exception {

        if(!userService.userDelete(userId)){
            throw new Exception("회원 정보 삭제에 실패했습니다.");
        }

        HashMap<String, String> result = new HashMap<>();
        result.put("delete", userId+"번 회원이 탈퇴했습니다.");

        return ResponseEntity.ok().body(result);

    }

    @GetMapping("/mypage")
    public ResponseEntity<MyPageInfoResponse> getMyPageInformation(@AuthenticationPrincipal Long userId) {

        MyPageInfoResponse result = userService.selectUserInfo(userId);
        return ResponseEntity.ok().body(result);
    }

}
