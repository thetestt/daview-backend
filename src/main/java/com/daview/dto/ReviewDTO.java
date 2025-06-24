package com.daview.dto;

import java.util.Date;

public class ReviewDTO {
	Long revId;
	Long memberId;
	String prodNm;
	String revTtl;
	String revCont;
	int revStars;
	int revViews;
	Date revRegDate;

	public Long getRevId() {
		return revId;
	}

	public void setRevId(Long revId) {
		this.revId = revId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getProdNm() {
		return prodNm;
	}

	public void setProdNm(String prodNm) {
		this.prodNm = prodNm;
	}

	public String getRevTtl() {
		return revTtl;
	}

	public void setRevTtl(String revTtl) {
		this.revTtl = revTtl;
	}

	public String getRevCont() {
		return revCont;
	}

	public void setRevCont(String revCont) {
		this.revCont = revCont;
	}

	public int getRevStars() {
		return revStars;
	}

	public void setRevStars(int revStars) {
		this.revStars = revStars;
	}

	public int getRevViews() {
		return revViews;
	}

	public void setRevViews(int revViews) {
		this.revViews = revViews;
	}

	public Date getRevRegDate() {
		return revRegDate;
	}

	public void setRevRegDate(Date revRegDate) {
		this.revRegDate = revRegDate;
	}

}
