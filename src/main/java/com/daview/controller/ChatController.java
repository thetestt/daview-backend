package com.daview.controller;

import com.daview.dto.ChatMessageDTO;
import com.daview.service.KafkaChatProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private KafkaChatProducer kafkaChatProducer;

    @MessageMapping("/chat/send") // pub/chat/send
    public void send(ChatMessageDTO message) {
        kafkaChatProducer.sendMessage(message);
        // WebSocket으로는 이제 응답 안 보내고 Kafka → Redis → DB → 구독 구조로 전환
    }
}