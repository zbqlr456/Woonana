package com.ssafy.woonana.controller.exercise;

import com.ssafy.woonana.domain.model.dto.exercise.request.ExerciseRequest;
import com.ssafy.woonana.domain.service.exercise.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping("")
    public String exercise() {
        return "exercise";
    }

    @PostMapping
    public ResponseEntity registerExercise(@RequestBody ExerciseRequest exerciseRequest) {
        exerciseService.register(exerciseRequest);
        return new ResponseEntity(HttpStatus.OK);
    }
}
