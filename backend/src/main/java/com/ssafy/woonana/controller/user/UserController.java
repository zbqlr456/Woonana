package com.ssafy.woonana.controller.user;

import com.ssafy.woonana.domain.model.dto.user.response.DeleteResponse;
import com.ssafy.woonana.domain.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<DeleteResponse> userDelete() {

        DeleteResponse delMsg = DeleteResponse.builder().delete("번 회원이 탈퇴했습니다.").build();
        return ResponseEntity.ok().body(delMsg);

    }
}
