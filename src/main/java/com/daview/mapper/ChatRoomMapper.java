package com.daview.mapper;

import com.daview.dto.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
    List<ChatRoomDTO> getChatRoomListForUser(Long memberId);
    List<ChatRoomDTO> getChatRoomsByMemberId(Long memberId);
    
    String findChatRoomId(@Param("senderId") Long senderId, 
                          @Param("receiverId") Long receiverId,
                          @Param("facilityId") String facilityId); // facilityId도 같이 조회하도록 변경 필요

    void insertChatRoom(@Param("chatroomId") String chatroomId,
                        @Param("senderId") Long senderId,
                        @Param("receiverId") Long receiverId,
                        @Param("facilityId") String facilityId); 
}