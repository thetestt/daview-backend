package com.daview.mapper;

import com.daview.dto.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
    
    // ✅ 유저가 참여 중인 채팅방 목록 (opponent 정보, lastMessage 포함)
    List<ChatRoomDTO> getChatRoomListForUser(Long memberId);

    // ✅ senderId 기준 전체 채팅방 (간단 목록용)
    List<ChatRoomDTO> getChatRoomsByMemberId(Long memberId);

    // ✅ 기존 채팅방 ID 조회 (이미 존재하는 방인지 확인)
    String findChatRoomId(@Param("senderId") Long senderId, 
                          @Param("receiverId") Long receiverId,
                          @Param("facilityId") String facilityId); // facilityId도 같이 조회

    // ✅ 채팅방 새로 생성
    void insertChatRoom(@Param("chatroomId") String chatroomId,
                        @Param("senderId") Long senderId,
                        @Param("receiverId") Long receiverId,
                        @Param("facilityId") String facilityId); 

    // ✅ 해당 유저가 채팅방에 접근 가능한지 여부
    int isUserInChatRoom(@Param("chatroomId") String chatroomId,
                         @Param("memberId") Long memberId);

    // ✅ 채팅방의 마지막 메시지 및 시간 업데이트
    void updateLastMessage(@Param("chatroomId") String chatroomId,
                           @Param("lastMessage") String lastMessage,
                           @Param("lastTime") String lastTime);

    // ✅ [✨추가] 특정 채팅방 하나의 리스트 정보를 가져오기 (WebSocket 실시간 업데이트용)
    ChatRoomDTO getChatRoomInfoForList(@Param("memberId") Long memberId,
                                       @Param("chatroomId") String chatroomId); // 👈 프론트의 ChatList 실시간 반영용

    // ✅ [✨추가] 상대방 ID 가져오기 (WebSocket 브로드캐스트 대상 판단용)
    Long getOpponentId(@Param("chatroomId") String chatroomId,
                       @Param("memberId") Long memberId); // 👈 메시지 수신자 ID 확인용
    
    ChatRoomDTO getChatRoomInfo(@Param("chatroomId") String chatroomId,
            @Param("memberId") Long memberId);
}