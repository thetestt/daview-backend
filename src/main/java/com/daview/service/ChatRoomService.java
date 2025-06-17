package com.daview.service;

import com.daview.dto.ChatMessageDTO;
import com.daview.dto.ChatRoomDTO;
import java.util.List;

public interface ChatRoomService {
    List<ChatRoomDTO> getChatRooms(int memberId);
    String findExistingRoom(Long senderId, Long receiverId);
    String createRoom(Long senderId, Long receiverId);
    
}