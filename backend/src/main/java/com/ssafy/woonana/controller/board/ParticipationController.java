package com.ssafy.woonana.controller.board;

import com.ssafy.woonana.domain.model.dto.board.response.PickListResponse;
import com.ssafy.woonana.domain.service.board.ParticipationService;
import com.ssafy.woonana.error.exception.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(value = "참가 신청 API", tags = {"Participation"})
@RestController
@RequestMapping("api/participate")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    @PostMapping("/{boardId}")
    @ApiOperation(value = "게시글에 참여 신청", notes = "다른 사용자가 해당 게시글의 운동에 참여 신청을 한다.")
    @ApiResponse(code = 500, message = "참여 신청 불가", response = ErrorResponse.class)
    public ResponseEntity registerParticipation(@PathVariable("boardId") Long boardId, @AuthenticationPrincipal Long userId) {

        // 해당 board의 인원이 다 찼으면 종료
        participationService.isStatusClose(boardId);
        // 해당 user가 이미 board에 참여 신청 되어있으면 종료
        participationService.isUserRegistered(boardId, userId);

        return ResponseEntity.ok(participationService.register(boardId, userId));
    }

    @GetMapping("/pick/{boardId}")
    @ApiOperation(value = "글 작성자가 신청자 목록 볼 때 사용", notes = "해당 모임에 참가 신청한 사용자 목록")
    public ResponseEntity<PickListResponse> getAppliedList(@PathVariable("boardId") Long boardId) {

        return ResponseEntity.ok(participationService.getAppliedList(boardId));
    }

    @PatchMapping("/pick/approve/{participationId}")
    @ApiOperation(value = "글 작성자가 신청자 참여 승인할 때 사용", notes = "신청자 목록에서 해당 참여 정보에 대한 승인 여부를 허용한다.")
    public ResponseEntity approveParticipation(@PathVariable("participationId") Long participationId) {

        participationService.approve(participationId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping (value = {"/pick/refuse/{participationId}", "/{participationId}"})
    @ApiOperation(value = "글 작성자가 신청자 참여 거절할 때와, 참여 신청자가 참여 취소할 때 사용", notes = "신청자 목록에서 해당 참여자의 참여를 거절 후 신청자 목록에서 삭제한다, 신청자 목록에서 참여 신청 내역을 삭제한다.")
    public ResponseEntity cancelParticipation(@PathVariable("participationId") Long participationId) {
        participationService.cancel(participationId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
