package com.daview.mapper;

import com.daview.dto.ChatMessageDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChatMessageMapper {
    void insertChatMessage(ChatMessageDTO message);
    List<ChatMessageDTO> getMessagesByRoom(@Param("chatroomId") String chatroomId, @Param("memberId") Long memberId);
  //채팅 읽음처리
    void markMessagesAsRead(@Param("chatroomId") String chatroomId, @Param("memberId") Long memberId);
    List<Long> findUnreadMessageIdsByChatroomAndOpponent(@Param("chatroomId") String chatroomId,
            @Param("readerId") Long readerId);

    
}