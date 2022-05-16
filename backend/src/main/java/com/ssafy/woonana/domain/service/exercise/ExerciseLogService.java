package com.ssafy.woonana.domain.service.exercise;

import com.ssafy.woonana.domain.model.dto.exercise.request.ExerciseLogRequest;
import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLog;
import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLogDate;
import com.ssafy.woonana.domain.model.dto.exercise.response.ExerciseLogResponse;
import com.ssafy.woonana.domain.model.entity.board.Board;
import com.ssafy.woonana.domain.model.entity.exercise.Exercise;
import com.ssafy.woonana.domain.model.entity.user.User;
import com.ssafy.woonana.domain.repository.board.BoardRepository;
import com.ssafy.woonana.domain.repository.board.ExerciseRepository;
import com.ssafy.woonana.domain.repository.exercise.ExerciseLogRepository;
import com.ssafy.woonana.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseLogService {

    private final ExerciseLogRepository exerciseLogRepository;

    private final ExerciseRepository exerciseRepository;

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    @Transactional
    public List<ExerciseLogResponse> findExerciseLogByMonth(int userId) {
        return exerciseLogRepository.findExerciseLogByMonth(userId);
    }

    @Transactional
    public List<ExerciseLog> findExerciseLogByUserId(Long userId){
        return exerciseLogRepository.findExerciseLogByUserId(userId);
    }

    @Transactional
    public List<ExerciseLogDate> findExerciseLogByLikes(Long userId){
        return exerciseLogRepository.findExerciseLogByLikes(userId);
    }

    @Transactional
    public Long register(Long boardId, Long exerciseId, Long userId){
        Board exerciseLogBoardId = boardRepository.findById(boardId).get();
        Exercise exerciseLogExerciseId = exerciseRepository.findById(exerciseId).get();
        User exerciseLogUser = userRepository.findById(userId).get();

        com.ssafy.woonana.domain.model.entity.exercise.ExerciseLog exerciseLog = new com.ssafy.woonana.domain.model.entity.exercise.ExerciseLog(exerciseLogBoardId, exerciseLogExerciseId, exerciseLogUser);
        exerciseLogRepository.save(exerciseLog);

        return exerciseLog.getExercise_log_id();
    }
}