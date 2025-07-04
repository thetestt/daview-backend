package com.daview.mapper;

import com.daview.dto.CaregiverReportDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CaregiverReportMapper {
    
    // 특정 요양사의 보고서 목록 조회 (페이징)
    List<CaregiverReportDTO> getReportsByCaregiverId(@Param("caregiverId") String caregiverId,
                                                     @Param("offset") int offset,
                                                     @Param("limit") int limit);
    
    // 특정 요양사의 보고서 총 개수
    int getReportsCountByCaregiverId(@Param("caregiverId") String caregiverId);
    
    // 특정 날짜의 보고서 조회
    CaregiverReportDTO getReportByDate(@Param("caregiverId") String caregiverId,
                                       @Param("reportDate") LocalDate reportDate);
    
    // 보고서 상세 조회
    CaregiverReportDTO getReportById(@Param("id") Long id);
    
    // 보고서 생성
    int insertReport(CaregiverReportDTO report);
    
    // 보고서 수정
    int updateReport(CaregiverReportDTO report);
    
    // 보고서 삭제
    int deleteReport(@Param("id") Long id);
    
    // 보고서 상태 변경
    int updateReportStatus(@Param("id") Long id, @Param("status") String status);
    
    // 최근 보고서 목록 조회 (대시보드용)
    List<CaregiverReportDTO> getRecentReports(@Param("caregiverId") String caregiverId,
                                              @Param("limit") int limit);
    
    // 특정 기간의 보고서 목록 조회
    List<CaregiverReportDTO> getReportsByDateRange(@Param("caregiverId") String caregiverId,
                                                   @Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate);
    
    // 상태별 보고서 목록 조회
    List<CaregiverReportDTO> getReportsByStatus(@Param("caregiverId") String caregiverId,
                                                @Param("status") String status);
} 