package com.ssafy.woonana.domain.service.board;

import com.ssafy.woonana.domain.model.dto.board.request.BoardRequest;
import com.ssafy.woonana.domain.model.entity.board.Board;
import com.ssafy.woonana.domain.model.entity.exercise.Exercise;
import com.ssafy.woonana.domain.model.entity.user.User;
import com.ssafy.woonana.domain.repository.board.BoardRepository;
import com.ssafy.woonana.domain.repository.board.ExerciseRepository;
import com.ssafy.woonana.domain.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public Long registBoard(BoardRequest boardRequest1) {
        Exercise exerciseRequest1 = exerciseRepository.findById(boardRequest1.getExerciseId()).get();
        User u1 = userRepository.findById(boardRequest1.getUserId()).get();
        Board board = new Board(boardRequest1.getTitle(), boardRequest1.getContent(), boardRequest1.getPlace(), boardRequest1.getMeetStartDate(), boardRequest1.getMeetEndDate(), boardRequest1.getMaxNumber(), exerciseRequest1, u1);
        boardRepository.save(board);
        System.out.println("==================");
        System.out.println("board = " + board);
        return board.getId();
    }

}
