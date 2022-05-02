package com.ssafy.woonana.domain.service.board;

import com.ssafy.woonana.domain.model.entity.board.Board;
import com.ssafy.woonana.domain.model.entity.participation.Participation;
import com.ssafy.woonana.domain.model.entity.user.User;
import com.ssafy.woonana.domain.repository.board.BoardRepository;
import com.ssafy.woonana.domain.repository.board.ExerciseRepository;
import com.ssafy.woonana.domain.repository.participation.ParticipationRepository;
import com.ssafy.woonana.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final BoardRepository boardRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long register(Long boardId, Long userId) {

        Board findBoard = boardRepository.findById(boardId).get();
        User requestUser = userRepository.findById(userId).get();

        boolean isUserAllowed = false;
        // 모집 옵션이 승인 거절 방법(0)일 때 승인 여부(isUserAllowed)는 false

        if (findBoard.getParticipationOption() == 1) { // 모집 옵션이 선착순(1)일 때
            isUserAllowed = true;
        }

        Participation participation = new Participation(requestUser, findBoard, isUserAllowed);
        participationRepository.save(participation);
        System.out.println(participation.getId());
        System.out.println(participation.getBoard());
        System.out.println(participation.getLastModifiedDate());
        return participation.getId();
    }
}
