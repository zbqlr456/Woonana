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

    private Long userId;
    private int rating;

    @Builder
    public UserEvaluateResponse(Evaluation eval){
        this.userId=eval.getEvaluationTarget().getUserId();
        this.rating=eval.getEvaluationRatingScore();
    }

}
