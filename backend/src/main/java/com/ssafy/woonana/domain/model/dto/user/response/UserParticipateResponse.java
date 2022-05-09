package com.ssafy.woonana.domain.model.dto.user.response;

import com.ssafy.woonana.domain.model.entity.board.Board;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserParticipateResponse { // 참여 내역 조회

    private Long boardId;
    private String boardTitle;
    private LocalDateTime boardMeetStartDate;
    private LocalDateTime boardMeetEndDate;

    @Builder
    public UserParticipateResponse(Board entity){
        boardId=entity.getId();
        boardTitle=entity.getTitle();
        boardMeetStartDate=entity.getMeetStartDate();
        boardMeetEndDate=entity.getMeetEndDate();
    }
}
