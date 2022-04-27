package com.ssafy.woonana.controller.chat;

import com.ssafy.woonana.domain.model.dto.chat.request.ChatRoom;
import com.ssafy.woonana.domain.repository.chat.ChatRoomRepository;
import com.ssafy.woonana.domain.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatService chatService;

    /**
     * View 모델 생성
     *
     * @param model
     * @return
     */
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    /**
     * 채팅방 전제 조회
     *
     * @return
     */
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatService.findAllRooms();
    }

    /**
     * 채팅방 생성
     *
     * @param name
     * @return
     */
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    /**
     * 채팅방 입장 모델 생성
     *
     * @param model
     * @param roomId
     * @return
     */
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomDetail";
    }

    /**
     * 채팅방 입장
     *
     * @param roomId
     * @return
     */
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatService.findRoomById(roomId);
    }
}
