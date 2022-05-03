package com.ssafy.woonana.domain.model.dto.board.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class BoardListResponse {

    private Long boardId;
    private String userNickname;
    private String title;
    private int allowedNumber;
    private int maxNumber;
    private String status;
}