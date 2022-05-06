package com.ssafy.woonana.domain.repository.evaluation;

import com.ssafy.woonana.domain.model.entity.evaluation.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    List<Evaluation> findEvaluationsByEvaluationUser(Long userId); // userId로 작성한 평가들 리턴
}
