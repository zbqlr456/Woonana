package com.ssafy.woonana.domain.model.dto.user.response;

import com.ssafy.woonana.domain.model.entity.evaluation.Evaluation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEvaluateResponse {

    private Long userId; // 평가 받은 사람
    private int rating; // 평가 점수

    public UserEvaluateResponse(Evaluation eval){
        this.userId=eval.getEvaluationTarget().getUserId(); // 평가 받은 사람
        this.rating=eval.getEvaluationRatingScore();
    }

}
