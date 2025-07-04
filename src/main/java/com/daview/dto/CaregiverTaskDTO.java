package com.daview.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

public class CaregiverTaskDTO {
    private Long id;
    private String caregiverId;
    private LocalDate taskDate;
    private LocalTime taskTime;
    private String taskType;
    private Boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 추가 정보 필드
    private String taskDescription;
    private String priority; // high, medium, low
    private String caregiverName;
    
    // 기본 생성자
    public CaregiverTaskDTO() {}
    
    // 전체 필드 생성자
    public CaregiverTaskDTO(Long id, String caregiverId, LocalDate taskDate, 
                           LocalTime taskTime, String taskType, Boolean completed,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.caregiverId = caregiverId;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskType = taskType;
        this.completed = completed;
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
    
    public String getCaregiverId() {
        return caregiverId;
    }
    
    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }
    
    public LocalDate getTaskDate() {
        return taskDate;
    }
    
    public void setTaskDate(LocalDate taskDate) {
        this.taskDate = taskDate;
    }
    
    public LocalTime getTaskTime() {
        return taskTime;
    }
    
    public void setTaskTime(LocalTime taskTime) {
        this.taskTime = taskTime;
    }
    
    public String getTaskType() {
        return taskType;
    }
    
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
    
    public Boolean getCompleted() {
        return completed;
    }
    
    public void setCompleted(Boolean completed) {
        this.completed = completed;
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
    
    public String getTaskDescription() {
        return taskDescription;
    }
    
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
    }
    
    public String getCaregiverName() {
        return caregiverName;
    }
    
    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }
    
    @Override
    public String toString() {
        return "CaregiverTaskDTO{" +
                "id=" + id +
                ", caregiverId=" + caregiverId +
                ", taskDate=" + taskDate +
                ", taskTime=" + taskTime +
                ", taskType='" + taskType + '\'' +
                ", completed=" + completed +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", taskDescription='" + taskDescription + '\'' +
                ", priority='" + priority + '\'' +
                ", caregiverName='" + caregiverName + '\'' +
                '}';
    }
} 