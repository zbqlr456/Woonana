package com.ssafy.woonana.domain.model.entity.board;

import com.ssafy.woonana.domain.model.entity.BaseTimeEntity;
import com.ssafy.woonana.domain.model.entity.exercise.Exercise;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"title", "content", "maxNumber"})
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;
    @Column(name = "board_title")
    private String title;
    @Column(name = "board_content")
    private String content;
    @CreatedDate
    @Column(name = "board_created_date")
    private LocalDateTime createdDate;
    @Column(name = "board_place")
    private String place;
    @Column(name = "board_meet_start_date")
    private LocalDateTime meetStartDate;
    @Column(name = "board_meet_end_date")
    private LocalDateTime meetEndDate;
    @Column(name = "board_max_number")
    private int maxNumber;

    @Column(name = "board_status")
    @Convert(converter = StatusAttributeConverter.class)
    private String status; // OPEN, CLOSE, DONE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    // 사진 url, 사용자 추가

    public Board(String title, String content, String place, LocalDateTime meetStartDate, LocalDateTime meetEndDate, int maxNumber, Exercise exercise) {
        this.title = title;
        this.content = content;
        this.place = place;
        this.meetStartDate = meetStartDate;
        this.meetEndDate = meetEndDate;
        this.maxNumber = maxNumber;
        this.status = "OPEN";

        if (exercise != null) {
            changeExercise(exercise);
        }
    }

    private void changeExercise(Exercise exercise) {
        this.exercise = exercise;
        exercise.getBoards().add(this);
    }
}
