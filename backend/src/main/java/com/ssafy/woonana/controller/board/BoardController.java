package com.ssafy.woonana.controller.board;

import com.ssafy.woonana.domain.model.dto.board.request.BoardRequest;
import com.ssafy.woonana.domain.model.dto.board.response.BoardDetailResponse;
import com.ssafy.woonana.domain.model.dto.board.response.BoardListResponse;
import com.ssafy.woonana.domain.service.board.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public ResponseEntity registerBoard(@RequestBody BoardRequest boardRequest, @AuthenticationPrincipal Long userId) {
        boardService.register(boardRequest, userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailResponse> getOneBoard(@PathVariable("boardId") Long boardId) {

        return ResponseEntity.ok(boardService.getOneBoard(boardId));
    }

    @GetMapping
    @ApiOperation(value = "신청 가능한 글 전체 목록", notes = "메인 페이지의 글 목록을 무한 스크롤로 보여준다.")
    public ResponseEntity<List<BoardListResponse>> getAllBoards() {

        return ResponseEntity.ok(boardService.getAllBoards());
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteBoard(@PathVariable("{boardId}") Long boardId) {

        boardService.deleteBoard(boardId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
