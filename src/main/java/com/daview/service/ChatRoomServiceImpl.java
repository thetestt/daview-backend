package com.daview.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.daview.dto.ChatMessageDTO;
import com.daview.dto.ChatRoomDTO;
import com.daview.mapper.CaregiverMapper;
import com.daview.mapper.ChatMessageMapper;
import com.daview.mapper.ChatRoomMapper;
import com.daview.mapper.FacilityMapper;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomMapper chatRoomMapper;
    private final ChatMessageMapper chatMessageMapper;
    private final FacilityMapper facilityMapper;
    private final KafkaChatProducer kafkaChatProducer;
    private final CaregiverMapper caregiverMapper;

    public ChatRoomServiceImpl(ChatRoomMapper chatRoomMapper,
                               ChatMessageMapper chatMessageMapper,
                               FacilityMapper facilityMapper,
                               CaregiverMapper caregiverMapper,
                               KafkaChatProducer kafkaChatProducer) {
        this.chatRoomMapper = chatRoomMapper;
        this.chatMessageMapper = chatMessageMapper;
        this.facilityMapper = facilityMapper;
        this.caregiverMapper = caregiverMapper;
        this.kafkaChatProducer = kafkaChatProducer;
    }

    // ✅ 유저가 속한 채팅방 리스트 가져오기
    @Override
    public List<ChatRoomDTO> getChatRoomListForUser(Long memberId) {
        return chatRoomMapper.getChatRoomListForUser(memberId);
    }

    // ✅ 채팅방 참여 여부 확인
    @Override
    public boolean isUserInChatRoom(String chatroomId, Long memberId) {
        return chatRoomMapper.isUserInChatRoom(chatroomId, memberId) > 0;
    }

    @Override
    public List<ChatRoomDTO> getChatRooms(Long memberId) {
        return chatRoomMapper.getChatRoomsByMemberId(memberId);
    }

    @Override
    public String findExistingRoom(Long senderId, Long receiverId, String facilityId) {
        return chatRoomMapper.findChatRoomId(senderId, receiverId, facilityId);
    }
    
    @Override
    public ChatRoomDTO getChatRoomInfo(String chatroomId, Long memberId) {
        return chatRoomMapper.getChatRoomInfo(chatroomId, memberId);
    }

    @Override
    public String createRoom(Long senderId, Long receiverId, String facilityId) {
        String newRoomId = UUID.randomUUID().toString();
        chatRoomMapper.insertChatRoom(newRoomId, senderId, receiverId, facilityId);

        // ✅ 시설 or 요양사 기본 메시지 전송
        if (facilityId != null && !facilityId.isBlank()) {
            String defaultMessage = facilityMapper.findDefaultMessageByFacilityId(facilityId);
            if (defaultMessage == null) {
                defaultMessage = caregiverMapper.findDefaultMessageByCaregiverId(facilityId);
            }

            if (defaultMessage != null && !defaultMessage.isBlank()) {
                ChatMessageDTO welcome = new ChatMessageDTO();
                welcome.setChatroomId(newRoomId);
                welcome.setSenderId(receiverId);  // 관리자/시설
                welcome.setReceiverId(senderId);  // 유저
                welcome.setContent(defaultMessage);
                welcome.setSentAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                chatMessageMapper.insertChatMessage(welcome);
                // kafkaChatProducer.sendMessage(welcome); // 사용 시 주석 해제
            }
        }

        return newRoomId;
    }
}
