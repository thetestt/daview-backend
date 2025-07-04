package com.daview.dto;

import java.util.Date;

public class ReviewDTO {
	private Long revId;
	private Long memberId;
	private String prodNm;
	private String revTtl;
	private String revCont;
	private int revStars;
	private int revViews;
	private Date revRegDate;
	private String memberName;
	private int commentCount;
	

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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

}
