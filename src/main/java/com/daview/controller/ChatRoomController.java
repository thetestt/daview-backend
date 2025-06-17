package com.daview.controller;

import com.daview.dto.ChatRoomDTO;

import com.daview.service.ChatRoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("/rooms")
    public List<ChatRoomDTO> getChatRooms(@RequestParam int memberId) {
        return chatRoomService.getChatRooms(memberId);
    }
    

    
    @PostMapping("/rooms/check-or-create")
    public Map<String, String> checkOrCreateRoom(@RequestBody Map<String, String> req) {
        System.out.println("💬 POST BODY: " + req); // 요청 확인 로그

        String memberIdStr = req.get("memberId");
        String receiverIdStr = req.get("receiverId");

        if (memberIdStr == null || receiverIdStr == null) {
            throw new IllegalArgumentException("memberId 또는 receiverId가 null입니다.");
        }

        Long senderId;
        Long receiverId;
        try {
            senderId = Long.valueOf(memberIdStr);
            receiverId = Long.valueOf(receiverIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("memberId 또는 receiverId가 숫자 형식이 아닙니다.");
        }

        String existingRoomId = chatRoomService.findExistingRoom(senderId, receiverId);
        if (existingRoomId != null) {
            return Map.of("chatroomId", existingRoomId);
        }

        String newRoomId = chatRoomService.createRoom(senderId, receiverId);
        return Map.of("chatroomId", newRoomId);
    }
    
}
