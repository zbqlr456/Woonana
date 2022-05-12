package com.ssafy.woonana.domain.repository.evaluation;

import com.ssafy.woonana.domain.model.dto.user.response.UserEvaluateResponse;
import com.ssafy.woonana.domain.model.entity.evaluation.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query(value = "select e from Evaluation e where e.evaluationUser.userId=:userId")
    List<Evaluation> findEvaluationsByEvaluationUser(Long userId);
}
