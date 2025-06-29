package com.daview.dto;

public class ChatReadDTO {
	 private String type; // "READ"
	    private String chatroomId;
	    private Long readerId;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
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
