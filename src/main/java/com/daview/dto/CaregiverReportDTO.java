package com.daview.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CaregiverReportDTO {
    private Long id;
    private Long caregiverId;
    private LocalDate reportDate;
    private String content;
    private String status; // pending, submitted, approved, rejected
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 조인을 위한 추가 필드
    private String caregiverName;
    private String facilityName;
    
    // 기본 생성자
    public CaregiverReportDTO() {}
    
    // 전체 필드 생성자
    public CaregiverReportDTO(Long id, Long caregiverId, LocalDate reportDate, 
                             String content, String status, LocalDateTime createdAt, 
                             LocalDateTime updatedAt) {
        this.id = id;
        this.caregiverId = caregiverId;
        this.reportDate = reportDate;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getter and Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCaregiverId() {
        return caregiverId;
    }
    
    public void setCaregiverId(Long caregiverId) {
        this.caregiverId = caregiverId;
    }
    
    public LocalDate getReportDate() {
        return reportDate;
    }
    
    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getCaregiverName() {
        return caregiverName;
    }
    
    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }
    
    public String getFacilityName() {
        return facilityName;
    }
    
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
    
    @Override
    public String toString() {
        return "CaregiverReportDTO{" +
                "id=" + id +
                ", caregiverId=" + caregiverId +
                ", reportDate=" + reportDate +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", caregiverName='" + caregiverName + '\'' +
                ", facilityName='" + facilityName + '\'' +
                '}';
    }
} 