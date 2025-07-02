package com.daview.controller.admin;

import com.daview.dto.ChatRoomDTO;
import com.daview.dto.ChatMessageDTO;
import com.daview.service.ChatRoomService;
import com.daview.service.ChatMessageService;
import com.daview.mapper.ChatRoomMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/inquiries")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AdminInquiryController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatRoomMapper chatRoomMapper;

    public AdminInquiryController(ChatRoomService chatRoomService, ChatMessageService chatMessageService, ChatRoomMapper chatRoomMapper) {
        this.chatRoomService = chatRoomService;
        this.chatMessageService = chatMessageService;
        this.chatRoomMapper = chatRoomMapper;
    }

    /**
     * 모든 1:1 문의(채팅방) 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<ChatRoomDTO>> getAllInquiries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status) {
        
        try {
            List<ChatRoomDTO> inquiries = chatRoomService.getAllChatRoomsForAdmin(page, size, search, status);
            return ResponseEntity.ok(inquiries);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 특정 채팅방의 메시지 목록 조회
     */
    @GetMapping("/{chatroomId}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getChatMessages(
            @PathVariable String chatroomId,
            @RequestParam Long adminId) {
        
        try {
            // 관리자 권한 확인 로직은 추후 추가
            List<ChatMessageDTO> messages = chatMessageService.getMessagesByRoom(chatroomId, adminId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 문의 상태 변경
     */
    @PutMapping("/{chatroomId}/status")
    public ResponseEntity<?> updateInquiryStatus(
            @PathVariable String chatroomId,
            @RequestBody Map<String, String> request) {
        
        try {
            String status = request.get("status");
            chatRoomService.updateChatRoomStatus(chatroomId, status);
            return ResponseEntity.ok(Map.of("success", true));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 관리자 답변 전송
     */
    @PostMapping("/{chatroomId}/reply")
    public ResponseEntity<?> sendAdminReply(
            @PathVariable String chatroomId,
            @RequestBody Map<String, Object> request) {
        
        try {
            // JWT에서 실제 로그인한 관리자 ID를 가져오는 것이 더 안전함
            // 현재는 요청에서 받은 adminId 사용
            Long adminId = Long.parseLong(request.get("adminId").toString());
            String content = request.get("content").toString();
            
            // 상대방 ID 가져오기 (채팅방에서 관리자가 아닌 사용자)
            Long receiverId = chatRoomMapper.getOpponentId(chatroomId, adminId);
            
            // 현재 시간을 ISO-8601 형식으로 설정 (ChatMessageServiceImpl에서 Instant.parse() 사용)
            String currentTime = LocalDateTime.now()
                .atZone(java.time.ZoneId.of("Asia/Seoul"))
                .toInstant()
                .toString();
            
            ChatMessageDTO message = new ChatMessageDTO();
            message.setChatroomId(chatroomId);
            message.setSenderId(adminId);
            message.setReceiverId(receiverId);  // 수신자 ID 설정
            message.setContent(content);
            message.setSentAt(currentTime);     // 전송 시간 설정
            message.setIsRead(false);
            
            ChatMessageDTO savedMessage = chatMessageService.saveMessage(message);
            
            System.out.println("✅ 관리자 메시지 전송 완료: " + chatroomId + " -> " + content);
            
            return ResponseEntity.ok(Map.of("success", true, "message", savedMessage));
        } catch (Exception e) {
            System.err.println("❌ 관리자 메시지 전송 실패: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
} 