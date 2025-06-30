package com.daview.service.admin;

import com.daview.dto.AdminReviewDTO;

import java.util.List;
import java.util.Map;

/**
 * 관리자용 후기 관리 서비스 인터페이스
 * 후기 조회, 삭제, 통계 등의 비즈니스 로직 정의
 */
public interface AdminReviewService {
    
    /**
     * 전체 후기 목록 조회 (페이지네이션과 필터링 지원)
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     * @param sortBy 정렬 기준
     * @param sortDirection 정렬 방향
     * @param search 검색어
     * @param stars 평점 필터
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 페이징된 후기 목록과 메타 데이터
     */
    Map<String, Object> getAllReviewsWithPagination(
        int page, int size, String sortBy, String sortDirection,
        String search, Integer stars, String startDate, String endDate
    );
    
    /**
     * 특정 후기 상세 정보 조회
     * @param revId 후기 ID
     * @return 후기 상세 정보
     */
    Map<String, Object> getReviewDetail(Long revId);
    
    /**
     * 후기 삭제
     * @param revId 후기 ID
     * @return 삭제 성공 여부
     */
    boolean deleteReview(Long revId);
    
    /**
     * 후기 통계 조회
     * @return 통계 데이터 맵
     */
    Map<String, Object> getReviewStatistics();
    
    /**
     * 월별 후기 작성 수 통계 조회
     * @return 월별 후기 작성 수 리스트
     */
    List<Map<String, Object>> getMonthlyReviewStats();
    
    /**
     * 정렬 필드 유효성 검증
     * @param sortBy 정렬 필드
     * @return 유효한 정렬 필드
     */
    String validateSortField(String sortBy);
} 