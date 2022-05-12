package com.ssafy.backend_chat.domain.dto;


import com.ssafy.backend_chat.domain.entity.ChatRoom;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatRoomDto {

    private String name;

    public ChatRoom toEntity(){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        chatRoom.setCreatedAt(LocalDateTime.now());
        return chatRoom;
    }
}