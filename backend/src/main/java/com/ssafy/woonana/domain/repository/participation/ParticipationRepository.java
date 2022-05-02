package com.ssafy.woonana.domain.repository.participation;

import com.ssafy.woonana.domain.model.entity.participation.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}
