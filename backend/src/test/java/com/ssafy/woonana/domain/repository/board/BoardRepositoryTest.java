package com.ssafy.woonana.domain.repository.board;

import com.ssafy.woonana.domain.model.dto.board.request.BoardRequest;
import com.ssafy.woonana.domain.model.dto.board.response.BoardDetailResponse;
import com.ssafy.woonana.domain.model.entity.board.Board;
import com.ssafy.woonana.domain.model.entity.exercise.Exercise;
import com.ssafy.woonana.domain.model.entity.user.User;
import com.ssafy.woonana.domain.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void findBoardListByExerciseId() {
        Exercise e1 = new Exercise("탁구");
        Exercise e2 = new Exercise("캐치볼");
        Exercise e3 = new Exercise("산책");

        exerciseRepository.save(e1);
        exerciseRepository.save(e2);
        exerciseRepository.save(e3);

        BoardRequest boardRequest1 = new BoardRequest("제목!!","내용!!","경기도", LocalDateTime.parse("2022-04-25T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),2,2);
        BoardRequest boardRequest2 = new BoardRequest("두번째제목!!","두번째내용!!","서울", LocalDateTime.parse("2022-04-26T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),4,2);
        BoardRequest boardRequest3 = new BoardRequest("3번째제목!!","3번째내용!!","서울", LocalDateTime.parse("2022-04-26T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),4,1);

        Exercise exerciseRequest1 = exerciseRepository.findById(boardRequest1.getExerciseId()).get();
        Exercise exerciseRequest2 = exerciseRepository.findById(boardRequest2.getExerciseId()).get();
        Exercise exerciseRequest3 = exerciseRepository.findById(boardRequest3.getExerciseId()).get();
        assertThat(exerciseRequest1.getName()).isEqualTo("캐치볼");

        Board board1 = new Board(boardRequest1.getTitle(), boardRequest1.getContent(), boardRequest1.getPlace(), boardRequest1.getMeetStartDate(), boardRequest1.getMeetEndDate(), boardRequest1.getMaxNumber(), exerciseRequest1);
        Board board2 = new Board(boardRequest2.getTitle(), boardRequest2.getContent(), boardRequest2.getPlace(), boardRequest2.getMeetStartDate(), boardRequest2.getMeetEndDate(), boardRequest2.getMaxNumber(), exerciseRequest2);
        Board board3 = new Board(boardRequest3.getTitle(), boardRequest3.getContent(), boardRequest3.getPlace(), boardRequest3.getMeetStartDate(), boardRequest3.getMeetEndDate(), boardRequest3.getMaxNumber(), exerciseRequest3);
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);

        // 운동별 게시글 목록 조회
        // TODO: paging
        List<Board> findBoards = exerciseRepository.findById(exerciseRequest1.getId()).get().getBoards();

        for (Board b : findBoards) {
            System.out.println("b.toString() = " + b.toString());
        }

    }

    @Test
    public void boardCRUD() {

        Exercise e1 = new Exercise("pingpong");
        Exercise e2 = new Exercise("catch");
        Exercise e3 = new Exercise("walk");

        exerciseRepository.save(e1);
        exerciseRepository.save(e2);
        exerciseRepository.save(e3);

        BoardRequest boardRequest1 = new BoardRequest("titile!!","content!!","gy", LocalDateTime.parse("2022-04-25T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),2,2);
        BoardRequest boardRequest2 = new BoardRequest("titletile!!","2content!!","seoul", LocalDateTime.parse("2022-04-26T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),4,2);
        BoardRequest boardRequest3 = new BoardRequest("3titletile!!","3content!!","seoul", LocalDateTime.parse("2022-04-26T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),4,1);

        Exercise exerciseRequest1 = exerciseRepository.findById(boardRequest1.getExerciseId()).get();
        Exercise exerciseRequest2 = exerciseRepository.findById(boardRequest2.getExerciseId()).get();
        Exercise exerciseRequest3 = exerciseRepository.findById(boardRequest3.getExerciseId()).get();

        Board board1 = new Board(boardRequest1.getTitle(), boardRequest1.getContent(), boardRequest1.getPlace(), boardRequest1.getMeetStartDate(), boardRequest1.getMeetEndDate(), boardRequest1.getMaxNumber(), exerciseRequest1);
        Board board2 = new Board(boardRequest2.getTitle(), boardRequest2.getContent(), boardRequest2.getPlace(), boardRequest2.getMeetStartDate(), boardRequest2.getMeetEndDate(), boardRequest2.getMaxNumber(), exerciseRequest2);
        Board board3 = new Board(boardRequest3.getTitle(), boardRequest3.getContent(), boardRequest3.getPlace(), boardRequest3.getMeetStartDate(), boardRequest3.getMeetEndDate(), boardRequest3.getMaxNumber(), exerciseRequest3);

        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);

        // 단건 조회
        Board findBoard = boardRepository.findById(board1.getId()).get();
        assertThat(findBoard).isEqualTo(board1);
        BoardDetailResponse boardDetailResponse = new BoardDetailResponse(findBoard);
        System.out.println("boardDetailResponse = " + boardDetailResponse.toString());

        // 리스트 조회
        List<Board> boardList = boardRepository.findAll();
        assertThat(boardList.size()).isEqualTo(3);

        // 전체 목록 카운트
        long count = boardRepository.count();
        assertThat(count).isEqualTo(3);

        // 삭제
        boardRepository.delete(board1);
        long deletedCount = boardRepository.count();
        assertThat(deletedCount).isEqualTo(2);

    }

    @Test
    public void userBoard() {

        //given
        Exercise e1 = new Exercise("탁구");
        exerciseRepository.save(e1);
        User u1 = new User(123123123L, "dkdk", "dkdk", "닉네임", "sdfks", "dsf");
        userRepository.save(u1);

        // when
        BoardRequest boardRequest1 = new BoardRequest("제목!!","내용!!","경기도", LocalDateTime.parse("2022-04-25T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),2, 1);
        Exercise exerciseRequest1 = exerciseRepository.findById(boardRequest1.getExerciseId()).get();
        Board board1 = new Board(boardRequest1.getTitle(), boardRequest1.getContent(), boardRequest1.getPlace(), boardRequest1.getMeetStartDate(), boardRequest1.getMeetEndDate(), boardRequest1.getMaxNumber(), exerciseRequest1, u1);
        boardRepository.save(board1);

        // then
        assertThat(board1.getUser()).isEqualTo(u1);

    }
}