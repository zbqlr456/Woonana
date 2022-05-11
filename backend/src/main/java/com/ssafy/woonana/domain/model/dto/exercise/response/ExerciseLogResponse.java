package com.ssafy.woonana.domain.model.dto.exercise.response;

import lombok.Data;

@Data
public class ExerciseLogResponse {

    private int exercise_log_id;
    private int exercise_count;
    private int user_id;
    private int exercise_id;
    private int board_id;
}
