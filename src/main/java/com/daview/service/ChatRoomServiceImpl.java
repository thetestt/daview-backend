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
    
    @Override
    public List<ChatRoomDTO> getChatRooms(int memberId) {
        return chatRoomMapper.getChatRoomsByMemberId(memberId);
    }
    
    @Override
    public String findExistingRoom(Long senderId, Long receiverId, String facilityId) {
        return chatRoomMapper.findChatRoomId(senderId, receiverId, facilityId);
    }

    @Override
    public String createRoom(Long senderId, Long receiverId, String facilityId) {
        String newRoomId = UUID.randomUUID().toString();
        chatRoomMapper.insertChatRoom(newRoomId, senderId, receiverId, facilityId);

        // ✅ 1. facilityId가 있는 경우만 안내 메시지 조회
        if (facilityId != null && !facilityId.isBlank()) {
        	String defaultMessage = facilityMapper.findDefaultMessageByFacilityId(facilityId);
        	if (defaultMessage == null) {
        	    defaultMessage = caregiverMapper.findDefaultMessageByCaregiverId(facilityId);
        	}
            
            System.out.println("🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡 facilityId: " + facilityId);
            System.out.println("🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡🟡 defaultMessage: " + defaultMessage);;

            // ✅ 2. 메시지가 존재할 경우에만 저장 및 전송
            if (defaultMessage != null && !defaultMessage.isBlank()) {
                ChatMessageDTO welcome = new ChatMessageDTO();
                welcome.setChatroomId(newRoomId);
                welcome.setSenderId(receiverId);  // 관리자/시설
                welcome.setReceiverId(senderId);  // 유저
                welcome.setContent(defaultMessage);

                String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                welcome.setSentAt(now);

                chatMessageMapper.insertChatMessage(welcome);         // DB 저장
                //kafkaChatProducer.sendMessage(welcome);               // ✅ 인스턴스 호출로 수정
            }
        }

        return newRoomId;
    }


    
}
