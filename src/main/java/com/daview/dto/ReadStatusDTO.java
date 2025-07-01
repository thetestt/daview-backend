package com.daview.dto;

public class ReadStatusDTO {
	
	private String chatroomId;
    private Long readerId;
    private Long lastReadMessageId;
    
    
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
	public Long getLastReadMessageId() {
		return lastReadMessageId;
	}
	public void setLastReadMessageId(Long lastReadMessageId) {
		this.lastReadMessageId = lastReadMessageId;
	}


}
