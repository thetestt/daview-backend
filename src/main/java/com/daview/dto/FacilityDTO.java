package com.daview.dto;

import java.util.List;

public class FacilityDTO {
    // 기본 facility 테이블 필드들
    private String facilityId;
    private String facilityName;
    private Integer monthlyFee;
    private String facilityType;
    private String location;
    private String city;
    private String theme;
    private String detailedAddress;
    private String homepageUrl;
    private String phoneNumber;
    private String createdAt;
    private String updatedAt;
    
    // JOIN으로 가져오는 facility_notice 필드들
    private String noticeTitle;
    private String noticeContent;
    
    // JOIN으로 가져오는 facility_photo 필드들
    private String photoUrl;
    private String thumbnailUrl;
    
    // JOIN으로 가져오는 facility_tag 필드들 (GROUP_CONCAT)
    private String tagString;
    
    // 리스트 형태 (기존 코드 호환용)
    private List<String> photos;
    private List<String> tags;
    private List<NoticeDTO> notices;
    
    // 기존 필드들 (호환성을 위해 유지)
    private Long memberId;
    private Integer facilityCharge;
    private String facilityAddressLocation;
    private String facilityAddressCity;
    private String facilityDetailAddress;
    private String facilityTheme;
    private String facilityHomepage;
    private String facilityPhone;
    private String isThumbnail;
    private String category;
    private String facilityTag;
    private String facilityCreatedAt;
    private String facilityUpdateAt;
    private String facilityDeletedAt;
    private String defaultMessage;

    // 새 필드들의 Getters and Setters
    public String getFacilityId() {
        return facilityId;
    }
    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }
    
    public String getFacilityName() {
        return facilityName;
    }
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
    
    public Integer getMonthlyFee() {
        return monthlyFee;
    }
    public void setMonthlyFee(Integer monthlyFee) {
        this.monthlyFee = monthlyFee;
    }
    
    public String getFacilityType() {
        return facilityType;
    }
    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }
    
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    
    public String getDetailedAddress() {
        return detailedAddress;
    }
    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }
    
    public String getHomepageUrl() {
        return homepageUrl;
    }
    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getNoticeTitle() {
        return noticeTitle;
    }
    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }
    
    public String getNoticeContent() {
        return noticeContent;
    }
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }
    
    public String getPhotoUrl() {
        return photoUrl;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
    
    public String getTagString() {
        return tagString;
    }
    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    // 기존 메서드들 (호환성을 위해 유지)
    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    
    public Integer getFacilityCharge() {
        return facilityCharge;
    }
    public void setFacilityCharge(Integer facilityCharge) {
        this.facilityCharge = facilityCharge;
    }
    
    public String getFacilityAddressLocation() {
        return facilityAddressLocation;
    }
    public void setFacilityAddressLocation(String facilityAddressLocation) {
        this.facilityAddressLocation = facilityAddressLocation;
    }
    
    public String getFacilityAddressCity() {
        return facilityAddressCity;
    }
    public void setFacilityAddressCity(String facilityAddressCity) {
        this.facilityAddressCity = facilityAddressCity;
    }
    
    public String getFacilityDetailAddress() {
        return facilityDetailAddress;
    }
    public void setFacilityDetailAddress(String facilityDetailAddress) {
        this.facilityDetailAddress = facilityDetailAddress;
    }
    
    public String getFacilityTheme() {
        return facilityTheme;
    }
    public void setFacilityTheme(String facilityTheme) {
        this.facilityTheme = facilityTheme;
    }
    
    public String getFacilityHomepage() {
        return facilityHomepage;
    }
    public void setFacilityHomepage(String facilityHomepage) {
        this.facilityHomepage = facilityHomepage;
    }
    
    public String getFacilityPhone() {
        return facilityPhone;
    }
    public void setFacilityPhone(String facilityPhone) {
        this.facilityPhone = facilityPhone;
    }
    
    public List<String> getPhotos() {
        return photos;
    }
    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
    
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    public List<NoticeDTO> getNotices() {
        return notices;
    }
    public void setNotices(List<NoticeDTO> notices) {
        this.notices = notices;
    }
    
    public String getIsThumbnail() {
        return isThumbnail;
    }
    public void setIsThumbnail(String isThumbnail) {
        this.isThumbnail = isThumbnail;
    }
    
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getFacilityTag() {
        return facilityTag;
    }
    public void setFacilityTag(String facilityTag) {
        this.facilityTag = facilityTag;
    }
    
    public String getFacilityCreatedAt() {
        return facilityCreatedAt;
    }
    public void setFacilityCreatedAt(String facilityCreatedAt) {
        this.facilityCreatedAt = facilityCreatedAt;
    }
    
    public String getFacilityUpdateAt() {
        return facilityUpdateAt;
    }
    public void setFacilityUpdateAt(String facilityUpdateAt) {
        this.facilityUpdateAt = facilityUpdateAt;
    }
    
    public String getFacilityDeletedAt() {
        return facilityDeletedAt;
    }
    public void setFacilityDeletedAt(String facilityDeletedAt) {
        this.facilityDeletedAt = facilityDeletedAt;
    }
    
    public String getDefaultMessage() {
        return defaultMessage;
    }
    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }
}
