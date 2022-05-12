package com.ssafy.woonana.controller.exercise;

import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLogResponse;
import com.ssafy.woonana.domain.service.exercise.ExerciseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin("*")
public class ExerciseLogController {

    @Autowired
    private ExerciseLogService exerciseLogService;

    @GetMapping("/myexercise/month/{userId}")
    public ResponseEntity<List<ExerciseLogResponse>> exerciseLogMonth(@PathVariable("userId") int userId){
        return ResponseEntity.ok(exerciseLogService.findExerciseLogByMonth(userId));
    }

    @GetMapping("/myexercise/record/{userId}")
    public ResponseEntity<List<ExerciseLogResponse>> exerciseLogRecord(@PathVariable("userId") int userId){
        return ResponseEntity.ok(exerciseLogService.findExerciseLogByUserId(userId));
    }

    @GetMapping("/myexercise/likes/{userId}")
    public ResponseEntity<List<ExerciseLogResponse>> exerciseLogLikes(@PathVariable("userId")int userId){
        return ResponseEntity.ok(exerciseLogService.findExerciseLogByLikes(userId));
    }
}
