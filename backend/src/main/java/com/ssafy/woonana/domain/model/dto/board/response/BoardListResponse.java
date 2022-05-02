package com.ssafy.woonana.domain.model.dto.board.response;

import lombok.Data;

import javax.persistence.Column;

@Data
public class BoardListResponse {

    private String userNickname;
    private String title;
    private int maxNumber;
    private String status;
}
