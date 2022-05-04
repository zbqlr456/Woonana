package com.ssafy.woonana.domain.model.dto.user.response;

import java.time.LocalDateTime;

public interface UserParticipateLog {
    Long getBoardId();
    LocalDateTime getMeetStartDate();
    String getExerciseName();
}
