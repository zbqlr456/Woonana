package com.ssafy.backend_chat.domain.repository;


import com.ssafy.backend_chat.domain.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom,String> {

    List<ChatRoom> findAllBy();
    ChatRoom findByRoomId(String roomId);
}
