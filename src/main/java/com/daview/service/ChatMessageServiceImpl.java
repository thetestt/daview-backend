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
    public void saveMessage(ChatMessageDTO message) {
        // ISO → MySQL DATETIME 변환
        LocalDateTime parsedDateTime = LocalDateTime.ofInstant(
            Instant.parse(message.getSentAt()), ZoneId.of("Asia/Seoul")
        );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = parsedDateTime.format(formatter);
        message.setSentAt(formatted);

        // ✅ 1. 메시지 저장
        chatMessageMapper.insertChatMessage(message);

        // ✅ 2. 채팅방 정보 업데이트
//        chatRoomMapper.updateLastMessage(message.getChatroomId(), message.getContent(), formatted);

        // ✅ 3. 채팅방 안 (ChatWindow) 실시간 전송
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getChatroomId(), message);

        // ✅ 4. 채팅방 리스트(ChatList) 실시간 업데이트
        // 1) 보낸 사람 기준 ChatRoomDTO (단일)
        ChatRoomDTO updatedSenderRoom = chatRoomMapper.getChatRoomInfoForList(message.getSenderId(), message.getChatroomId());
        messagingTemplate.convertAndSend("/sub/chat/roomList/" + message.getSenderId(), updatedSenderRoom);

        // 2) 받는 사람 ID 추출
        Long opponentId = chatRoomMapper.getOpponentId(message.getChatroomId(), message.getSenderId());
        if (opponentId != null) {
            ChatRoomDTO updatedReceiverRoom = chatRoomMapper.getChatRoomInfoForList(opponentId, message.getChatroomId());
            messagingTemplate.convertAndSend("/sub/chat/roomList/" + opponentId, updatedReceiverRoom);
        }
    }

    @Override
    public List<ChatMessageDTO> getMessagesByRoom(String chatroomId) {
        return chatMessageMapper.getMessagesByRoom(chatroomId);
    }
}