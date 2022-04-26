package com.ssafy.woonana.domain.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private Long userId;

    @Column
    private String userEmail;

    @Column
    private String userNickname;

    @Column
    private String userBirthday;

    @Column
    private String userSex;

    @Column
    private int userRatingScore;

    @Column
    private String userProfileUrl;

}
