package com.daview.controller.admin;

import com.daview.dto.AdminReviewDTO;
import com.daview.service.admin.AdminReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 관리자용 후기 관리 컨트롤러
 * HTTP 요청을 받아 적절한 서비스로 전달하고 응답을 반환
 */
@RestController
@RequestMapping("/api/admin/reviews")
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    @Autowired
    public AdminReviewController(AdminReviewService adminReviewService) {
        this.adminReviewService = adminReviewService;
    }

    /**
     * 전체 후기 목록 조회 (페이지네이션과 필터링 지원)
     * GET /api/admin/reviews
     */
    @GetMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getAllReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "revRegDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer stars,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        try {
            System.out.println("=== AdminReviewController: 전체 후기 목록 조회 요청 ===");
            System.out.println("페이지: " + page + ", 크기: " + size + ", 정렬: " + sortBy + " " + sortDirection);
            System.out.println("필터 - 검색어: " + search + ", 평점: " + stars + ", 시작일: " + startDate + ", 종료일: " + endDate);
            
            Map<String, Object> result = adminReviewService.getAllReviewsWithPagination(
                page, size, sortBy, sortDirection, search, stars, startDate, endDate);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", result);
            
            @SuppressWarnings("unchecked")
            List<AdminReviewDTO> reviews = (List<AdminReviewDTO>) result.get("reviews");
            System.out.println("조회된 후기 수: " + reviews.size());
            System.out.println("전체 후기 수: " + result.get("totalElements"));
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("후기 목록 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "후기 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 특정 후기 상세 정보 조회
     * GET /api/admin/reviews/{id}
     */
    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getReviewDetail(@PathVariable("id") Long id) {
        try {
            System.out.println("=== 후기 상세 조회 요청: " + id + " ===");
            
            Map<String, Object> reviewDetail = adminReviewService.getReviewDetail(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", reviewDetail);
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("후기 상세 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "후기 상세 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 후기 삭제
     * DELETE /api/admin/reviews/{reviewId}
     */
    @DeleteMapping(value = "/{reviewId}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> deleteReview(@PathVariable Long reviewId) {
        try {
            System.out.println("=== 후기 삭제 요청 ===");
            System.out.println("후기 ID: " + reviewId);
            
            boolean success = adminReviewService.deleteReview(reviewId);
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("success", true);
                response.put("message", "후기가 성공적으로 삭제되었습니다.");
            } else {  
                response.put("success", false);
                response.put("message", "후기 삭제에 실패했습니다.");
            }
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("후기 삭제 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "후기 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 후기 통계 조회
     * GET /api/admin/reviews/statistics
     */
    @GetMapping(value = "/statistics", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getReviewStatistics() {
        try {
            System.out.println("=== 후기 통계 조회 요청 ===");
            
            Map<String, Object> statistics = adminReviewService.getReviewStatistics();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", statistics);
            
            System.out.println("통계 조회 완료");
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("후기 통계 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "후기 통계 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 월별 후기 작성 수 통계 조회
     * GET /api/admin/reviews/statistics/monthly
     */
    @GetMapping(value = "/statistics/monthly", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getMonthlyReviewStats() {
        try {
            System.out.println("=== 월별 후기 통계 조회 요청 ===");
            
            List<Map<String, Object>> monthlyStats = adminReviewService.getMonthlyReviewStats();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", monthlyStats);
            
            System.out.println("월별 후기 통계 조회 완료 - 데이터 수: " + monthlyStats.size());
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("월별 후기 통계 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "월별 후기 통계 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }
} 