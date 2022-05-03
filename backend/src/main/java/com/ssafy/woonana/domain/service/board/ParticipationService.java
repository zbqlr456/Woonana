package com.ssafy.woonana.domain.service.board;

import com.ssafy.woonana.domain.model.dto.board.request.ApproveRequest;
import com.ssafy.woonana.domain.model.dto.board.response.PickListDetail;
import com.ssafy.woonana.domain.model.dto.board.response.PickListResponse;
import com.ssafy.woonana.domain.model.entity.board.Board;
import com.ssafy.woonana.domain.model.entity.participation.Participation;
import com.ssafy.woonana.domain.model.entity.user.User;
import com.ssafy.woonana.domain.repository.board.BoardRepository;
import com.ssafy.woonana.domain.repository.board.ExerciseRepository;
import com.ssafy.woonana.domain.repository.participation.ParticipationRepository;
import com.ssafy.woonana.domain.repository.user.UserRepository;
import com.ssafy.woonana.error.exception.custom.ParticipationDuplicateException;
import com.ssafy.woonana.error.exception.custom.ParticipationIsFullException;
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

        // 모집 옵션이 승인 거절 방법(0)일 때 승인 여부(isUserAllowed)는 false
        boolean isUserAllowed = false;

        if (findBoard.getParticipationOption() == 1) { // 모집 옵션이 선착순(1)일 때
            isUserAllowed = true;
            findBoard.updateAllowedMemberCount();
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

    @Transactional
    public void approve(Long participationId) {
        Participation findParticipation = participationRepository.findById(participationId).get();
        Board findBoard = boardRepository.findById(findParticipation.getBoard().getId()).get();

        // 해당 board의 인원이 다 찼으면 종료
        this.isStatusClose(findBoard.getId());

        findParticipation.setAllowed(true);
        findBoard.updateAllowedMemberCount();

        System.out.println("findBoard = " + findBoard.getAllowedNumber());
        System.out.println("findBoard = " + findBoard.getMaxNumber());

        // 해당 참여자를 마지막으로 모집 인원이 다 찼을 때
        if (findBoard.getMaxNumber() <= findBoard.getAllowedNumber()){
            System.out.println("========ewr=wer=we=rw=ree=r=wer=wer========");
            System.out.println("findBoard = " + findBoard.getStatus());
        }

        participationRepository.save(findParticipation);
    }

    @Transactional
    public void cancel(Long participationId) {

        // TODO : 신청 거절시 변경할 데이터가 없어서 일단 삭제로 함
        Participation findParticipation = participationRepository.findById(participationId).get();
        participationRepository.delete(findParticipation);
    }

    public void isUserRegistered(Long boardId, Long userId) {

        Participation findParticipation = participationRepository.findParticipation(boardId, userId);
        if (findParticipation != null)
            throw new ParticipationDuplicateException();

    }

    public void isStatusClose(Long boardId) {
        Board findBoard = boardRepository.findById(boardId).get();
        if (findBoard.getStatus().equals("CLOSE"))
            throw new ParticipationIsFullException();
    }
}
