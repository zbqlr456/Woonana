package com.ssafy.woonana.domain.model.entity.exercise;

import com.ssafy.woonana.domain.model.entity.board.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exercise {

    @Id
    @GeneratedValue
    @Column(name = "exercise_id")
    private Long id;

    @Column(name = "exercise_name")
    private String name;

    @OneToMany(mappedBy = "exercise")
    private List<Board> boards = new ArrayList<>();

    public Exercise(String name) {
        this.name = name;
    }
}
