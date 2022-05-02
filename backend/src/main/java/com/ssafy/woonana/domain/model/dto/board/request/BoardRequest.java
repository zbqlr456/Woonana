package com.ssafy.woonana.domain.model.dto.board.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardRequest {

    private String title;
    private String content;
    private String place;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetStartDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime meetEndDate;
    private int maxNumber;
    private int participationOption;
    private Long exerciseId;
    //private Long userId;

    public BoardRequest(String title, String content, String place, LocalDateTime meetStartDate, LocalDateTime meetEndDate, int maxNumber, int participationOption, long exerciseId) {
        this.title = title;
        this.content = content;
        this.place = place;
        this.meetStartDate = meetStartDate;
        this.meetEndDate = meetEndDate;
        this.maxNumber = maxNumber;
        this.participationOption = participationOption;
        this.exerciseId = exerciseId;
    }
    public BoardRequest(String title, String content, String place, LocalDateTime meetStartDate, LocalDateTime meetEndDate, int maxNumber, long exerciseId) {
        this.title = title;
        this.content = content;
        this.place = place;
        this.meetStartDate = meetStartDate;
        this.meetEndDate = meetEndDate;
        this.maxNumber = maxNumber;
        this.exerciseId = exerciseId;
      //  this.userId = userId;
    }
}
