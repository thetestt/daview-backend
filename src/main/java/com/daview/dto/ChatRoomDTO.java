package com.daview.dto;

public class ChatRoomDTO {

    private String chatroomId;
    private Long opponentId;
    private String opponentName;
    private String lastMessage;
    private String sentAt;
    private int unreadCount;
    
    // 관리자용 필드들
    private String status;        // 문의 상태 (pending, processing, completed, closed)
    private String createdAt;     // 채팅방 생성일
  

	// 구분
    private String type; // facility, caregiver, user
    private String facilityType; // 실버타운, 요양원 (facility일 경우만)

    // caregiver 전용 필드
    private String caregiverName;
    private String hopeWorkAreaLocation;
    private String hopeWorkAreaCity;

    // facility 전용 필드
    private String facilityName;
    private String facilityAddressLocation;
    private String facilityAddressCity;
    private String facilityPhone;
    private String facilityId;
    
    
 // 일반 유저 전용 필드
    private String userName; // 일반 유저 이름
    
    //채팅방 나가기 사용필드
    private Boolean senderTrashCan;
    private Boolean receiverTrashCan;
    
    //나간 채팅방인지 검증할떄쓰는 DTO
    private Long senderId; 
    private Long receiverId; 
    
//채팅방에 저장할 컬럼 
    private String senderName;
    private String senderType;

    private String receiverName;
    private String receiverType;
    
    
    private String userRole;           // 'sender' 또는 'receiver'
    private boolean iLeftThisRoom;     // 내가 나갔는지
    private boolean opponentOut;       // 상대가 나갔는지
    
    
    
    
    public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public boolean isiLeftThisRoom() {
		return iLeftThisRoom;
	}
	public void setiLeftThisRoom(boolean iLeftThisRoom) {
		this.iLeftThisRoom = iLeftThisRoom;
	}
	public boolean isOpponentOut() {
		return opponentOut;
	}
	public void setOpponentOut(boolean opponentOut) {
		this.opponentOut = opponentOut;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderType() {
		return senderType;
	}
	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}
	public Boolean getSenderTrashCan() {
		return senderTrashCan;
	}
	public void setSenderTrashCan(Boolean senderTrashCan) {
		this.senderTrashCan = senderTrashCan;
	}
	public Boolean getReceiverTrashCan() {
		return receiverTrashCan;
	}
	public void setReceiverTrashCan(Boolean receiverTrashCan) {
		this.receiverTrashCan = receiverTrashCan;
	}
    
    public String getCaregiverName() {
		return caregiverName;
	}
	public void setCaregiverName(String caregiverName) {
		this.caregiverName = caregiverName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	// --- Getter / Setter ---
    public String getChatroomId() { return chatroomId; }
    public void setChatroomId(String chatroomId) { this.chatroomId = chatroomId; }

    public String getOpponentName() { return opponentName; }
    public void setOpponentName(String opponentName) { this.opponentName = opponentName; }

    public String getLastMessage() { return lastMessage; }
    public void setLastMessage(String lastMessage) { this.lastMessage = lastMessage; }



    public String getSentAt() {
		return sentAt;
	}
	public void setSentAt(String sentAt) {
		this.sentAt = sentAt;
	}
	public int getUnreadCount() { return unreadCount; }
    public void setUnreadCount(int unreadCount) { this.unreadCount = unreadCount; }

    public Long getOpponentId() { return opponentId; }
    public void setOpponentId(Long opponentId) { this.opponentId = opponentId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getFacilityType() { return facilityType; }
    public void setFacilityType(String facilityType) { this.facilityType = facilityType; }


    public String getHopeWorkAreaLocation() { return hopeWorkAreaLocation; }
    public void setHopeWorkAreaLocation(String hopeWorkAreaLocation) { this.hopeWorkAreaLocation = hopeWorkAreaLocation; }

    public String getHopeWorkAreaCity() { return hopeWorkAreaCity; }
    public void setHopeWorkAreaCity(String hopeWorkAreaCity) { this.hopeWorkAreaCity = hopeWorkAreaCity; }

    public String getFacilityName() { return facilityName; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }

    public String getFacilityAddressLocation() { return facilityAddressLocation; }
    public void setFacilityAddressLocation(String facilityAddressLocation) { this.facilityAddressLocation = facilityAddressLocation; }

    public String getFacilityAddressCity() { return facilityAddressCity; }
    public void setFacilityAddressCity(String facilityAddressCity) { this.facilityAddressCity = facilityAddressCity; }

    public String getFacilityPhone() { return facilityPhone; }
    public void setFacilityPhone(String facilityPhone) { this.facilityPhone = facilityPhone; }
    
    // 관리자용 필드 getter/setter
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
