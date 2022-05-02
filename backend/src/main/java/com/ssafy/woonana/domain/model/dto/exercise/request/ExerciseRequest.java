package com.ssafy.woonana.domain.model.dto.exercise.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ExerciseRequest {

    private String[] exerciseNames;

    public ExerciseRequest(String[] exerciseNames) {
        this.exerciseNames = exerciseNames;
    }
}
