package com.ssafy.woonana.domain.repository.participation;

import com.ssafy.woonana.domain.model.entity.participation.Participation;
import com.ssafy.woonana.domain.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    List<Participation> findParticipationsByUser(User user); // user에 해당하는 참여들 리턴

    List<Participation> findListByBoardId(Long boardId);

    @Query("select p from Participation p where p.board.id = :boardId and p.user.userId = :userId")
    Participation findParticipation(@Param("boardId") Long boardId, @Param("userId") Long userId);

}
