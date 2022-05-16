package com.ssafy.woonana.domain.repository.exercise;

import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLogCountResponse;
import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLog;
import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLogDate;
import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLogResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseLogRepository extends JpaRepository<com.ssafy.woonana.domain.model.entity.exercise.ExerciseLog, Long> {

    @Query(value = "select * from exercise_log", nativeQuery = true)
    List<ExerciseLogResponse> findExerciseLogByMonth(@Param("userId") int userId);

    @Query("select el.exercise.id as exerciseId, e.name as exerciseName, count(el.exercise.id) as exerciseCount from ExerciseLog el " +
            "join Exercise e on el.exercise.id = e.id where el.user.userId = :userId group by el.exercise.id ")
    List<ExerciseLog> findExerciseLogByUserId(@Param("userId")Long userId);

    @Query("select el.exercise.id as exerciseId, function('date_format', b.meetEndDate, '%y-%m-%d') as endDate from ExerciseLog el " +
            "join Board b on el.user.userId = b.user.userId where el.user.userId = :userId ")
    List<ExerciseLogDate> findExerciseLogByLikes(@Param("userId")Long userId);

    @Query(value="select el.exercise_id, count(*) as count from exercise_log el " +
            "where exercise_id=:exerciseId and el.user_id=:userId " +
            "group by el.exercise_id", nativeQuery = true)
    List<ExerciseLogCountResponse> findExerciseCountByUser(@Param("exerciseId") Long exerciseId, @Param("userId") Long userId);

}
