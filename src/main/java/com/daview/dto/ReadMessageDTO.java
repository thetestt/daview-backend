package com.daview.dto;

public class ReadMessageDTO {
    private String chatroomId;
    private Long readerId;
    
    
    
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
