package com.daview.service.admin.impl;

import com.daview.dto.AdminReviewDTO;
import com.daview.mapper.admin.AdminReviewMapper;
import com.daview.service.admin.AdminReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 관리자용 후기 관리 서비스 구현체
 */
@Service
public class AdminReviewServiceImpl implements AdminReviewService {

    private final AdminReviewMapper adminReviewMapper;

    // 정렬 가능한 필드 목록
    private static final Set<String> VALID_SORT_FIELDS = Set.of(
        "revRegDate", "revStars", "revViews", "memberName", "prodNm"
    );

    @Autowired
    public AdminReviewServiceImpl(AdminReviewMapper adminReviewMapper) {
        this.adminReviewMapper = adminReviewMapper;
    }

    @Override
    public Map<String, Object> getAllReviewsWithPagination(
            int page, int size, String sortBy, String sortDirection,
            String search, Integer stars, String startDate, String endDate) {
        try {
            System.out.println("=== AdminReviewService: 후기 목록 조회 시작 ===");
            System.out.println("페이지: " + page + ", 크기: " + size + ", 정렬: " + sortBy + " " + sortDirection);
            System.out.println("필터 - 검색어: " + search + ", 평점: " + stars + ", 시작일: " + startDate + ", 종료일: " + endDate);

            // 입력값 검증
            if (page < 0) page = 0;
            if (size <= 0 || size > 100) size = 10;
            
            sortBy = validateSortField(sortBy);
            
            if (!"ASC".equalsIgnoreCase(sortDirection) && !"DESC".equalsIgnoreCase(sortDirection)) {
                sortDirection = "DESC";
            }

            // 페이지네이션 계산
            int offset = page * size;

            // 데이터베이스 조회
            List<AdminReviewDTO> reviews = adminReviewMapper.findAllReviewsForAdmin(
                offset, size, sortBy, sortDirection, search, stars, startDate, endDate);
            
            int totalElements = adminReviewMapper.countReviewsForAdmin(search, stars, startDate, endDate);

            // 페이지네이션 메타데이터 계산
            int totalPages = (int) Math.ceil((double) totalElements / size);
            boolean hasNext = page < totalPages - 1;
            boolean hasPrevious = page > 0;

            // 결과 맵 구성
            Map<String, Object> result = new HashMap<>();
            result.put("reviews", reviews);
            result.put("totalElements", totalElements);
            result.put("totalPages", totalPages);
            result.put("currentPage", page);
            result.put("pageSize", size);
            result.put("hasNext", hasNext);
            result.put("hasPrevious", hasPrevious);

            System.out.println("조회된 후기 수: " + reviews.size());
            System.out.println("전체 후기 수: " + totalElements);

            return result;

        } catch (Exception e) {
            System.err.println("=== AdminReviewService: 후기 목록 조회 오류 ===");
            e.printStackTrace();
            throw new RuntimeException("후기 목록 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getReviewDetail(Long revId) {
        try {
            System.out.println("=== AdminReviewService: 후기 상세 조회 - ID: " + revId + " ===");

            // 입력값 검증
            if (revId == null || revId <= 0) {
                throw new IllegalArgumentException("유효하지 않은 후기 ID입니다.");
            }

            AdminReviewDTO review = adminReviewMapper.findReviewDetailById(revId);

            if (review == null) {
                throw new RuntimeException("해당 ID의 후기를 찾을 수 없습니다: " + revId);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("review", review);

            System.out.println("후기 상세 조회 완료 - " + review.getRevTtl());

            return result;

        } catch (Exception e) {
            System.err.println("=== AdminReviewService: 후기 상세 조회 오류 ===");
            e.printStackTrace();
            throw new RuntimeException("후기 상세 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteReview(Long revId) {
        try {
            System.out.println("=== AdminReviewService: 후기 삭제 ===");
            System.out.println("후기 ID: " + revId);

            // 입력값 검증
            if (revId == null || revId <= 0) {
                throw new IllegalArgumentException("유효하지 않은 후기 ID입니다.");
            }

            // 후기 존재 여부 확인
            AdminReviewDTO review = adminReviewMapper.findReviewDetailById(revId);
            if (review == null) {
                throw new RuntimeException("해당 ID의 후기를 찾을 수 없습니다: " + revId);
            }

            // 데이터베이스 삭제
            int deletedRows = adminReviewMapper.deleteReview(revId);

            boolean success = deletedRows > 0;
            System.out.println("후기 삭제 " + (success ? "성공" : "실패") + " - 영향받은 행: " + deletedRows);

            return success;

        } catch (Exception e) {
            System.err.println("=== AdminReviewService: 후기 삭제 오류 ===");
            e.printStackTrace();
            throw new RuntimeException("후기 삭제 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getReviewStatistics() {
        try {
            System.out.println("=== AdminReviewService: 후기 통계 조회 ===");

            // 평점별 통계
            List<Map<String, Object>> starsStats = adminReviewMapper.getReviewCountByStars();

            // 전체 후기 수
            int totalReviews = adminReviewMapper.getTotalReviewCount();

            // 평균 평점
            Double averageRating = adminReviewMapper.getAverageRating();
            if (averageRating == null) averageRating = 0.0;

            // 결과 맵 생성
            Map<String, Object> result = new HashMap<>();
            result.put("starsStatistics", starsStats);
            result.put("totalReviews", totalReviews);
            result.put("averageRating", Math.round(averageRating * 10) / 10.0); // 소수점 1자리

            System.out.println("통계 조회 완료 - 총 후기: " + totalReviews + ", 평균 평점: " + averageRating);

            return result;

        } catch (Exception e) {
            System.err.println("=== AdminReviewService: 후기 통계 조회 오류 ===");
            e.printStackTrace();
            throw new RuntimeException("후기 통계 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> getMonthlyReviewStats() {
        try {
            System.out.println("=== AdminReviewService: 월별 후기 통계 조회 ===");

            List<Map<String, Object>> monthlyStats = adminReviewMapper.getMonthlyReviewStats();

            System.out.println("월별 후기 통계 조회 완료 - 데이터 수: " + monthlyStats.size());

            return monthlyStats;

        } catch (Exception e) {
            System.err.println("=== AdminReviewService: 월별 후기 통계 조회 오류 ===");
            e.printStackTrace();
            throw new RuntimeException("월별 후기 통계 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public String validateSortField(String sortBy) {
        if (sortBy == null || sortBy.trim().isEmpty()) {
            return "revRegDate"; // 기본값
        }

        String trimmedSortBy = sortBy.trim();
        if (VALID_SORT_FIELDS.contains(trimmedSortBy)) {
            return trimmedSortBy;
        }

        System.out.println("유효하지 않은 정렬 필드: " + sortBy + ", 기본값 사용: revRegDate");
        return "revRegDate"; // 기본값으로 대체
    }
} 