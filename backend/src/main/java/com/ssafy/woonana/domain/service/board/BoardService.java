package com.ssafy.woonana.domain.service.board;

import com.ssafy.woonana.domain.model.dto.board.request.BoardRequest;
import com.ssafy.woonana.domain.model.dto.board.request.BoardUpdateRequest;
import com.ssafy.woonana.domain.model.dto.board.response.*;
import com.ssafy.woonana.domain.model.dto.user.response.UserParticipatedCheck;
import com.ssafy.woonana.domain.model.entity.board.Board;
import com.ssafy.woonana.domain.model.entity.exercise.Exercise;
import com.ssafy.woonana.domain.model.entity.participation.Participation;
import com.ssafy.woonana.domain.model.entity.user.User;
import com.ssafy.woonana.domain.repository.board.BoardRepository;
import com.ssafy.woonana.domain.repository.board.ExerciseRepository;
import com.ssafy.woonana.domain.repository.evaluation.EvaluationRepository;
import com.ssafy.woonana.domain.repository.exercise.ExerciseLogRepository;
import com.ssafy.woonana.domain.repository.participation.ParticipationRepository;
import com.ssafy.woonana.domain.repository.user.UserRepository;
import com.ssafy.woonana.error.exception.custom.ParticipationDuplicateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    @PersistenceContext EntityManager em;
    private final BoardRepository boardRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseLogRepository exerciseLogRepository;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;
    private final EvaluationRepository evaluationRepository;
    private final AwsS3Service awsS3Service;

    @Transactional
    public Long register(BoardRequest boardRequest, Long userId) throws IOException {
        Exercise exerciseRequest = exerciseRepository.findById(boardRequest.getExerciseId()).get();
        User boardUser = userRepository.findById(userId).get();
        Board board = new Board(boardRequest.getTitle(), boardRequest.getContent(), boardRequest.getPlace(), boardRequest.getMeetStartDate(), boardRequest.getMeetEndDate(), boardRequest.getMaxNumber(), boardRequest.getParticipationOption(), exerciseRequest, boardUser);
        // ????????? ????????? ????????? ??????
        if (!boardRequest.getFile().isEmpty()) {
            String url = awsS3Service.uploadFile(boardRequest.getFile(), "static");
            board.setImageUrl(url);
        }
        // ??? ???????????? ?????? ????????? board??? ????????? ??????
        this.isTimeOverlapped(boardUser.getUserId(), board.getMeetStartDate(), board.getMeetEndDate());
        board.updateAllowedMemberCount();
        boardRepository.save(board);

        // ??? ???????????? ?????? ?????? ??????
        boolean isUserAllowed = true;
        Participation participation = new Participation(boardUser, board, isUserAllowed);
        participationRepository.save(participation);

        return board.getId();
    }

    public List<BoardListResponse> getAllBoards() {
        List<Board> boardList = boardRepository.findAllByOrderByCreatedDateDesc();
        List<BoardListResponse> list = new ArrayList<>();

        for (Board b : boardList) {
            list.add(new BoardListResponse(b.getId(), b.getUser().getUserNickname(), b.getUser().getUserEmail(), b.getTitle(), b.getAllowedNumber(), b.getMaxNumber(), b.getStatus(), b.getImageUrl(), b.getPlace()));
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
            list.add(new BoardListResponse(b.getId(), b.getUser().getUserNickname(), b.getUser().getUserEmail(), b.getTitle(), b.getAllowedNumber(), b.getMaxNumber(), b.getStatus(), b.getImageUrl(), b.getPlace()));
        }

        return list;
    }
    public List<BoardListResponse> getBoardsByExercise(Long exerciseId) {
        List<Board> findBoards = boardRepository.findBoardsByExerciseId(exerciseId);
        List<BoardListResponse> list = new ArrayList<>();
        for (Board b : findBoards) {
            list.add(new BoardListResponse(b.getId(), b.getUser().getUserNickname(), b.getUser().getUserEmail(), b.getTitle(), b.getAllowedNumber(), b.getMaxNumber(), b.getStatus(), b.getImageUrl(), b.getPlace()));
        }

        return list;
    }

    public Long getBoardsCountByExercise(Long exerciseId) {
        return boardRepository.getCountByExercise(exerciseId);
    }

    public BoardDetailResponse getOneBoard(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).get();
        User findBoardUser = findBoard.getUser();
        return new BoardDetailResponse(findBoard, findBoardUser);
    }
    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        Board findBoard = boardRepository.findById(boardId).get();
        if (findBoard.getUser().getUserId() == userId) {
            // TODO: ?????????????????? ????????? ?????? ??????
            // TODO: ???????????? ?????? ????????? ?????? ?????? ??????, ?????? ????????? ????????? ?????? ????????? ?????? ??????
            exerciseLogRepository.deleteLogByBoardId(boardId);

            participationRepository.deleteParticipationByBoardId(boardId);

            evaluationRepository.deleteEvaluationByBoardId(boardId);

            boardRepository.deleteById(boardId);
        }

    }

    private void isTimeOverlapped(Long userId, LocalDateTime meetStartDate, LocalDateTime meetEndDate) {
        List<UserParticipatedCheck> participationsByTime = participationRepository.findParticipationsByTime(userId, meetStartDate, meetEndDate);
        if (participationsByTime.size() != 0)
            throw new ParticipationDuplicateException("??? ???????????? ?????? ?????? ????????? ????????? ????????????.");
    }

    public List<ParticipatedMemberResponse> getParticipations(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).get();
        List<Participation> findList = findBoard.getParticipations();
        List<ParticipatedMemberResponse> list = new ArrayList<>();
        for (Participation p : findList) {
            User oneUser = p.getUser();
            if (p.isAllowed() == true)
                list.add(new ParticipatedMemberResponse(oneUser.getUserId(), oneUser.getUserNickname(), oneUser.getUserProfileUrl(), oneUser.getUserSex(), oneUser.getUserRatingScore()));
        }
        return list;
    }

    @Transactional
    public void update(BoardUpdateRequest boardUpdateRequest, Long userId, Long boardId) throws Exception {
        User findUser = userRepository.findById(userId).get();
        User boardUser = boardRepository.findById(boardId).get().getUser();
        if (findUser != boardUser) {
            throw new RuntimeException("??? ???????????? ??????");
        }
        boardRepository.updateOneBoard(boardUpdateRequest.getTitle(), boardUpdateRequest.getContent(), boardUpdateRequest.getMaxNumber(), boardId);

    }
}
