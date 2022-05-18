package com.ssafy.backend_chat.domain.dto;

import com.ssafy.backend_chat.domain.entity.ChatRoom;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatRoomDto {

    private String name;
    private Long boardId;

    public ChatRoom toEntity(){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoom.setBoardId(boardId);
        chatRoom.setCreatedAt(LocalDateTime.now());
        return chatRoom;
    }
}