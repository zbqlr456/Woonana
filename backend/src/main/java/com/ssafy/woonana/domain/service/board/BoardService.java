package com.ssafy.woonana.domain.service.board;

import com.ssafy.woonana.domain.model.dto.board.request.BoardRequest;
import com.ssafy.woonana.domain.model.dto.board.response.BoardDetailResponse;
import com.ssafy.woonana.domain.model.dto.board.response.BoardListResponse;
import com.ssafy.woonana.domain.model.entity.board.Board;
import com.ssafy.woonana.domain.model.entity.exercise.Exercise;
import com.ssafy.woonana.domain.model.entity.user.User;
import com.ssafy.woonana.domain.repository.board.BoardRepository;
import com.ssafy.woonana.domain.repository.board.ExerciseRepository;
import com.ssafy.woonana.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long register(BoardRequest boardRequest, Long userId) {
        Exercise exerciseRequest = exerciseRepository.findById(boardRequest.getExerciseId()).get();
        User boardUser = userRepository.findById(userId).get();
        Board board = new Board(boardRequest.getTitle(), boardRequest.getContent(), boardRequest.getPlace(), boardRequest.getMeetStartDate(), boardRequest.getMeetEndDate(), boardRequest.getMaxNumber(), boardRequest.getParticipationOption(), exerciseRequest, boardUser);
        boardRepository.save(board);
        return board.getId();
    }

    public List<BoardListResponse> getAllBoards() {

        return null;
    }

    public BoardDetailResponse getOneBoard(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).get();
        return new BoardDetailResponse(findBoard);
    }

    public void deleteBoard(Long boardId) {
        // TODO: 참여하기로한 멤버가 있는 경우
        // TODO: 완료하지 않은 운동인 경우 운동 기록, 참여 등에도 삭제가 반영 되는지 확인 필요
        boardRepository.deleteById(boardId);
    }
}
