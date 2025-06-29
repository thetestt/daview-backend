package com.daview.controller;

import com.daview.dto.ChatReadDTO;
import com.daview.service.ChatMessageService;
import com.daview.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
   
    public ChatWebSocketController(ChatRoomService chatRoomService, ChatMessageService chatMessageService) {
        this.chatRoomService = chatRoomService;
        this.chatMessageService = chatMessageService;
    }

    // 구독 요청이 들어올 때 자동 호출됨
    @SubscribeMapping("/chat/room/{chatroomId}")
    public void validateSubscription(
            @DestinationVariable String chatroomId,
            @Header("memberId") Long memberId
    ) {
        boolean allowed = chatRoomService.isUserInChatroom(chatroomId, memberId);

        if (!allowed) {
            System.out.println("🚫 구독 차단됨: " + chatroomId + ", memberId=" + memberId);
            throw new IllegalArgumentException("이 채팅방에 접근 권한이 없습니다.");
        }

        System.out.println("✅ 구독 허용됨: " + chatroomId + ", memberId=" + memberId);
    }
    

//    @MessageMapping("/read") // 프론트에서 /pub/read 로 보내면 여기 도달
//    public void handleReadMessage(ChatReadDTO dto) {
//        System.out.println("📩 읽음 처리 요청 수신: " + dto);
//        chatMessageService.markMessagesAsRead(dto.getChatroomId(), dto.getReaderId());
//    }
}
