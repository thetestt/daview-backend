package com.daview.mapper.admin;

import com.daview.dto.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 관리자용 예약 관리 매퍼 인터페이스
 */
@Mapper
public interface AdminReservationMapper {
    
    /**
     * 페이지네이션과 필터링을 적용한 예약 목록 조회
     */
    List<Map<String, Object>> getAllReservationsWithPagination(Map<String, Object> params);
    
    /**
     * 필터링 조건에 맞는 예약 총 개수 조회
     */
    int getReservationCount(Map<String, Object> params);
    
    /**
     * 특정 예약 상세 정보 조회
     */
    Map<String, Object> getReservationDetail(String rsvId);
    
    /**
     * 예약 상태 변경
     */
    int updateReservationStatus(Map<String, Object> params);
    
    /**
     * 전체 예약 개수 조회
     */
    int getTotalReservationCount();
    
    /**
     * 상태별 예약 개수 조회
     */
    List<Map<String, Object>> getReservationsByStatus();
    
    /**
     * 이번 달 예약 개수 조회
     */
    int getThisMonthReservationCount();
    
    /**
     * 오늘 예약 개수 조회
     */
    int getTodayReservationCount();
    
    /**
     * 월별 예약 통계 조회
     */
    List<Map<String, Object>> getMonthlyReservationStats();
    
    /**
     * 예약 삭제
     */
    int deleteReservation(String rsvId);
} 