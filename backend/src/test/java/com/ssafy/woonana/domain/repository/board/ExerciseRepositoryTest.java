package com.ssafy.woonana.domain.repository.board;

import com.ssafy.woonana.domain.model.entity.exercise.Exercise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ExerciseRepositoryTest {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Test
    public void save() {
        Exercise e1 = new Exercise("탁구", "");
        Exercise e2 = new Exercise("캐치볼", "");
        Exercise e3 = new Exercise("산책", "");

        exerciseRepository.save(e1);
        exerciseRepository.save(e2);
        exerciseRepository.save(e3);

        Exercise findExercise = exerciseRepository.findById(e2.getId()).get();

        assertThat(exerciseRepository.findAll().size()).isEqualTo(3);
        assertThat(findExercise.getName()).isEqualTo("캐치볼");
    }

}