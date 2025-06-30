package com.daview.dto;

import java.util.Date;

/**
 * 관리자용 후기 DTO
 * 후기 정보와 작성자 정보를 함께 포함
 */
public class AdminReviewDTO {
    private Long revId;
    private Long memberId;
    private String memberName;      // 작성자 이름
    private String memberUsername;  // 작성자 유저명
    private String prodNm;
    private String revTtl;
    private String revCont;
    private int revStars;
    private int revViews;
    private Date revRegDate;

    // 기본 생성자
    public AdminReviewDTO() {}

    // 전체 필드 생성자
    public AdminReviewDTO(Long revId, Long memberId, String memberName, String memberUsername, 
                         String prodNm, String revTtl, String revCont, int revStars, 
                         int revViews, Date revRegDate) {
        this.revId = revId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberUsername = memberUsername;
        this.prodNm = prodNm;
        this.revTtl = revTtl;
        this.revCont = revCont;
        this.revStars = revStars;
        this.revViews = revViews;
        this.revRegDate = revRegDate;
    }

    // Getter & Setter
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
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

    @Override
    public String toString() {
        return "AdminReviewDTO{" +
                "revId=" + revId +
                ", memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                ", memberUsername='" + memberUsername + '\'' +
                ", prodNm='" + prodNm + '\'' +
                ", revTtl='" + revTtl + '\'' +
                ", revStars=" + revStars +
                ", revViews=" + revViews +
                ", revRegDate=" + revRegDate +
                '}';
    }
} 