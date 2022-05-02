package com.ssafy.woonana.domain.repository.board;

import com.ssafy.woonana.domain.model.dto.board.request.BoardRequest;
import com.ssafy.woonana.domain.model.entity.board.Board;
import com.ssafy.woonana.domain.model.entity.exercise.Exercise;
import com.ssafy.woonana.domain.model.entity.participation.Participation;
import com.ssafy.woonana.domain.model.entity.user.User;
import com.ssafy.woonana.domain.repository.participation.ParticipationRepository;
import com.ssafy.woonana.domain.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParticipationRepositoryTest {

    @Autowired
    ParticipationRepository participationRepository;
    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardRepository boardRepository;

    @Test
    public void participateUserToBoard() {
        //given
        Exercise e1 = new Exercise("탁구");
        exerciseRepository.save(e1);
        User u1 = new User(123123123L, "dkdk", "dkdk", "닉네임", "sdfks", "dsf");
        userRepository.save(u1);
        //BoardRequest boardRequest1 = new BoardRequest("제목!!","내용!!","경기도", LocalDateTime.parse("2022-04-25T20:00:00"), LocalDateTime.parse("2022-04-25T22:00:00"),2, 1);
        //Exercise exerciseRequest1 = exerciseRepository.findById(boardRequest1.getExerciseId()).get();
        //Board board1 = new Board(boardRequest1.getTitle(), boardRequest1.getContent(), boardRequest1.getPlace(), boardRequest1.getMeetStartDate(), boardRequest1.getMeetEndDate(), boardRequest1.getMaxNumber(), exerciseRequest1, u1);
        //boardRepository.save(board1);

        // when
        //Participation participation1 = new Participation(true, board1);
        //participationRepository.save()

        // 글 작성자는 자동 참가

        // 참가 신청하는 사용자도 같은 참가 id 갖도록 하기

    }
}
