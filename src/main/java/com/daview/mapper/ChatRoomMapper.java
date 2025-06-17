package com.daview.mapper;

import com.daview.dto.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
	List<ChatRoomDTO> getChatRoomListForUser(int memberId);
    List<ChatRoomDTO> getChatRoomsByMemberId(int memberId);
    
    String findChatRoomId(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    void insertChatRoom(@Param("chatroomId") String chatroomId,
                        @Param("senderId") Long senderId,
                        @Param("receiverId") Long receiverId);
}