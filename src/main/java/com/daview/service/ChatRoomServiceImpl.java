package com.daview.service;

import com.daview.dto.ChatMessageDTO;
import com.daview.dto.ChatRoomDTO;
import com.daview.mapper.ChatMessageMapper;
import com.daview.mapper.ChatRoomMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomMapper chatRoomMapper;

    public ChatRoomServiceImpl(ChatRoomMapper chatRoomMapper) {
        this.chatRoomMapper = chatRoomMapper;
    }

    @Override
    public List<ChatRoomDTO> getChatRooms(int memberId) {
        return chatRoomMapper.getChatRoomsByMemberId(memberId);
    }
    
    @Override
    public String findExistingRoom(Long senderId, Long receiverId) {
        return chatRoomMapper.findChatRoomId(senderId, receiverId);
    }

    @Override
    public String createRoom(Long senderId, Long receiverId) {
        String newRoomId = UUID.randomUUID().toString();
        chatRoomMapper.insertChatRoom(newRoomId, senderId, receiverId);
        return newRoomId;
    }

    
}
