package com.daview.dto;

import java.util.List;

public class CaregiverDTO {
    private String caregiverId;
    private Long memberId;
    private String hopeWorkAreaLocation;
    private String hopeWorkAreaCity;
    private String hopeWorkPlace;
    private String hopeWorkType;
    private String hopeEmploymentType;
    private String educationLevel;
    private String introduction;
    private int hopeWorkAmount;
    private String caregiverCreatedAt;
    private String caregiverUpdateAt;

    private List<String> certificates;
    private List<CaregiverCareerDTO> career;

    private String username;
    private String userGender;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUserGender() {
        return userGender;
    }
    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getCaregiverId() {
        return caregiverId;
    }
    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }
    public Long getMemberId() {
        return memberId;
    }
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    public String getHopeWorkAreaLocation() {
        return hopeWorkAreaLocation;
    }
    public void setHopeWorkAreaLocation(String hopeWorkAreaLocation) {
        this.hopeWorkAreaLocation = hopeWorkAreaLocation;
    }
    public String getHopeWorkAreaCity() {
        return hopeWorkAreaCity;
    }
    public void setHopeWorkAreaCity(String hopeWorkAreaCity) {
        this.hopeWorkAreaCity = hopeWorkAreaCity;
    }
    public String getHopeWorkPlace() {
        ret
