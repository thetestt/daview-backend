package com.daview.service;

import com.daview.dto.ChatMessageDTO;
import com.daview.dto.ChatRoomDTO;
import com.daview.mapper.ChatMessageMapper;
import com.daview.mapper.ChatRoomMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageMapper chatMessageMapper;
    private final ChatRoomMapper chatRoomMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public ChatMessageDTO saveMessage(ChatMessageDTO message) {
        // ISO → MySQL DATETIME 변환
        LocalDateTime parsedDateTime = LocalDateTime.ofInstant(
            Instant.parse(message.getSentAt()), ZoneId.of("Asia/Seoul")
        );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = parsedDateTime.format(formatter);
        message.setSentAt(formatted);

        // ✅ ✉️ 읽지 않은 상태로 기본 설정
        message.setIsRead(false);

        // ✅ 1. 메시지 저장
        chatMessageMapper.insertChatMessage(message); // 이때 message.chatMessageId 가 채워져야 함 (MyBatis <selectKey>)

        // ✅ 2. 채팅방 정보 업데이트 (주석은 상황 따라 복구 가능)
        // chatRoomMapper.updateLastMessage(message.getChatroomId(), message.getContent(), formatted);

        // ✅ 3. 채팅방 안 (ChatWindow) 실시간 전송
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getChatroomId(), message);
        System.out.println("🔹 메시지 WebSocket 전송 (ChatWindow): " + message.getContent());

        // ✅ 4. 채팅방 리스트(ChatList) 실시간 업데이트
        ChatRoomDTO updatedSenderRoom = chatRoomMapper.getChatRoomInfoForList(
            message.getSenderId(), message.getChatroomId());
        messagingTemplate.convertAndSend("/sub/chat/roomList/" + message.getSenderId(), updatedSenderRoom);

        Long opponentId = chatRoomMapper.getOpponentId(message.getChatroomId(), message.getSenderId());
        if (opponentId != null) {
            ChatRoomDTO updatedReceiverRoom = chatRoomMapper.getChatRoomInfoForList(
                opponentId, message.getChatroomId());
            messagingTemplate.convertAndSend("/sub/chat/roomList/" + opponentId, updatedReceiverRoom);
        }

        return message; // ✅ 저장된 message (chatMessageId 포함)를 반환
    }
//메세지 불러오기
    @Override
    public List<ChatMessageDTO> getMessagesByRoom(String chatroomId, Long memberId) {
        return chatMessageMapper.getMessagesByRoom(chatroomId, memberId);
    }
    
  //채팅 읽음처리
    @Override
    public void markMessagesAsRead(String chatroomId, Long memberId) {
    	System.out.println("✅ 읽음 처리 요청: chatroomId = " + chatroomId + ", memberId = " + memberId);
        chatMessageMapper.markMessagesAsRead(chatroomId, memberId);
    }
    
    @Override
    public List<Long> findUnreadMessageIdsSentByOpponent(String chatroomId, Long readerId) {
        return chatMessageMapper.findUnreadMessageIdsByChatroomAndOpponent(chatroomId, readerId);
    }
}