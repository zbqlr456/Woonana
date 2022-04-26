package com.ssafy.woonana.controller.chat;

import com.ssafy.woonana.domain.model.dto.chat.request.MsgRoom;
import com.ssafy.woonana.domain.service.chat.MsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class MsgController {

    private final MsgService msgService;

    @PostMapping
    public MsgRoom createRoom(@RequestParam String name){
        return msgService.createRoom(name);
    }

    @GetMapping
    public List<MsgRoom> findAllRoom(){
        return msgService.findAllRoom();
    }

}
