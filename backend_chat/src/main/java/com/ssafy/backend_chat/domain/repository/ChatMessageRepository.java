package com.ssafy.backend_chat.domain.repository;

import com.ssafy.backend_chat.domain.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findByRoomIdOrderByCreatedAt(String roomId);

}