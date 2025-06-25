package com.daview.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.daview.dto.ChatMessageDTO;
import com.daview.dto.ChatRoomDTO;
import com.daview.mapper.CaregiverMapper;
import com.daview.mapper.ChatMessageMapper;
import com.daview.mapper.ChatRoomMapper;
import com.daview.mapper.FacilityMapper;
import com.daview.mapper.UserMapper;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomMapper chatRoomMapper;
    private final ChatMessageMapper chatMessageMapper;
    private final FacilityMapper facilityMapper;
    private final KafkaChatProducer kafkaChatProducer;
    private final CaregiverMapper caregiverMapper;
    private final UserMapper userMapper;
   
    
    public ChatRoomServiceImpl(ChatRoomMapper chatRoomMapper,
                               ChatMessageMapper chatMessageMapper,
                               FacilityMapper facilityMapper,
                               CaregiverMapper caregiverMapper,
                               KafkaChatProducer kafkaChatProducer,
                               UserMapper userMapper
                               ) {
        this.chatRoomMapper = chatRoomMapper;
        this.chatMessageMapper = chatMessageMapper;
        this.facilityMapper = facilityMapper;
        this.caregiverMapper = caregiverMapper;
        this.kafkaChatProducer = kafkaChatProducer;
        this.userMapper = userMapper;
       
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
        // 채팅방 정보를 가져옴
        ChatRoomDTO dto = chatRoomMapper.getChatRoomInfo(chatroomId, memberId);
        if (dto == null) return null;  // 채팅방이 없다면 종료

        String opponentIdStr = dto.getOpponentId().toString();
        
        String facilityId = dto.getFacilityId(); 

        // 2. facility_id로 시설 정보 조회
        if (facilityId != null && !facilityId.isEmpty()) {
            var facility = facilityMapper.findFacilityInfoById(facilityId);
            if (facility != null) {
                // 시설일 경우
                dto.setType("facility");
                dto.setFacilityName(facility.getFacilityName());
                dto.setFacilityAddressLocation(facility.getFacilityAddressLocation());
                dto.setFacilityAddressCity(facility.getFacilityAddressCity());
                dto.setFacilityPhone(facility.getFacilityPhone());
                dto.setFacilityType(facility.getFacilityType());
                System.out.println("채팅방 상대 정보 (시설): " + dto);
                return dto;
            }
        }

        // 2. caregiver 조회
        try {
            Long opponentMemberId = Long.valueOf(dto.getOpponentId());
            var caregiver = caregiverMapper.findCaregiverInfoByMemberId(opponentMemberId);
            if (caregiver != null) {
                // 요양사일 경우
                dto.setType("caregiver");
                dto.setCaregiverName(caregiver.getUsername());
                dto.setHopeWorkAreaLocation(caregiver.getHopeWorkAreaLocation());
                dto.setHopeWorkAreaCity(caregiver.getHopeWorkAreaCity());
                System.out.println("채팅방 상대 정보: " + dto);
                return dto;
            } else {
                // caregiver가 없으면 일반 유저 조회로 넘어감
                System.err.println("Caregiver 정보가 없습니다.");
            }
        } catch (Exception e) {
            System.err.println("Caregiver 조회 실패: " + e.getMessage());
        }

        // 3. 일반 유저 조회
        var user = userMapper.findUserById(dto.getOpponentId());
        if (user != null) {
            // 일반 유저일 경우
            dto.setType("user");
            dto.setUserName(user.getName()); // 일반 유저 이름 설정
            System.out.println("일반 유저 정보: " + dto);
            return dto;
        } else {
            // 만약 user 정보도 없다면, 명확한 에러 처리
            System.err.println("User 정보가 없습니다.");
        }

        return null;  // caregiver와 user 모두 없을 경우 null을 반환
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
    
    @Override
    public void exitChatRoom(String chatroomId, Long memberId) {
        // 본인이 속한 채팅방인지 확인하고 trash_can 처리
        chatRoomMapper.updateTrashCan(chatroomId, memberId);
    }
    
    //채팅방 있는지 없는지 체크
    @Override
    public ChatRoomDTO getChatRoomDetailById(String chatroomId) {
        return chatRoomMapper.getChatRoomDetailById(chatroomId);
    }
    
    
    //웹소켓 검증용서비스
    @Override
    public boolean isUserInChatroom(String chatroomId, Long memberId) {
    	Map<String, Object> param = new HashMap<>();
        param.put("chatroomId", chatroomId);
        param.put("memberId", memberId);
        return chatRoomMapper.existsByChatroomIdAndMemberId(param);
    }
}
