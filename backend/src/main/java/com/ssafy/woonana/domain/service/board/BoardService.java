package com.ssafy.woonana.domain.service.board;

import com.ssafy.woonana.domain.model.dto.board.request.BoardRequest;
import com.ssafy.woonana.domain.model.dto.board.response.*;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    private final AwsS3Service awsS3Service;

    @Transactional
    public Long register(BoardRequest boardRequest, Long userId) throws IOException {
        Exercise exerciseRequest = exerciseRepository.findById(boardRequest.getExerciseId()).get();
        User boardUser = userRepository.findById(userId).get();
        Board board = new Board(boardRequest.getTitle(), boardRequest.getContent(), boardRequest.getPlace(), boardRequest.getMeetStartDate(), boardRequest.getMeetEndDate(), boardRequest.getMaxNumber(), boardRequest.getParticipationOption(), exerciseRequest, boardUser);
        // 등록된 사진이 있으면 세팅
        if (!boardRequest.getFile().isEmpty()) {
            String url = awsS3Service.uploadFile(boardRequest.getFile(), "static");
            board.setImageUrl(url);
        }
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
        List<Board> boardList = boardRepository.findAllByOrderByIdDesc();
        List<BoardListResponse> list = new ArrayList<>();

        for (Board b : boardList) {
            list.add(new BoardListResponse(b.getId(), b.getUser().getUserNickname(), b.getUser().getUserEmail(), b.getTitle(), b.getAllowedNumber(), b.getMaxNumber(), b.getStatus(), b.getImageUrl()));
        }

        return list;
    }

    public List<MyBoardListResponse> getBoardsByUser(Long userId) {
        List<Board> boardList = boardRepository.findBoardsByUserId(userId);
        List<MyBoardListResponse> list = new ArrayList<>();

        for (Board b : boardList) {
            list.add(new MyBoardListResponse(b.getId(), b.getTitle(), b.getAllowedNumber(), b.getMaxNumber(), b.getStatus()));
        }

        return list;
    }

    public List<BoardListResponse> getBoardsByMeet() {
        List<Board> boardList = boardRepository.findBoardsByOrderByMeetStartDateAsc();
        List<BoardListResponse> list = new ArrayList<>();

        for (Board b : boardList) {
            list.add(new BoardListResponse(b.getId(), b.getUser().getUserNickname(), b.getUser().getUserEmail(), b.getTitle(), b.getAllowedNumber(), b.getMaxNumber(), b.getStatus(), b.getImageUrl()));
        }

        return list;
    }
    public List<BoardByExerciseListResponse> getBoardsByExercise(Long exerciseId) {
        List<Board> findBoards = boardRepository.findBoardsByExerciseId(exerciseId);
        List<BoardByExerciseListResponse> list = new ArrayList<>();
        for (Board b : findBoards) {
            list.add(new BoardByExerciseListResponse(b.getExercise().getId(), b.getId(), b.getPlace()));
        }

        return list;
    }

    public BoardDetailResponse getOneBoard(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).get();
        User findBoardUser = findBoard.getUser();
        return new BoardDetailResponse(findBoard, findBoardUser);
    }
    @Transactional
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

    public List<ParticipatedMemberResponse> getParticipations(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).get();
        List<Participation> findList = findBoard.getParticipations();
        List<ParticipatedMemberResponse> list = new ArrayList<>();
        for (Participation p : findList) {
            User oneUser = p.getUser();
            list.add(new ParticipatedMemberResponse(oneUser.getUserId(), oneUser.getUserNickname(), oneUser.getUserProfileUrl(), oneUser.getUserSex(), oneUser.getUserRatingScore()));
        }
        return list;
    }

}
