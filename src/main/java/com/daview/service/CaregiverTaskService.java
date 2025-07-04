package com.daview.service;

import com.daview.dto.CaregiverTaskDTO;
import java.time.LocalDate;
import java.util.List;

public interface CaregiverTaskService {
    
    // 일정 목록 조회 (페이징)
    List<CaregiverTaskDTO> getTasksByCaregiverId(String caregiverId, int page, int size);
    
    // 일정 총 개수
    int getTasksCountByCaregiverId(String caregiverId);
    
    // 특정 날짜의 일정 목록 조회
    List<CaregiverTaskDTO> getTasksByDate(String caregiverId, LocalDate taskDate);
    
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
    List<CaregiverTaskDTO> getTodayTasks(String caregiverId);
    
    // 이번 주 일정 목록 조회
    List<CaregiverTaskDTO> getWeeklyTasks(String caregiverId);
    
    // 특정 기간의 일정 목록 조회
    List<CaregiverTaskDTO> getTasksByDateRange(String caregiverId, LocalDate startDate, LocalDate endDate);
    
    // 완료/미완료 일정 목록 조회
    List<CaregiverTaskDTO> getTasksByCompletion(String caregiverId, Boolean completed);
    
    // 일정 타입별 목록 조회
    List<CaregiverTaskDTO> getTasksByType(String caregiverId, String taskType);
    
    // 최근 일정 목록 조회 (대시보드용)
    List<CaregiverTaskDTO> getRecentTasks(String caregiverId, int limit);
    
    // 오늘의 완료된 일정 개수
    int getTodayCompletedTasksCount(String caregiverId);
    
    // 이번 주 총 일정 개수
    int getWeeklyTasksCount(String caregiverId);
} 