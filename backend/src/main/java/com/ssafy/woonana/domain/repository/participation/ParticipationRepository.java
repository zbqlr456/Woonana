package com.ssafy.woonana.domain.repository.participation;

import com.ssafy.woonana.domain.model.entity.participation.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findListByBoardId(Long boardId);
}
