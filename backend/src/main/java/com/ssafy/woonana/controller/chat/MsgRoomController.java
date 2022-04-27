package com.ssafy.woonana.controller.chat;

import com.ssafy.woonana.domain.model.entity.chat.MsgRoom;
import com.ssafy.woonana.domain.service.chat.MsgService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comm")
public class MsgRoomController {

    private final MsgService msgService;

    @GetMapping("/room")
    public String room(Model model) {
        return "/comm/room";
    }

    @GetMapping("/rooms")
    public List<MsgRoom> rooms() {
        return msgService.findAllRoom();
    }

    @ApiOperation(value = "방 입장", notes = "room ID를 통해서 방에 입장합니다.")
    @GetMapping("/room/enter/{roomId}")
    public String roomEnter(Model model, @ApiParam(value = "방 ID", required = true) @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "comm/roomEnter";
    }

    @ApiOperation(value = "방 조회", notes = "room ID를 통해서 방을 조회합니다.")
    @GetMapping("/room/{roomId}")
    public MsgRoom roomInfo(@ApiParam(value = "방 ID", required = true) @PathVariable String roomId) {
        return msgService.findById(roomId);
    }

    @PostMapping("/room")
    @ResponseBody
    public MsgRoom createRoom(@RequestParam String name) {
        return msgService.createRoom(name);
    }
}
