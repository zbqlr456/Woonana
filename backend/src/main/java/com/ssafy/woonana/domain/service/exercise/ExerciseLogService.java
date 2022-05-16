package com.ssafy.woonana.domain.service.exercise;

import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLog;
import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLogByMonthResponse;
import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLogResponse;
import com.ssafy.woonana.domain.repository.exercise.ExerciseLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExerciseLogService {

    @Autowired
    private ExerciseLogRepository exerciseLogRepository;

    @Transactional
    public ExerciseLogByMonthResponse findExerciseLogByMonth(Long userId) {
        LocalDateTime monthStartDate = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), 1, 0,0,0);
        LocalDateTime monthEndDate = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().plusMonths(1).getMonth(), 1, 0,0,0);
//        System.out.println("monthStartDate: "+monthStartDate);
//        System.out.println("monthEndDate: "+monthEndDate);

        List<ExerciseLogResponse> monthExerciseList = exerciseLogRepository.findExerciseLogByMonth(userId, monthStartDate, monthEndDate);
        int excMonthCount=0;

        for(ExerciseLogResponse resp: monthExerciseList){
            excMonthCount+=resp.getExerciseCount();
        }

        int count = exerciseLogRepository.countExerciseLogsByUser(userId);
        ExerciseLogByMonthResponse result = new ExerciseLogByMonthResponse(count, excMonthCount, monthExerciseList);
        return result;
    }

    @Transactional
    public List<ExerciseLog> findExerciseLogByUserId(Long userId){
        return exerciseLogRepository.findExerciseLogByUserId(userId);
    }

    @Transactional
    public List<ExerciseLog> findExerciseLogByLikes(Long userId){
        return exerciseLogRepository.findExerciseLogByLikes(userId);
    }
}