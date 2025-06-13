package com.daview.controller;

import com.daview.dto.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat/send") // /pub/chat/send
    @SendTo("/sub/chat/room")     // 구독자에게 메시지 전송
    public ChatMessageDTO sendMessage(ChatMessageDTO message) {
        return message; // 현재는 단순 echo (다음 단계에서 Kafka로 연결 예정)
    }
}
