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

            // ✅ DB에 저장
            chatMessageService.saveMessage(message);

            // ✅ WebSocket으로 브로드캐스트 (채팅방 별 경로로 전송)
            messagingTemplate.convertAndSend(
                "/sub/chat/room/" + message.getChatroomId(),
                message
            );

        } catch (Exception e) {
            log.error("❌ Kafka Consumer 오류", e);
        }
    }
}