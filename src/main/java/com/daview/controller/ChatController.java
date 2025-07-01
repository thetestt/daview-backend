package com.daview.controller;

import com.daview.dto.ChatMessageDTO;
import com.daview.dto.ReadMessageDTO;
import com.daview.service.ChatMessageService;
import com.daview.service.ChatRoomService;
import com.daview.service.KafkaChatProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ChatController {

    @Autowired
    private KafkaChatProducer kafkaChatProducer;
    
    @Autowired
    private ChatMessageService chatMessageService;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private ChatRoomService chatRoomService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @MessageMapping("/send") // pub/chat/send
    public void send(ChatMessageDTO message) {
        kafkaChatProducer.sendMessage(message);
        try {
            log.info("✅ Kafka로 보낼 메시지: {}", objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            log.error("❌ 메시지 JSON 변환 실패", e);
        }
        // WebSocket으로는 이제 응답 안 보내고 Kafka → Redis → DB → 구독 구조로 전환
    }
    
    
    //채팅방에 과저 메세지 넣기
    @GetMapping("/rooms/{chatroomId}/messages")
    public List<ChatMessageDTO> getMessages(@PathVariable String chatroomId,
    	    @RequestParam Long memberId) {
        return chatMessageService.getMessagesByRoom(chatroomId, memberId); // ✅ 인스턴스로 호출
    }
    
   
    @GetMapping("/rooms/{chatroomId}/verify")
    public Map<String, Boolean> verifyAccess(
            @PathVariable String chatroomId,
            @RequestParam Long memberId) {

        boolean allowed = chatRoomService.isUserInChatroom(chatroomId, memberId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("allowed", allowed);
        return response;
    }
    
//    //채팅 읽음처리하는 컨트롤러
//    @PostMapping("/{chatroomId}/read")
//    public ResponseEntity<Void> markMessagesAsRead(
//            @PathVariable String chatroomId,
//            @RequestParam Long memberId
//    ) {
//        chatMessageService.markMessagesAsRead(chatroomId, memberId);
//        return ResponseEntity.ok().build();
//    }
    
    
    // ✅ 읽음 처리 WebSocket 수신 처리
    @MessageMapping("/read")
    public void handleRead(@Payload ReadMessageDTO dto) {

        // 1. DB에 읽음 처리
        chatMessageService.markMessagesAsRead(dto.getChatroomId(), dto.getReaderId());
        
     // 2. 지금 읽힌 메시지 ID들을 조회
        List<Long> readMessageIds = chatMessageService
            .findUnreadMessageIdsSentByOpponent(dto.getChatroomId(), dto.getReaderId());

        // 3. DTO에 세팅
        dto.setChatMessageIds(readMessageIds);


        // 2. 상대방에게 읽었음을 알림
        messagingTemplate.convertAndSend("/sub/chat/read/" + dto.getChatroomId(), dto);

    }
    
}