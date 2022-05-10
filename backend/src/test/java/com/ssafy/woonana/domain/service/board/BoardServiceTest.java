package com.ssafy.woonana.domain.service.board;

import com.ssafy.woonana.domain.model.dto.board.request.BoardRequest;
import com.ssafy.woonana.domain.model.entity.board.Board;
import com.ssafy.woonana.domain.model.entity.exercise.Exercise;
import com.ssafy.woonana.domain.model.entity.user.User;
import com.ssafy.woonana.domain.repository.board.BoardRepository;
import com.ssafy.woonana.domain.repository.board.ExerciseRepository;
import com.ssafy.woonana.domain.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;


    @Test
    void saveBoard() {
        // 사용자가 이미 작성한 글 중 만남 시간이나 끝나는 시간이 등록하는 시간의 사이에 있는 경우 글 등록 실패

        //given
        User u1 = createUser();
        Exercise e1 = createExercise("탁구", "");
        System.out.println("e1 = " + e1.getId());
        //Long boardId = boardService.register(boardRequest1);


        // then
        //Board getBoard = boardRepository.findById(boardId).get();
        //System.out.println("getBoard = " + getBoard);
        //Assertions.assertThat(getBoard.getId()).isEqualTo(boardId);

        //Board board1 = new Board(boardRequest1.getTitle(), boardRequest1.getContent(), boardRequest1.getPlace(), boardRequest1.getMeetStartDate(), boardRequest1.getMeetEndDate(), boardRequest1.getMaxNumber(), exerciseRequest1, u1);
        //boardRepository.save(board1);
//        BoardRequest boardRequest2 = new BoardRequest("둘제목!!","둘내용!!","경기도", LocalDateTime.parse("2022-04-25T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),2, 1);
  //      Exercise exerciseRequest2 = exerciseRepository.findById(boardRequest2.getExerciseId()).get();
    //    Board board2 = new Board(boardRequest2.getTitle(), boardRequest2.getContent(), boardRequest2.getPlace(), boardRequest2.getMeetStartDate(), boardRequest2.getMeetEndDate(), boardRequest2.getMaxNumber(), exerciseRequest2, u1);
      //  boardRepository.save(board2);

        // when

        //Board board = new Board()
        //Long boardId = boardService.registBoard(boardRequest1);
        //System.out.println("board2 = " + board1.getMeetStartDate());
        //System.out.println("board2 = " + board1.getMeetEndDate());
        //System.out.println("board2 = " + board2.getMeetStartDate());
        //System.out.println("board2 = " + board2.getMeetEndDate());
    }

    private Exercise createExercise(String eName, String imageUrl) {
        Exercise e1 = new Exercise(eName, imageUrl);
        em.persist(e1);
        return e1;
    }

    private User createUser() {
        User u1 = new User(123123123L, "dkdk", "dkdk", "닉네임", "sdfks", "dsf");
        em.persist(u1);
        return u1;
    }
}