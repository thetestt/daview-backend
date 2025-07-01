package com.daview.service.admin;

import com.daview.dto.ReservationDTO;

import java.util.List;
import java.util.Map;

/**
 * 관리자용 예약 관리 서비스 인터페이스
 * 예약 조회, 상태 관리, 통계 등의 비즈니스 로직 정의
 */
public interface AdminReservationService {
    
    /**
     * 전체 예약 목록 조회 (페이지네이션과 필터링 지원)
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     * @param sortBy 정렬 기준
     * @param sortDirection 정렬 방향
     * @param status 상태 필터
     * @param search 검색어
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @param prodType 상품 타입 필터
     * @return 페이징된 예약 목록과 메타 데이터
     */
    Map<String, Object> getAllReservationsWithPagination(
        int page, int size, String sortBy, String sortDirection,
        String status, String search, String startDate, String endDate, String prodType
    );
    
    /**
     * 특정 예약 상세 정보 조회
     * @param rsvId 예약 ID
     * @return 예약 상세 정보
     */
    Map<String, Object> getReservationDetail(String rsvId);
    
    /**
     * 예약 상태 변경
     * @param rsvId 예약 ID
     * @param status 새로운 상태
     * @return 변경 성공 여부
     */
    boolean updateReservationStatus(String rsvId, String status);
    
    /**
     * 전체 예약 통계 조회
     * @return 통계 데이터 맵
     */
    Map<String, Object> getReservationStatistics();
    
    /**
     * 월별 예약 수 통계 조회
     * @return 월별 예약 수 리스트
     */
    List<Map<String, Object>> getMonthlyReservationStatistics();
    
    /**
     * 예약 상태별 통계 조회
     * @return 상태별 예약 통계 데이터
     */
    Map<String, Object> getReservationStatusStatistics();
    
    /**
     * 예약 삭제 (관리자용)
     * @param rsvId 예약 ID
     * @return 삭제 성공 여부
     */
    boolean deleteReservation(String rsvId);
    
    /**
     * 정렬 필드 유효성 검증
     * @param sortBy 정렬 필드
     * @return 유효한 정렬 필드
     */
    String validateSortField(String sortBy);
} 