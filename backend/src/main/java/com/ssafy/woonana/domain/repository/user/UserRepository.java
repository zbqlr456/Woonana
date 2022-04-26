package com.ssafy.woonana.domain.repository.user;

import com.ssafy.woonana.domain.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
