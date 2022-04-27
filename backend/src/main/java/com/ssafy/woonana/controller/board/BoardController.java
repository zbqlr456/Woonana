package com.ssafy.woonana.controller.board;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}