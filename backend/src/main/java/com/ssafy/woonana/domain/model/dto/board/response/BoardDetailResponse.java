package com.ssafy.woonana.domain.model.dto.board.response;

import com.ssafy.woonana.domain.model.entity.board.Board;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDetailResponse {

    private String title;
    private String content;
    private String place;
    private LocalDateTime createdDate;
    private LocalDateTime meetStartDate;
    private LocalDateTime meetEndDate;
    private int allowedNumber;
    private int maxNumber;
    private Long exerciseId;
    private String status;

    public BoardDetailResponse(String title, String content, String place, LocalDateTime createdDate, LocalDateTime meetStartDate, LocalDateTime meetEndDate, int maxNumber, long exerciseId, String status) {
        this.title = title;
        this.content = content;
        this.place = place;
        this.createdDate = createdDate;
        this.meetStartDate = meetStartDate;
        this.meetEndDate = meetEndDate;
        this.maxNumber = maxNumber;
        this.exerciseId = exerciseId;
        this.status = status;
    }

    public BoardDetailResponse(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.place = board.getPlace();
        this.createdDate = board.getCreatedDate();
        this.meetStartDate = board.getMeetStartDate();
        this.meetEndDate = board.getMeetEndDate();
        this.allowedNumber = board.getAllowedNumber();
        this.maxNumber = board.getMaxNumber();
        this.exerciseId = board.getExercise().getId();
        this.status = board.getStatus();
    }
}
