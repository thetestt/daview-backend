package com.daview.service;

import com.daview.dto.CaregiverReportDTO;
import java.time.LocalDate;
import java.util.List;

public interface CaregiverReportService {
    
    // 보고서 목록 조회 (페이징)
    List<CaregiverReportDTO> getReportsByCaregiverId(String caregiverId, int page, int size);
    
    // 보고서 총 개수
    int getReportsCountByCaregiverId(String caregiverId);
    
    // 특정 날짜의 보고서 조회
    CaregiverReportDTO getReportByDate(String caregiverId, LocalDate reportDate);
    
    // 보고서 상세 조회
    CaregiverReportDTO getReportById(Long id);
    
    // 보고서 생성
    CaregiverReportDTO createReport(CaregiverReportDTO report);
    
    // 보고서 수정
    CaregiverReportDTO updateReport(CaregiverReportDTO report);
    
    // 보고서 삭제
    boolean deleteReport(Long id);
    
    // 보고서 상태 변경
    boolean updateReportStatus(Long id, String status);
    
    // 최근 보고서 목록 조회 (대시보드용)
    List<CaregiverReportDTO> getRecentReports(String caregiverId, int limit);
    
    // 특정 기간의 보고서 목록 조회
    List<CaregiverReportDTO> getReportsByDateRange(String caregiverId, LocalDate startDate, LocalDate endDate);
    
    // 상태별 보고서 목록 조회
    List<CaregiverReportDTO> getReportsByStatus(String caregiverId, String status);
} 