package com.ssafy.woonana.domain.service.chat;

import com.ssafy.woonana.domain.model.dto.chat.request.ChatRoom;
import com.ssafy.woonana.domain.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    public List<ChatRoom> findAllRooms(){
        List<ChatRoom> rooms = chatRoomRepository.findAllRoom();
        return rooms;
    }

    public ChatRoom createRoom(String name){
        ChatRoom newRoom = chatRoomRepository.createChatRoom(name);
        return newRoom;
    }

    public ChatRoom findRoomById(String roomId){
        ChatRoom getRoom = chatRoomRepository.findRoomById(roomId);
        return getRoom;
    }

}
