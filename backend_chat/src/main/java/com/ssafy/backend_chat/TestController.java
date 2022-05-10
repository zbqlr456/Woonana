package com.ssafy.backend_chat;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("chatapi/test")
public class TestController {

    @GetMapping("/urltest")
    public ResponseEntity<String> getMsgForTest(){
        return ResponseEntity.ok().body("woonana 백엔드 >> 채팅 호출");
    }
}
