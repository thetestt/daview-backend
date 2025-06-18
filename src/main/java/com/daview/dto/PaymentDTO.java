package com.daview.dto;

import java.util.Date;

public class PaymentDTO {
	private String pymId; // 내부 결제 ID
	private String impUid; // 플랫폼 결제건 ID
	private String merchantUid; // 결제 요청 시 주문 ID
	private String rsvId;
	private String prodId;
	private int memberId;

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

	public String getRsvId() {
		return rsvId;
	}

	public void setRsvId(String rsvId) {
		this.rsvId = rsvId;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
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

}
