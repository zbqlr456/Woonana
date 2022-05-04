package com.ssafy.woonana.domain.service.board;

import com.ssafy.woonana.domain.model.dto.board.request.BoardRequest;
import com.ssafy.woonana.domain.model.dto.board.response.BoardDetailResponse;
import com.ssafy.woonana.domain.model.dto.board.response.BoardListResponse;
import com.ssafy.woonana.domain.model.dto.user.response.UserParticipatedCheck;
import com.ssafy.woonana.domain.model.entity.board.Board;
import com.ssafy.woonana.domain.model.entity.exercise.Exercise;
import com.ssafy.woonana.domain.model.entity.participation.Participation;
import com.ssafy.woonana.domain.model.entity.user.User;
import com.ssafy.woonana.domain.repository.board.BoardRepository;
import com.ssafy.woonana.domain.repository.board.ExerciseRepository;
import com.ssafy.woonana.domain.repository.participation.ParticipationRepository;
import com.ssafy.woonana.domain.repository.user.UserRepository;
import com.ssafy.woonana.error.exception.custom.ParticipationDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;

    @Transactional
    public Long register(BoardRequest boardRequest, Long userId) {
        Exercise exerciseRequest = exerciseRepository.findById(boardRequest.getExerciseId()).get();
        User boardUser = userRepository.findById(userId).get();
        Board board = new Board(boardRequest.getTitle(), boardRequest.getContent(), boardRequest.getPlace(), boardRequest.getMeetStartDate(), boardRequest.getMeetEndDate(), boardRequest.getMaxNumber(), boardRequest.getParticipationOption(), exerciseRequest, boardUser);
        // 이 시간대에 참여 신청된 board가 있으면 종료
        this.isTimeOverlapped(boardUser.getUserId(), board.getMeetStartDate(), board.getMeetEndDate());
        board.updateAllowedMemberCount();
        boardRepository.save(board);

        // 글 작성자는 참여 승인 허가
        boolean isUserAllowed = true;
        Participation participation = new Participation(boardUser, board, isUserAllowed);
        participationRepository.save(participation);

        return board.getId();
    }

    public List<BoardListResponse> getAllBoards() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardListResponse> list = new ArrayList<>();

        for (Board b : boardList) {
            list.add(new BoardListResponse(b.getId(), b.getUser().getUserNickname(), b.getTitle(), b.getAllowedNumber(), b.getMaxNumber(), b.getStatus()));
        }

        return list;
    }

    public BoardDetailResponse getOneBoard(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).get();
        System.out.println("findBoard = " + findBoard.getStatus());
        return new BoardDetailResponse(findBoard);
    }

    public void deleteBoard(Long boardId) {
        // TODO: 참여하기로한 멤버가 있는 경우
        // TODO: 완료하지 않은 운동인 경우 운동 기록, 참여 등에도 삭제가 반영 되는지 확인 필요
        boardRepository.deleteById(boardId);
    }

    private void isTimeOverlapped(Long userId, LocalDateTime meetStartDate, LocalDateTime meetEndDate) {
        List<UserParticipatedCheck> participationsByTime = participationRepository.findParticipationsByTime(userId, meetStartDate, meetEndDate);
        if (participationsByTime.size() != 0)
            throw new ParticipationDuplicateException("이 시간대에 이미 참여 신청된 운동이 있습니다.");
    }
}
