package com.ssafy.woonana.controller.user;

import com.ssafy.woonana.domain.model.dto.user.request.UserEvaluateRequest;
import com.ssafy.woonana.domain.model.dto.user.response.LikeExcerciseResponse;
import com.ssafy.woonana.domain.model.dto.user.response.MyPageInfoResponse;
import com.ssafy.woonana.domain.model.dto.user.response.UserEvaluateResponse;
import com.ssafy.woonana.domain.model.dto.user.response.UserParticipateResponse;
import com.ssafy.woonana.domain.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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

    @GetMapping("/likes")
    @ApiOperation(value="내 운동 선호도 조회", notes = "로그인한 사용자의 운동 선호도 조회")
    public ResponseEntity<LikeExcerciseResponse> getExcerciseLike(@AuthenticationPrincipal Long userId){

        return null;
    }

    @PostMapping("/evalue")
    @ApiOperation(value="평가하기", notes="사용자의 점수를 평가한다.")
    public void evaluate(@RequestBody UserEvaluateRequest evaluation, @AuthenticationPrincipal Long userId){

        userService.evaluate(userId, evaluation.getUserId(), evaluation.getRating());

    }

    @GetMapping("/evalue")
    @ApiOperation(value="평가내역", notes="내가 평가한 모든 사용자들의 평가 내역을 보여준다.")
    public ResponseEntity<List<UserEvaluateResponse>> getEvaluateList(@AuthenticationPrincipal Long userId){

        List<UserEvaluateResponse> result = userService.getEvaluationList(userId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/participate")
    @ApiOperation(value="참여내역", notes="사용자가 참여한 게시글을 최신 순으로 보여준다.")
    public ResponseEntity<List<UserParticipateResponse>> getParticipateList(@AuthenticationPrincipal Long userId){

        List<UserParticipateResponse> result = userService.getParticipationList(userId);
        return ResponseEntity.ok().body(result);
    }
}
