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
import java.util.List;

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
        User u1 = new User(123123123L, "dkdk", "dkdk", "닉네임", "sdfks", "dsf");
        userRepository.save(u1);

        // when
        Participation participation = participationRepository.findParticipation(22L, 2220561222L);

        System.out.println("participation = " + participation);

        List<Participation> listByBoardId = participationRepository.findListByBoardId(10L);
        for (Participation p : listByBoardId) {
            System.out.println("p = " + p.isAllowed());
        }
        //participationRepository.save()

    }

    @Test
    public void maxNumberCheck() {
        Board findBoard = boardRepository.findById(21L).get();
        List<Participation> findBoardList = participationRepository.findListByBoardId(21L);
        int memberCnt = 0;
        for (Participation p : findBoardList) {
            if (p.isAllowed()) memberCnt++;
        }
//        findBoard.updateAllowedMemberCount(memberCnt);
    }
}
