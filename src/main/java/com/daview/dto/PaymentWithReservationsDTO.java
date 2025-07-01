package com.daview.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentWithReservationsDTO {
	private String pymId;
	private String impUid;
	private String merchantUid;
	private String prodid;
	private Long memberId;
	private String custNm;
	private String custTel;
	private String custEmTel;
	private Date custDate;
	private String custMemo;
	private int pymPrice;
	private int pymStatus;
	private String pymMethod;
	private String pymNum;
	private Date pymDate;
	private String refundReason;
	private Date refundDate;
	private int couponDiscount;

	private List<ReservationDTO> reservations = new ArrayList<>();

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

	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
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

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public List<ReservationDTO> getReservations() {
		return reservations;
	}

	public void setReservations(List<ReservationDTO> reservations) {
		this.reservations = reservations;
	}

	public int getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(int couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

}
