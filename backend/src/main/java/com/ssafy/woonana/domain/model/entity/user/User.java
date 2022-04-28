package com.ssafy.woonana.domain.model.entity.user;

import com.ssafy.woonana.domain.model.entity.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
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

    @OneToMany(mappedBy = "user")
    private List<Board> userBoards = new ArrayList<>();

    public User(Long userId, String userEmail, String userBirthday, String userNickname, String userSex, String userProfileUrl) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userBirthday = userBirthday;
        this.userNickname = userNickname;
        this.userSex = userSex;
        this.userProfileUrl = userProfileUrl;
    }
}
