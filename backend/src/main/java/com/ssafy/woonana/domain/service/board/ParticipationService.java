package com.ssafy.woonana.domain.service.board;

import com.ssafy.woonana.domain.model.dto.board.response.PickListDetail;
import com.ssafy.woonana.domain.model.dto.board.response.PickListResponse;
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

import java.util.List;
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
        return participation.getId();
    }

    public PickListResponse getAppliedList(Long boardId) {

        // boardId에 연관된 participateId 모든 목록 출력
        List<Participation> findAppliedList = participationRepository.findListByBoardId(boardId);
        PickListResponse response = new PickListResponse();
        for (Participation p : findAppliedList) {
            PickListDetail detail = new PickListDetail();
            User findUser = userRepository.findById(p.getUser().getUserId()).get();
            detail.setParticipationId(p.getId());
            detail.setUserId(findUser.getUserId());
            detail.setUserNickname(findUser.getUserNickname());
            detail.setProfileUrl(findUser.getUserProfileUrl());
            detail.setParticipationAllowed(p.isAllowed());
            response.addPickListDetail(detail);
        }

        return response;
    }
}
