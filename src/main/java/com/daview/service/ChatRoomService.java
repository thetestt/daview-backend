package com.daview.service;

import com.daview.dto.ChatRoomDTO;
import java.util.List;

public interface ChatRoomService {
    List<ChatRoomDTO> getChatRooms(Long memberId);
    String findExistingRoom(Long senderId, Long receiverId, String facilityId);
    String createRoom(Long senderId, Long receiverId, String facilityId);
    List<ChatRoomDTO> getChatRoomListForUser(Long memberId);
    boolean isUserInChatRoom(String chatroomId, Long memberId);
    ChatRoomDTO getChatRoomInfo(String chatroomId, Long memberId);
    void exitChatRoom(String chatroomId, Long memberId);
    boolean isUserInChatroom(String chatroomId, Long memberId);
    ChatRoomDTO getChatRoomDetailById(String chatroomId);
}