package com.daview.dto;

import java.util.Date;

public class ReservationDTO {
	private String rsvId;
	private Long memberId;
	private String prodId;
	private int prodType;
	private int prodPrice;
	private String prodNm;
	private String prodDetail;
	private String prodPhoto;
	private int rsvType;
	private Date rsvDate;
	private int rsvCnt;

	public String getRsvId() {
		return rsvId;
	}

	public void setRsvId(String rsvId) {
		this.rsvId = rsvId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public int getProdType() {
		return prodType;
	}

	public void setProdType(int prodType) {
		this.prodType = prodType;
	}

	public int getProdPrice() {
		return prodPrice;
	}

	public void setProdPrice(int prodPrice) {
		this.prodPrice = prodPrice;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getProdDetail() {
		return prodDetail;
	}

	public void setProdDetail(String prodDetail) {
		this.prodDetail = prodDetail;
	}

	public String getProdPhoto() {
		return prodPhoto;
	}

	public void setProdPhoto(String prodPhoto) {
		this.prodPhoto = prodPhoto;
	}

	public int getRsvType() {
		return rsvType;
	}

	public void setRsvType(int rsvType) {
		this.rsvType = rsvType;
	}

	public Date getRsvDate() {
		return rsvDate;
	}

	public void setRsvDate(Date rsvDate) {
		this.rsvDate = rsvDate;
	}

	public int getRsvCnt() {
		return rsvCnt;
	}

	public void setRsvCnt(int rsvCnt) {
		this.rsvCnt = rsvCnt;
	}

}
