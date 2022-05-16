package com.ssafy.woonana.controller.exercise;

import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLog;
import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLogByMonthResponse;
import com.ssafy.woonana.domain.service.exercise.ExerciseLogService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "월별 운동 기록 조회", notes="userId 사용자의 총 운동 횟수, 월별 운동 기록을 조회한다.")
    public ResponseEntity<ExerciseLogByMonthResponse> exerciseLogMonth(@PathVariable("userId") Long userId){

        return ResponseEntity.ok(exerciseLogService.findExerciseLogByMonth(userId));
    }

    @GetMapping("/myexercise/record/{userId}")
    public ResponseEntity<List<ExerciseLog>> exerciseLogRecord(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(exerciseLogService.findExerciseLogByUserId(userId));
    }

    @GetMapping("/myexercise/likes/{userId}")
    public ResponseEntity<List<ExerciseLog>> exerciseLogLikes(@PathVariable("userId")Long userId){
        return ResponseEntity.ok(exerciseLogService.findExerciseLogByLikes(userId));
    }
}
