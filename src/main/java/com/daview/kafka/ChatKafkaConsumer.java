package com.daview.kafka;

import com.daview.dto.ChatMessageDTO;
import com.daview.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void listen(String messageJson) {
        try {
            ChatMessageDTO message = objectMapper.readValue(messageJson, ChatMessageDTO.class);

            // ✅ DB에 저장하고 저장된 메시지로 다시 받음
            ChatMessageDTO saved = chatMessageService.saveMessage(message);

            // ✅ WebSocket으로 저장된 메시지를 보냄 (ID 포함됨)
            messagingTemplate.convertAndSend(
                "/sub/chat/room/" + saved.getChatroomId(),
                saved
            );
            
         // ✅ 채팅방 리스트 갱신 알림도 추가!
            messagingTemplate.convertAndSend(
                "/sub/chat/roomList/" + saved.getReceiverId(),
                "new"
            );

        } catch (Exception e) {
            log.error("❌ Kafka Consumer 오류", e);
        }
    }
}