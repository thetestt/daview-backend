package com.daview.mapper.admin;

import com.daview.dto.AdminReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminReviewMapper {
    
    // 전체 후기 목록 조회 (페이지네이션 지원)
    List<AdminReviewDTO> findAllReviewsForAdmin(
        @Param("offset") int offset,
        @Param("limit") int limit,
        @Param("sortBy") String sortBy,
        @Param("sortDirection") String sortDirection,
        @Param("search") String search,
        @Param("stars") Integer stars,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate
    );
    
    // 전체 후기 수 조회 (필터링 조건 적용)
    int countReviewsForAdmin(
        @Param("search") String search,
        @Param("stars") Integer stars,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate
    );
    
    // 특정 후기 상세 정보 조회
    AdminReviewDTO findReviewDetailById(@Param("revId") Long revId);
    
    // 후기 삭제
    int deleteReview(@Param("revId") Long revId);
    
    // 평점별 후기 수 통계
    List<Map<String, Object>> getReviewCountByStars();
    
    // 월별 후기 작성 수 통계 (최근 12개월)
    List<Map<String, Object>> getMonthlyReviewStats();
    
    // 평균 평점 조회
    Double getAverageRating();
    
    // 총 후기 수 조회
    int getTotalReviewCount();
} 