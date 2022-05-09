package com.ssafy.woonana.domain.model.dto.board.request;

import lombok.Data;

@Data
public class ApproveRequest {
    private Long participationId;
    private Long userId;
    private Long boardId;
    private Boolean allowed;
}
