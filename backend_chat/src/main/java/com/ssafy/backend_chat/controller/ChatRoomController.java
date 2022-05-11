package com.ssafy.backend_chat.controller;

import com.ssafy.backend_chat.domain.dto.ChatRoomDto;
import com.ssafy.backend_chat.domain.entity.ChatRoom;
import com.ssafy.backend_chat.domain.repository.ChatRoomRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "채팅방")
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/rooms")
    @ResponseBody
    @ApiOperation(value = "채팅방 전체 조회")
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllBy();
    }

    @PostMapping("/room")
    @ApiOperation(value = "채팅방 개설")
    public void createRoom(@RequestBody ChatRoomDto chatRoomDto) {
        chatRoomRepository.save(chatRoomDto.toEntity());
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    @ApiOperation(value = "채팅방 이름 조회")
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findByRoomId(roomId);
    }

}
