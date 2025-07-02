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
    private List<String> services;  // 제공 서비스 목록
    
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
    
    // 기업 대시보드용 추가 필드들
    private String facilityEmail;
    private String facilityWebsite;
    private String introduction;
    private Integer capacity;
    private String establishedDate;

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
    
    public List<String> getServices() {
        return services;
    }
    public void setServices(List<String> services) {
        this.services = services;
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
    
    // 기업 대시보드용 추가 필드들의 getter/setter
    public String getFacilityEmail() {
        return facilityEmail;
    }
    public void setFacilityEmail(String facilityEmail) {
        this.facilityEmail = facilityEmail;
    }
    
    public String getFacilityWebsite() {
        return facilityWebsite;
    }
    public void setFacilityWebsite(String facilityWebsite) {
        this.facilityWebsite = facilityWebsite;
    }
    
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public String getEstablishedDate() {
        return establishedDate;
    }
    public void setEstablishedDate(String establishedDate) {
        this.establishedDate = establishedDate;
    }
    
    // ==================== snake_case 접근자 (프론트엔드 호환성) ====================
    
    public String getFacility_name() {
        return facilityName;
    }
    public void setFacility_name(String facility_name) {
        this.facilityName = facility_name;
    }
    
    public String getFacility_type() {
        return facilityType;
    }
    public void setFacility_type(String facility_type) {
        this.facilityType = facility_type;
    }
    
    public String getFacility_address_location() {
        return facilityAddressLocation;
    }
    public void setFacility_address_location(String facility_address_location) {
        this.facilityAddressLocation = facility_address_location;
    }
    
    public String getFacility_address_city() {
        return facilityAddressCity;
    }
    public void setFacility_address_city(String facility_address_city) {
        this.facilityAddressCity = facility_address_city;
    }
    
    public String getFacility_detail_address() {
        return facilityDetailAddress;
    }
    public void setFacility_detail_address(String facility_detail_address) {
        this.facilityDetailAddress = facility_detail_address;
    }
    
    public String getFacility_phone() {
        return facilityPhone;
    }
    public void setFacility_phone(String facility_phone) {
        this.facilityPhone = facility_phone;
    }
    
    public String getFacility_homepage() {
        return facilityHomepage;
    }
    public void setFacility_homepage(String facility_homepage) {
        this.facilityHomepage = facility_homepage;
    }
    
    public String getFacility_theme() {
        return facilityTheme;
    }
    public void setFacility_theme(String facility_theme) {
        this.facilityTheme = facility_theme;
    }
    
    public Integer getFacility_charge() {
        return facilityCharge;
    }
    public void setFacility_charge(Integer facility_charge) {
        this.facilityCharge = facility_charge;
    }
    
    public String getDefault_message() {
        return defaultMessage;
    }
    public void setDefault_message(String default_message) {
        this.defaultMessage = default_message;
    }
    
    public String getFacility_tag() {
        return facilityTag;
    }
    public void setFacility_tag(String facility_tag) {
        this.facilityTag = facility_tag;
    }
    
    public String getEstablished_date() {
        return establishedDate;
    }
    public void setEstablished_date(String established_date) {
        this.establishedDate = established_date;
    }
    
    public String getFacility_email() {
        return facilityEmail;
    }
    public void setFacility_email(String facility_email) {
        this.facilityEmail = facility_email;
    }
    
    public String getFacility_website() {
        return facilityWebsite;
    }
    public void setFacility_website(String facility_website) {
        this.facilityWebsite = facility_website;
    }
}
