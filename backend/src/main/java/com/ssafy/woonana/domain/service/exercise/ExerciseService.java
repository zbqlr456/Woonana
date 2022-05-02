package com.ssafy.woonana.domain.service.exercise;

import com.ssafy.woonana.domain.model.dto.exercise.request.ExerciseRequest;
import com.ssafy.woonana.domain.model.entity.exercise.Exercise;
import com.ssafy.woonana.domain.repository.board.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExerciseService {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Transactional
    public void register(ExerciseRequest exerciseRequest) {
        String[] exerciseArray = exerciseRequest.getExerciseNames();

        for (int i = 0; i < exerciseArray.length; i++) {
            Exercise exercise = new Exercise(exerciseArray[i]);
            Exercise findOne = exerciseRepository.findExerciseByName(exercise.getName());
            // 등록된 적 없는 운동만 등록하기
            if (findOne == null) exerciseRepository.save(exercise);
        }
    }
}
