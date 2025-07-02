package com.daview.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.daview.dto.CaregiverDTO;
import com.daview.dto.ChatMessageDTO;
import com.daview.dto.ChatRoomDTO;
import com.daview.dto.FacilityDTO;
import com.daview.dto.User;
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
    
    private String mapRoleToType(String role) {
        return switch (role.toUpperCase()) {
            case "COMPANY" -> "facility";
            case "CAREGIVER" -> "caregiver";
            case "ADMIN" -> "admin";
            default -> "user";
        };
    }

    private String getNameByType(String type, Long memberId) {
        switch (type) {
            case "facility" -> {
                FacilityDTO f = facilityMapper.findByMemberId(memberId);
                return f != null ? f.getFacilityName() : "알 수 없음";
            }
            case "caregiver", "user", "admin" -> {
                User u = userMapper.findByMemberId(memberId);
                return u != null ? u.getName() : "알 수 없음";
            }
            default -> {
                return "알 수 없음";
            }
        }
    }
   
    
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
    public ChatRoomDTO getChatRoomInfo(String chatroomId, Long myMemberId) {
    	 ChatRoomDTO dto = chatRoomMapper.findById(chatroomId);
    	    if (dto == null) return null;

    	    boolean iAmSender = Objects.equals(myMemberId, dto.getSenderId());
    	    Long opponentId = iAmSender ? dto.getReceiverId() : dto.getSenderId();
    	    dto.setOpponentId(opponentId);

    	    // 💡 먼저 상대방의 타입을 판별해야 한다!
    	    String type = determineOpponentType(dto.getFacilityId(), opponentId);
    	    dto.setType(type);

    	    switch (type) {
    	        case "caregiver" -> {
    	            CaregiverDTO caregiver = caregiverMapper.findByCaregiverId(dto.getFacilityId());
    	            User user = userMapper.findByMemberId2(opponentId);
    	            dto.setCaregiverName(user != null ? user.getUsername() : null);
    	            dto.setHopeWorkAreaLocation(caregiver.getHopeWorkAreaLocation());
    	            dto.setHopeWorkAreaCity(caregiver.getHopeWorkAreaCity());
    	            dto.setOpponentName(user != null ? user.getName() : null);
    	        }
    	        case "facility" -> {
    	            FacilityDTO facility = facilityMapper.findByFacilityId(dto.getFacilityId());
    	            dto.setFacilityName(facility.getFacilityName());
    	            dto.setFacilityType(facility.getFacilityType());
    	            dto.setFacilityPhone(facility.getFacilityPhone());
    	            dto.setFacilityAddressLocation(facility.getFacilityAddressLocation());
    	            dto.setFacilityAddressCity(facility.getFacilityAddressCity());
    	            dto.setOpponentName(facility.getFacilityName());
    	        }
    	        case "user" -> {
    	            User user = userMapper.findByMemberId2(opponentId);
    	            dto.setUserName(user.getUsername());
    	            dto.setOpponentName(user.getName());
    	        }
    	    }

    	    return dto;
    	}
    
    
    //타입 먼저 찾기
    private String determineOpponentType(String facilityId, Long opponentId) {
        // 먼저 caregiver인지 확인
        CaregiverDTO caregiver = caregiverMapper.findByCaregiverId(facilityId);
        if (caregiver != null && caregiver.getMemberId().equals(opponentId)) return "caregiver";

        // 그다음 facility인지 확인
        FacilityDTO facility = facilityMapper.findByFacilityId(facilityId);
        if (facility != null && facility.getMemberId().equals(opponentId)) return "facility";

        return "user"; // 둘 다 아니면 일반 사용자
    }
    
