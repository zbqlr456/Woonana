package com.ssafy.woonana.controller.board;

import com.ssafy.woonana.domain.service.board.ParticipationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "참가 신청 API", tags = {"Participation"})
@RestController
@RequestMapping("api/participation")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    @PostMapping("/{boardId}")
    public ResponseEntity registerParticipation(@PathVariable("boardId") Long boardId, @AuthenticationPrincipal Long userId) {

        return ResponseEntity.ok(participationService.register(boardId, userId));
    }
}
