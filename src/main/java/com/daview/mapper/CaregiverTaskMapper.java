package com.daview.mapper;

import com.daview.dto.CaregiverTaskDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CaregiverTaskMapper {
    
    // 특정 요양사의 일정 목록 조회 (페이징)
    List<CaregiverTaskDTO> getTasksByCaregiverId(@Param("caregiverId") String caregiverId,
                                                 @Param("offset") int offset,
                                                 @Param("limit") int limit);
    
    // 특정 요양사의 일정 총 개수
    int getTasksCountByCaregiverId(@Param("caregiverId") String caregiverId);
    
    // 특정 날짜의 일정 목록 조회
    List<CaregiverTaskDTO> getTasksByDate(@Param("caregiverId") String caregiverId,
                                          @Param("taskDate") LocalDate taskDate);
    
    // 일정 상세 조회
    CaregiverTaskDTO getTaskById(@Param("id") Long id);
    
    // 일정 생성
    int insertTask(CaregiverTaskDTO task);
    
    // 일정 수정
    int updateTask(CaregiverTaskDTO task);
    
    // 일정 삭제
    int deleteTask(@Param("id") Long id);
    
    // 일정 완료 상태 토글
    int toggleTaskCompletion(@Param("id") Long id, @Param("completed") Boolean completed);
    
    // 오늘의 일정 목록 조회
    List<CaregiverTaskDTO> getTodayTasks(@Param("caregiverId") String caregiverId);
    
    // 이번 주 일정 목록 조회
    List<CaregiverTaskDTO> getWeeklyTasks(@Param("caregiverId") String caregiverId,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate);
    
    // 특정 기간의 일정 목록 조회
    List<CaregiverTaskDTO> getTasksByDateRange(@Param("caregiverId") String caregiverId,
                                               @Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);
    
    // 완료/미완료 일정 목록 조회
    List<CaregiverTaskDTO> getTasksByCompletion(@Param("caregiverId") String caregiverId,
                                                @Param("completed") Boolean completed);
    
    // 일정 타입별 목록 조회
    List<CaregiverTaskDTO> getTasksByType(@Param("caregiverId") String caregiverId,
                                          @Param("taskType") String taskType);
    
    // 최근 일정 목록 조회 (대시보드용)
    List<CaregiverTaskDTO> getRecentTasks(@Param("caregiverId") String caregiverId,
                                          @Param("limit") int limit);
    
    // 오늘의 완료된 일정 개수
    int getTodayCompletedTasksCount(@Param("caregiverId") String caregiverId);
    
    // 이번 주 총 일정 개수
    int getWeeklyTasksCount(@Param("caregiverId") String caregiverId,
                            @Param("startDate") LocalDate startDate,
                            @Param("endDate") LocalDate endDate);
} 