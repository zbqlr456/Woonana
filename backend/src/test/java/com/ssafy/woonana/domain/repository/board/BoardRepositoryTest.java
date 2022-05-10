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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
        Exercise e1 = new Exercise("탁구", "");
        Exercise e2 = new Exercise("캐치볼", "");
        Exercise e3 = new Exercise("산책", "");

        exerciseRepository.save(e1);
        exerciseRepository.save(e2);
        exerciseRepository.save(e3);

        BoardRequest boardRequest1 = new BoardRequest("제목!!","내용!!","경기도", LocalDateTime.parse("2022-04-25T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),2, 0,2);
        BoardRequest boardRequest2 = new BoardRequest("두번째제목!!","두번째내용!!","서울", LocalDateTime.parse("2022-04-26T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),4, 0, 2);
        BoardRequest boardRequest3 = new BoardRequest("3번째제목!!","3번째내용!!","서울", LocalDateTime.parse("2022-04-26T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),4,1, 1);

        Exercise exerciseRequest1 = exerciseRepository.findById(boardRequest1.getExerciseId()).get();
        Exercise exerciseRequest2 = exerciseRepository.findById(boardRequest2.getExerciseId()).get();
        Exercise exerciseRequest3 = exerciseRepository.findById(boardRequest3.getExerciseId()).get();
        assertThat(exerciseRequest1.getName()).isEqualTo("캐치볼");


        // 운동별 게시글 목록 조회
        // TODO: paging
        List<Board> findBoards = exerciseRepository.findById(exerciseRequest1.getId()).get().getBoards();

        for (Board b : findBoards) {
            System.out.println("b.toString() = " + b.toString());
        }

    }

    @Test
    public void boardCRUD() {

        Exercise e1 = new Exercise("pingpong", "");
        Exercise e2 = new Exercise("catch", "");
        Exercise e3 = new Exercise("walk", "");

        exerciseRepository.save(e1);
        exerciseRepository.save(e2);
        exerciseRepository.save(e3);

        BoardRequest boardRequest1 = new BoardRequest("제목!!","내용!!","경기도", LocalDateTime.parse("2022-04-25T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),2, 0,2);
        BoardRequest boardRequest2 = new BoardRequest("두번째제목!!","두번째내용!!","서울", LocalDateTime.parse("2022-04-26T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),4, 0, 2);
        BoardRequest boardRequest3 = new BoardRequest("3번째제목!!","3번째내용!!","서울", LocalDateTime.parse("2022-04-26T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),4,1, 1);

        Exercise exerciseRequest1 = exerciseRepository.findById(boardRequest1.getExerciseId()).get();
        Exercise exerciseRequest2 = exerciseRepository.findById(boardRequest2.getExerciseId()).get();
        Exercise exerciseRequest3 = exerciseRepository.findById(boardRequest3.getExerciseId()).get();

        /*
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
        */
    }

    @Test
    public void userBoard() {

        //given
        Exercise e1 = new Exercise("탁구", "");
        exerciseRepository.save(e1);
        User u1 = new User(123123123L, "dkdk", "dkdk", "닉네임", "sdfks", "dsf");
        userRepository.save(u1);

        // when
        BoardRequest boardRequest1 = new BoardRequest("제목!!","내용!!","경기도", LocalDateTime.parse("2022-04-25T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),2, 0,0);
        Exercise exerciseRequest1 = exerciseRepository.findById(boardRequest1.getExerciseId()).get();
        Board board1 = new Board(boardRequest1.getTitle(), boardRequest1.getContent(), boardRequest1.getPlace(), boardRequest1.getMeetStartDate(), boardRequest1.getMeetEndDate(), boardRequest1.getMaxNumber(), 0, exerciseRequest1, u1);
        boardRepository.save(board1);

        // then
        assertThat(board1.getUser()).isEqualTo(u1);

    }

    @Test
    public void changeStatus() {
        Board findboard = boardRepository.findById(38L).get();
        if (findboard.getStatus().equals("DONE")) {
        } else {
            findboard.changeStatus("CLOSE");
        }

        System.out.println("findboard = " + findboard.getStatus());

        Board ff = boardRepository.findById(38L).get();
        System.out.println("ff.getStatus() = " + ff.getStatus());
    }

    @Test
    public void paging() {
        PageRequest pageRequest = PageRequest.of(0, 100, Sort.by(Sort.Direction.DESC, "createdDate"));
        Slice<Board> page = boardRepository.findAll(pageRequest);
        List<Board> content = page.getContent();
        for (Board b : content) {
            System.out.println("b = " + b.getId());
            System.out.println(page.hasNext());
        }
    }

    @Test
    public void getBoardsByExercise() {
        List<Board> findBoards = boardRepository.findBoardsByExerciseId(4L);
        for (Board b : findBoards) {
            System.out.println("b.getTitle() = " + b.getTitle());
        }
    }

    @Test
    public void getAllByOrderByMeetStartDateAsc() {
        List<Board> findBoards = boardRepository.findBoardsByOrderByMeetStartDateAsc();
        for (Board b : findBoards) {
            System.out.println("b.getTitle() = " + b.getId());
        }
    }
}