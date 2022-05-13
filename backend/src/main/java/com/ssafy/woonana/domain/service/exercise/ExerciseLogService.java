package com.ssafy.woonana.domain.service.exercise;

import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLog;
import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLogResponse;
import com.ssafy.woonana.domain.repository.exercise.ExerciseLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ExerciseLogService {

    @Autowired
    private ExerciseLogRepository exerciseLogRepository;

    @Transactional
    public List<ExerciseLogResponse> findExerciseLogByMonth(int userId) {return exerciseLogRepository.findExerciseLogByMonth(userId);}

    @Transactional
    public List<ExerciseLog> findExerciseLogByUserId(Long userId){
        return exerciseLogRepository.findExerciseLogByUserId(userId);
    }

    @Transactional
    public List<ExerciseLog> findExerciseLogByLikes(Long userId){
        return exerciseLogRepository.findExerciseLogByLikes(userId);
    }
}