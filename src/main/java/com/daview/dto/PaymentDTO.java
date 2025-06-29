package com.daview.dto;

import java.util.Date;

public class PaymentDTO {
	// 기본 결제 정보
	private String pymId; // 내부 결제 ID
	private String impUid; // 플랫폼 결제건 ID
	private String merchantUid; // 결제 요청 시 주문 ID
	private Long memberId;
	private String custNm;
	private String custTel;
	private String custEmTel;
	private Date custDate;
	private String custMemo;
	private int pymPrice;
	private int pymStatus;
	private String pymMethod;
	private String pymNum; // pg사 발행한 결제 거래번호
	private Date pymDate;

	// 관리자 페이지용 alias 필드들
	private String paymentId; // pymId와 동일
	private int amount; // pymPrice와 동일
	private int status; // pymStatus와 동일
	private Date paymentDate; // pymDate와 동일
	private String customerName; // custNm과 동일
	private String customerPhone; // custTel과 동일
	private String customerEmail; // custEmTel과 동일

	// 조인해서 가져오는 회원 정보
	private String memberName;
	private String memberEmail;
	private String memberPhone;

	// 조인해서 가져오는 결제 방법 정보
	private String paymentMethodName;
	private String paymentNumber; // pymNum과 동일

	// 조인해서 가져오는 예약 정보
	private String reservationId;
	private String productName;
	private String productType;
	private String reservationTypeName;
	private Date reservationDate;
	private Integer reservationStatus;

	// Getters and Setters
	public String getPymId() {
		return pymId;
	}

	public void setPymId(String pymId) {
		this.pymId = pymId;
	}

	public String getImpUid() {
		return impUid;
	}

	public void setImpUid(String impUid) {
		this.impUid = impUid;
	}

	public String getMerchantUid() {
		return merchantUid;
	}

	public void setMerchantUid(String merchantUid) {
		this.merchantUid = merchantUid;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}

	public String getCustTel() {
		return custTel;
	}

	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}

	public String getCustEmTel() {
		return custEmTel;
	}

	public void setCustEmTel(String custEmTel) {
		this.custEmTel = custEmTel;
	}

	public Date getCustDate() {
		return custDate;
	}

	public void setCustDate(Date custDate) {
		this.custDate = custDate;
	}

	public String getCustMemo() {
		return custMemo;
	}

	public void setCustMemo(String custMemo) {
		this.custMemo = custMemo;
	}

	public int getPymPrice() {
		return pymPrice;
	}

	public void setPymPrice(int pymPrice) {
		this.pymPrice = pymPrice;
	}

	public int getPymStatus() {
		return pymStatus;
	}

	public void setPymStatus(int pymStatus) {
		this.pymStatus = pymStatus;
	}

	public String getPymMethod() {
		return pymMethod;
	}

	public void setPymMethod(String pymMethod) {
		this.pymMethod = pymMethod;
	}

	public String getPymNum() {
		return pymNum;
	}

	public void setPymNum(String pymNum) {
		this.pymNum = pymNum;
	}

	public Date getPymDate() {
		return pymDate;
	}

	public void setPymDate(Date pymDate) {
		this.pymDate = pymDate;
	}

	// 관리자 페이지용 alias 필드 getters/setters
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	// 조인 필드 getters/setters
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public String getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getReservationTypeName() {
		return reservationTypeName;
	}

	public void setReservationTypeName(String reservationTypeName) {
		this.reservationTypeName = reservationTypeName;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public Integer getReservationStatus() {
		return reservationStatus;
	}

	public void setReservationStatus(Integer reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
}
