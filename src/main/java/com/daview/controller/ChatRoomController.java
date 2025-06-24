package com.daview.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daview.dto.ChatRoomDTO;
import com.daview.service.ChatRoomService;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("/rooms")
    public List<ChatRoomDTO> getChatRooms(@RequestParam Long memberId) {
        return chatRoomService.getChatRoomListForUser(memberId);
    }
    
    //쳇방 들어가서 아이디 검증하기 수정테스트250623 06:49
    @GetMapping("/rooms/{chatroomId}/validate")
    public ResponseEntity<?> validateUserInChatRoom(
            @PathVariable String chatroomId,
            @RequestParam Long memberId) {

        boolean isMember = chatRoomService.isUserInChatRoom(chatroomId, memberId);

        if (isMember) {
            return ResponseEntity.ok().body(Map.of("success", true, "message", "접근 허용"));
        } else {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN) // 403
                .body(Map.of("success", false, "message", "채팅방 접근 권한이 없습니다."));
        }
    }

    
    @GetMapping("/rooms/{chatroomId}/info")
    public ChatRoomDTO getSingleChatRoomInfo(
        @PathVariable String chatroomId,
        @RequestParam Long memberId
    ) {
        return chatRoomService.getChatRoomInfo(chatroomId, memberId);
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

        
        String existingRoomId = chatRoomService.findExistingRoom(senderId, receiverId, facilityId);

        if (existingRoomId != null) {
            return Map.of("chatroomId", existingRoomId);
        }

        System.out.println("✅ ChatRoomController에서 createRoom 호출 예정");

        String newRoomId = chatRoomService.createRoom(senderId, receiverId, facilityId);
        return Map.of("chatroomId", newRoomId);
    }
    
    
    @PutMapping("/rooms/{chatroomId}/exit")
    public ResponseEntity<?> exitChatRoom(
            @PathVariable String chatroomId,
            @RequestBody Map<String, Object> payload
    ) {
        Long memberId = Long.parseLong(payload.get("memberId").toString());
        chatRoomService.exitChatRoom(chatroomId, memberId);
        return ResponseEntity.ok(Map.of("success", true));
    }
    

    
}
