package com.ssafy.woonana.controller.board;

import com.ssafy.woonana.domain.model.dto.board.request.BoardRequest;
import com.ssafy.woonana.domain.model.dto.board.response.BoardDetailResponse;
import com.ssafy.woonana.domain.model.dto.board.response.BoardListResponse;
import com.ssafy.woonana.domain.service.board.BoardService;
import com.ssafy.woonana.error.exception.ErrorResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "게시글 API", tags = {"Board"})
@RestController
@RequestMapping("api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    @ApiOperation(value = "글 하나 등록", notes = "새로운 글을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "글 작성 성공"),
            @ApiResponse(code = 400, message = "input 오류", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "토큰 만료 || 토큰 없음 || 토큰 오류 => 권한 인증 오류", response = ErrorResponse.class),
            @ApiResponse(code = 409, message = "글 작성 불가 (작성자가 동시간대에 다른 참여 신청된 경우)", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
    })
    public ResponseEntity registerBoard(@RequestBody BoardRequest boardRequest, @AuthenticationPrincipal Long userId) {
        boardService.register(boardRequest, userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    @ApiOperation(value = "게시 글 하나 상세보기", notes = "글 목록에서 선택한 하나의 글에 대해 상세 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "글 하나 조회 성공"),
            @ApiResponse(code = 401, message = "토큰 만료 || 토큰 없음 || 토큰 오류 => 권한 인증 오류", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "해당 글의 정보 없음", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
    })
    public ResponseEntity<BoardDetailResponse> getOneBoard(@PathVariable("boardId") Long boardId) {

        return ResponseEntity.ok(boardService.getOneBoard(boardId));
    }

    @GetMapping
    @ApiOperation(value = "신청 가능한 글 전체 목록", notes = "메인 페이지의 글 목록을 무한 스크롤로 보여준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "글 작성 성공"),
            @ApiResponse(code = 401, message = "토큰 만료 || 토큰 없음 || 토큰 오류 => 권한 인증 오류", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "글 정보가 없음", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
    })
    public ResponseEntity<List<BoardListResponse>> getAllBoards() {

        return ResponseEntity.ok(boardService.getAllBoards());
    }

    @DeleteMapping("/{boardId}")
    @ApiOperation(value = "게시 글 하나 삭제", notes = "작성자가 본인이 작성한 글을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "글 하나 삭제 성공"),
            @ApiResponse(code = 401, message = "토큰 만료 || 토큰 없음 || 토큰 오류 => 권한 인증 오류", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "글 정보가 없음", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "서버 에러", response = ErrorResponse.class)
    })
    public ResponseEntity deleteBoard(@PathVariable("{boardId}") Long boardId) {

        boardService.deleteBoard(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
