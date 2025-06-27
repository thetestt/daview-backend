package com.daview.dto;

import java.util.List;

public class ReadMessageDTO {
    private String chatroomId;
    private Long readerId;
    private String type;
    private List<Long> chatMessageIds;
    
    
    
    
    
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Long> getChatMessageIds() {
		return chatMessageIds;
	}
	public void setChatMessageIds(List<Long> chatMessageIds) {
		this.chatMessageIds = chatMessageIds;
	}
	public String getChatroomId() {
		return chatroomId;
	}
	public void setChatroomId(String chatroomId) {
		this.chatroomId = chatroomId;
	}
	public Long getReaderId() {
		return readerId;
	}
	public void setReaderId(Long readerId) {
		this.readerId = readerId;
	} 
}
