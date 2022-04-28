package com.ssafy.woonana.domain.model.dto.board.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardRequest {

    private String title;
    private String content;
    private String place;
    private LocalDateTime meetStartDate;
    private LocalDateTime meetEndDate;
    private int maxNumber;
    private Long exerciseId;
    private Long userId;

    public BoardRequest(String title, String content, String place, LocalDateTime meetStartDate, LocalDateTime meetEndDate, int maxNumber, long exerciseId) {
        this.title = title;
        this.content = content;
        this.place = place;
        this.meetStartDate = meetStartDate;
        this.meetEndDate = meetEndDate;
        this.maxNumber = maxNumber;
        this.exerciseId = exerciseId;
    }
    public BoardRequest(String title, String content, String place, LocalDateTime meetStartDate, LocalDateTime meetEndDate, int maxNumber, long exerciseId, long userId) {
        this.title = title;
        this.content = content;
        this.place = place;
        this.meetStartDate = meetStartDate;
        this.meetEndDate = meetEndDate;
        this.maxNumber = maxNumber;
        this.exerciseId = exerciseId;
        this.userId = userId;
    }
}
