package com.daview.controller.admin;

import com.daview.dto.ReservationDTO;
import com.daview.service.admin.AdminReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 관리자용 예약 관리 컨트롤러
 * HTTP 요청을 받아 적절한 서비스로 전달하고 응답을 반환
 */
@RestController
@RequestMapping("/api/admin/reservations")
public class AdminReservationController {

    private final AdminReservationService adminReservationService;

    @Autowired
    public AdminReservationController(AdminReservationService adminReservationService) {
        this.adminReservationService = adminReservationService;
    }

    /**
     * 전체 예약 목록 조회 (페이지네이션과 필터링 지원)
     * GET /api/admin/reservations
     */
    @GetMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getAllReservations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "rsvDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String prodType) {
        try {
            System.out.println("=== AdminReservationController: 전체 예약 목록 조회 요청 ===");
            System.out.println("페이지: " + page + ", 크기: " + size + ", 정렬: " + sortBy + " " + sortDirection);
            System.out.println("필터 - 상태: " + status + ", 검색어: " + search + ", 시작일: " + startDate + ", 종료일: " + endDate + ", 상품타입: " + prodType);
            
            Map<String, Object> result = adminReservationService.getAllReservationsWithPagination(
                page, size, sortBy, sortDirection, status, search, startDate, endDate, prodType);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", result);
            
            @SuppressWarnings("unchecked")
            List<ReservationDTO> reservations = (List<ReservationDTO>) result.get("reservations");
            System.out.println("조회된 예약 수: " + reservations.size());
            System.out.println("전체 예약 수: " + result.get("totalElements"));
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("예약 목록 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "예약 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 특정 예약 상세 정보 조회
     * GET /api/admin/reservations/{id}
     */
    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getReservationDetail(@PathVariable("id") String rsvId) {
        try {
            System.out.println("=== 예약 상세 조회 요청: " + rsvId + " ===");
            
            Map<String, Object> reservationDetail = adminReservationService.getReservationDetail(rsvId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", reservationDetail);
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("예약 상세 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "예약 상세 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 예약 상태 변경
     * PATCH /api/admin/reservations/{rsvId}/status
     */
    @PatchMapping(value = "/{rsvId}/status", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> updateReservationStatus(
            @PathVariable String rsvId, 
            @RequestBody Map<String, Object> statusData) {
        try {
            String newStatus = (String) statusData.get("status");
            System.out.println("=== 예약 상태 변경 요청 ===");
            System.out.println("예약 ID: " + rsvId + ", 새 상태: " + newStatus);
            
            boolean success = adminReservationService.updateReservationStatus(rsvId, newStatus);
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("success", true);
                response.put("message", "예약 상태가 성공적으로 변경되었습니다.");
            } else {  
                response.put("success", false);
                response.put("message", "예약 상태 변경에 실패했습니다.");
            }
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("예약 상태 변경 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "예약 상태 변경에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 전체 예약 통계 조회
     * GET /api/admin/reservations/statistics
     */
    @GetMapping(value = "/statistics", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getReservationStatistics() {
        try {
            System.out.println("=== 예약 통계 조회 요청 ===");
            
            Map<String, Object> statistics = adminReservationService.getReservationStatistics();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", statistics);
            
            System.out.println("통계 조회 완료");
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("예약 통계 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "예약 통계 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 월별 예약 수 통계 조회
     * GET /api/admin/reservations/statistics/monthly
     */
    @GetMapping(value = "/statistics/monthly", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getMonthlyReservationStatistics() {
        try {
            System.out.println("=== 월별 예약 통계 조회 요청 ===");
            
            List<Map<String, Object>> monthlyStats = adminReservationService.getMonthlyReservationStatistics();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", monthlyStats);
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("월별 예약 통계 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "월별 예약 통계 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 예약 상태별 통계 조회
     * GET /api/admin/reservations/statistics/status
     */
    @GetMapping(value = "/statistics/status", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getReservationStatusStatistics() {
        try {
            System.out.println("=== 예약 상태별 통계 조회 요청 ===");
            
            Map<String, Object> statusStats = adminReservationService.getReservationStatusStatistics();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", statusStats);
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("예약 상태별 통계 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "예약 상태별 통계 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 예약 삭제 (관리자용)
     * DELETE /api/admin/reservations/{rsvId}
     */
    @DeleteMapping(value = "/{rsvId}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> deleteReservation(@PathVariable String rsvId) {
        try {
            System.out.println("=== 예약 삭제 요청: " + rsvId + " ===");
            
            boolean success = adminReservationService.deleteReservation(rsvId);
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("success", true);
                response.put("message", "예약이 성공적으로 삭제되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "예약 삭제에 실패했습니다.");
            }
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("예약 삭제 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "예약 삭제에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }
} 