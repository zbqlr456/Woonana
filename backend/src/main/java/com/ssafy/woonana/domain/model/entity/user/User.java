package com.ssafy.woonana.domain.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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

    @Column
    private String accessToken; // 카카오 로그인에 필요한 액세스 토큰

}
