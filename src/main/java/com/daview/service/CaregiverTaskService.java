package com.daview.service;

import com.daview.dto.CaregiverTaskDTO;
import java.time.LocalDate;
import java.util.List;

public interface CaregiverTaskService {
    
    // 일정 목록 조회 (페이징)
    List<CaregiverTaskDTO> getTasksByCaregiverId(Long caregiverId, int page, int size);
    
    // 일정 총 개수
    int getTasksCountByCaregiverId(Long caregiverId);
    
    // 특정 날짜의 일정 목록 조회
    List<CaregiverTaskDTO> getTasksByDate(Long caregiverId, LocalDate taskDate);
    
    // 일정 상세 조회
    CaregiverTaskDTO getTaskById(Long id);
    
    // 일정 생성
    CaregiverTaskDTO createTask(CaregiverTaskDTO task);
    
    // 일정 수정
    CaregiverTaskDTO updateTask(CaregiverTaskDTO task);
    
    // 일정 삭제
    boolean deleteTask(Long id);
    
    // 일정 완료 상태 토글
    boolean toggleTaskCompletion(Long id, Boolean completed);
    
    // 오늘의 일정 목록 조회
    List<CaregiverTaskDTO> getTodayTasks(Long caregiverId);
    
    // 이번 주 일정 목록 조회
    List<CaregiverTaskDTO> getWeeklyTasks(Long caregiverId);
    
    // 특정 기간의 일정 목록 조회
    List<CaregiverTaskDTO> getTasksByDateRange(Long caregiverId, LocalDate startDate, LocalDate endDate);
    
    // 완료/미완료 일정 목록 조회
    List<CaregiverTaskDTO> getTasksByCompletion(Long caregiverId, Boolean completed);
    
    // 일정 타입별 목록 조회
    List<CaregiverTaskDTO> getTasksByType(Long caregiverId, String taskType);
    
    // 최근 일정 목록 조회 (대시보드용)
    List<CaregiverTaskDTO> getRecentTasks(Long caregiverId, int limit);
    
    // 오늘의 완료된 일정 개수
    int getTodayCompletedTasksCount(Long caregiverId);
    
    // 이번 주 총 일정 개수
    int getWeeklyTasksCount(Long caregiverId);
} 