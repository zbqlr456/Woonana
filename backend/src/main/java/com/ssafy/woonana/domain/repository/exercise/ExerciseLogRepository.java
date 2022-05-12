package com.ssafy.woonana.domain.repository.exercise;

import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLogCountResponse;
import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLogResponse;
import com.ssafy.woonana.domain.model.entity.exercise.ExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {

    @Query(value = "select * from exercise_log", nativeQuery = true)
    List<ExerciseLogResponse> findExerciseLogByMonth(@Param("userId") int userId);

    @Query(value = "select el.exercise_id as exercise_id, e.exercise_name as exercise_name, el.exercise_count as exercise_count from exercise_log el" +
            "join exercise e on el.exercise_id = e.exercise_id where el.user_id = :userId", nativeQuery = true)
    List<ExerciseLogResponse> findExerciseLogByUserId(@Param("userId")int userId);

    @Query(value = "select el.exercise_id as exercise_id, e.exercise_name as exercise_name, el.exercise_count as exercise_count from exercise_log el" +
            "join exercise e on el.exercise_id = e.exercise_id where el.user_id = :userId", nativeQuery = true)
    List<ExerciseLogResponse> findExerciseLogByLikes(@Param("userId")int userId);

    @Query(value="select el.exercise_id, count(*) as count from exercise_log el " +
            "where exercise_id=:exerciseId and el.user_id=:userId " +
            "group by el.exercise_id", nativeQuery = true)
    List<ExerciseLogCountResponse> findExerciseCountByUser(@Param("exerciseId") Long exerciseId, @Param("userId") Long userId);

}