//    @Override
//    public ChatRoomDTO getChatRoomInfo(String chatroomId, Long memberId) {
//        // 채팅방 정보를 가져옴
//        ChatRoomDTO dto = chatRoomMapper.getChatRoomInfo(chatroomId, memberId);
//        System.out.println("== ChatRoom Info 확인 ==");
//        if (dto == null) return null;  // 채팅방이 없다면 종료
//        
//
//
//        String opponentIdStr = dto.getOpponentId().toString();
//        
//        String facilityId = dto.getFacilityId(); 
//
//        // 2. facility_id로 시설 정보 조회
//        if (facilityId != null && !facilityId.isEmpty()) {
//            var facility = facilityMapper.findFacilityInfoById(facilityId);
//            if (facility != null) {
//                // 시설일 경우
//                dto.setType("facility");
//                dto.setFacilityName(facility.getFacilityName());
//                dto.setFacilityAddressLocation(facility.getFacilityAddressLocation());
//                dto.setFacilityAddressCity(facility.getFacilityAddressCity());
//                dto.setFacilityPhone(facility.getFacilityPhone());
//                dto.setFacilityType(facility.getFacilityType());
//                System.out.println("채팅방 상대 정보 (시설): " + dto);
//                return dto;
//            }
//        }
//
//        // 2. caregiver 조회
//        try {
//            Long opponentMemberId = Long.valueOf(dto.getOpponentId());
//            var caregiver = caregiverMapper.findCaregiverInfoByMemberId(opponentMemberId);
//            if (caregiver != null) {
//                // 요양사일 경우
//                dto.setType("caregiver");
//                dto.setCaregiverName(caregiver.getUsername());
//                dto.setHopeWorkAreaLocation(caregiver.getHopeWorkAreaLocation());
//                dto.setHopeWorkAreaCity(caregiver.getHopeWorkAreaCity());
//                System.out.println("채팅방 상대 정보: " + dto);
//                return dto;
//            } else {
//                // caregiver가 없으면 일반 유저 조회로 넘어감
//                System.err.println("Caregiver 정보가 없습니다.");
//            }
//        } catch (Exception e) {
//            System.err.println("Caregiver 조회 실패: " + e.getMessage());
//        }
//
//        // 3. 일반 유저 조회
//        var user = userMapper.findUserById(dto.getOpponentId());
//        if (user != null) {
//            // 일반 유저일 경우
//            dto.setType("user");
//            dto.setUserName(user.getName()); // 일반 유저 이름 설정
//            System.out.println("일반 유저 정보: " + dto);
//            return dto;
//        } else {
//            // 만약 user 정보도 없다면, 명확한 에러 처리
//            System.err.println("User 정보가 없습니다.");
//        }
//
//        return null;  // caregiver와 user 모두 없을 경우 null을 반환
//    }

    @Override
    public String createRoom(Long senderId, Long receiverId, String facilityId) {
        String newRoomId = UUID.randomUUID().toString();

        // 🔹 sender 정보 조회
        User sender = userMapper.findByMemberId(senderId);
        String senderRole = sender.getRole();
        String senderType = mapRoleToType(senderRole);
        String senderName = getNameByType(senderType, senderId);

        // 🔹 receiver 정보 조회
        User receiver = userMapper.findByMemberId(receiverId);
        String receiverRole = receiver.getRole();
        String receiverType = mapRoleToType(receiverRole);
        String receiverName;
        if ("facility".equals(receiverType) && facilityId != null) {
            FacilityDTO facility = facilityMapper.findByFacilityId(facilityId);
            receiverName = facility != null ? facility.getFacilityName() : "알 수 없음";
        } else if ("caregiver".equals(receiverType)) {
            User user = userMapper.findByMemberId(receiverId); // ✅ caregiver의 사용자 이름
            receiverName = user != null ? user.getName() : "알 수 없음";
        } else {
            receiverName = getNameByType(receiverType, receiverId);
        }
        // 🔹 채팅방 INSERT
        chatRoomMapper.insertChatRoom(newRoomId, senderId, receiverId, facilityId,
                senderName, senderType, receiverName, receiverType);

        // 🔹 기본 메시지 처리 (기존대로 유지)
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
                String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                welcome.setSentAt(now);
                welcome.setIsRead(true);
                chatMessageMapper.insertChatMessage(welcome);
                // kafkaChatProducer.sendMessage(welcome); // 필요 시 사용
                
                chatRoomMapper.updateLastMessageAndTime(newRoomId, defaultMessage, now);
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
    
    // 관리자용 메소드들 구현
    @Override
    public List<ChatRoomDTO> getAllChatRoomsForAdmin(int page, int size, String search, String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("offset", page * size);
        params.put("limit", size);
        params.put("search", search);
        params.put("status", status);
        
        return chatRoomMapper.findAllChatRoomsForAdmin(params);
    }
    
    @Override
    public void updateChatRoomStatus(String chatroomId, String status) {
        Map<String, Object> params = new HashMap<>();
        params.put("chatroomId", chatroomId);
        params.put("status", status);
        
        chatRoomMapper.updateChatRoomStatus(params);
    }
}
