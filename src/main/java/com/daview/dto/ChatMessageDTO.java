package com.daview.dto;

public class ChatMessageDTO {
	private Long chatMessageId;
	private String chatroomId;
    private Long senderId;
    private String content;
    private String sentAt;
    private Long receiverId; 
    private Boolean isRead;
    
    
    
    
	public Long getChatMessageId() {
		return chatMessageId;
	}
	public void setChatMessageId(Long chatMessageId) {
		this.chatMessageId = chatMessageId;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	public String getChatroomId() {
		return chatroomId;
	}
	public void setChatroomId(String chatroomId) {
		this.chatroomId = chatroomId;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSentAt() {
		return sentAt;
	}
	public void setSentAt(String sentAt) {
		this.sentAt = sentAt;
	}

}
