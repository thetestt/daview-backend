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
        System.out.println("💬 POST BODY: " + req);

        String memberIdStr = req.get("memberId");
        String receiverIdStr = req.get("receiverId");
        String facilityId = req.get("facilityId");
        System.out.println("✅ Controller에서 facilityId: " + facilityId + " / type: " + facilityId.getClass().getName());

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

        // ❗ 여기를 수정
        String existingRoomId = chatRoomService.findExistingRoom(senderId, receiverId, facilityId);

        if (existingRoomId != null) {
            return Map.of("chatroomId", existingRoomId);
        }

        System.out.println("✅ ChatRoomController에서 createRoom 호출 예정");

        String newRoomId = chatRoomService.createRoom(senderId, receiverId, facilityId);
        return Map.of("chatroomId", newRoomId);
    }
    
}
