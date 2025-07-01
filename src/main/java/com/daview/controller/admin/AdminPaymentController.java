package com.daview.controller.admin;

import com.daview.dto.PaymentDTO;
import com.daview.service.admin.AdminPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/payments")
@CrossOrigin(origins = "*")
public class AdminPaymentController {

    @Autowired
    private AdminPaymentService adminPaymentService;

    // 결제 목록 조회 (페이징, 검색, 필터링)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPaymentList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer paymentMethod,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "pym_date") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("search", search);
            params.put("status", status);
            params.put("paymentMethod", paymentMethod);
            params.put("startDate", startDate);
            params.put("endDate", endDate);
            params.put("sortBy", sortBy);
            params.put("sortDir", sortDir);
            params.put("offset", page * size);
            params.put("limit", size);
            
            Map<String, Object> result = adminPaymentService.getPaymentList(params);
            
            Map<String, Object> response = new HashMap<>();
            response.put("content", result.get("payments"));
            response.put("totalElements", result.get("totalCount"));
            response.put("totalPages", (int) Math.ceil((Long) result.get("totalCount") / (double) size));
            response.put("currentPage", page);
            response.put("size", size);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "결제 목록 조회 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    // 결제 상세 조회
    @GetMapping("/{paymentId}")
    public ResponseEntity<Map<String, Object>> getPaymentDetail(@PathVariable String paymentId) {
        try {
            PaymentDTO payment = adminPaymentService.getPaymentById(paymentId);
            
            Map<String, Object> response = new HashMap<>();
            if (payment != null) {
                response.put("payment", payment);
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "결제 정보를 찾을 수 없습니다.");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "결제 상세 조회 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    // 결제 상태 변경
    @PutMapping("/{paymentId}/status")
    public ResponseEntity<Map<String, Object>> updatePaymentStatus(
            @PathVariable String paymentId, 
            @RequestBody Map<String, Object> request) {
        
        try {
            Object statusObj = request.get("status");
            if (statusObj == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "결제 상태가 필요합니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            Integer newStatus = Integer.valueOf(statusObj.toString());
            boolean success = adminPaymentService.updatePaymentStatus(paymentId, newStatus);
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("message", "결제 상태가 성공적으로 변경되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "결제 상태 변경에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "결제 상태 변경 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    // 전체 결제 통계 조회
    @GetMapping("/statistics/total")
    public ResponseEntity<Map<String, Object>> getPaymentTotalStatistics() {
        try {
            Map<String, Object> statistics = adminPaymentService.getPaymentTotalStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "전체 결제 통계 조회 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    // 상태별 결제 통계
    @GetMapping("/statistics/status")
    public ResponseEntity<List<Map<String, Object>>> getPaymentStatusStatistics() {
        try {
            List<Map<String, Object>> statistics = adminPaymentService.getPaymentStatusStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 최근 7일 결제 통계
    @GetMapping("/statistics/recent")
    public ResponseEntity<List<Map<String, Object>>> getRecentPaymentStatistics() {
        try {
            List<Map<String, Object>> statistics = adminPaymentService.getRecentPaymentStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 결제 방법별 통계
    @GetMapping("/statistics/methods")
    public ResponseEntity<List<Map<String, Object>>> getPaymentMethodStatistics() {
        try {
            List<Map<String, Object>> statistics = adminPaymentService.getPaymentMethodStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 월별 결제 통계
    @GetMapping("/statistics/monthly")
    public ResponseEntity<List<Map<String, Object>>> getMonthlyPaymentStatistics() {
        try {
            List<Map<String, Object>> statistics = adminPaymentService.getMonthlyPaymentStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 회원별 결제 통계
    @GetMapping("/statistics/members")
    public ResponseEntity<List<Map<String, Object>>> getMemberPaymentStatistics() {
        try {
            List<Map<String, Object>> statistics = adminPaymentService.getMemberPaymentStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 결제 방법 목록 조회
    @GetMapping("/methods")
    public ResponseEntity<List<Map<String, Object>>> getPaymentMethods() {
        try {
            List<Map<String, Object>> methods = adminPaymentService.getPaymentMethods();
            return ResponseEntity.ok(methods);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 예약 타입 목록 조회
    @GetMapping("/reservation-types")
    public ResponseEntity<List<Map<String, Object>>> getReservationTypes() {
        try {
            List<Map<String, Object>> types = adminPaymentService.getReservationTypes();
            return ResponseEntity.ok(types);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // 결제-예약 연결 생성
    @PostMapping("/{paymentId}/reservations/{reservationId}")
    public ResponseEntity<Map<String, Object>> createPaymentReservationMap(
            @PathVariable String paymentId,
            @PathVariable String reservationId) {
        
        try {
            boolean success = adminPaymentService.createPaymentReservationMap(paymentId, reservationId);
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("message", "결제-예약 연결이 생성되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "결제-예약 연결 생성에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "결제-예약 연결 생성 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    // 결제-예약 연결 삭제
    @DeleteMapping("/{paymentId}/reservations/{reservationId}")
    public ResponseEntity<Map<String, Object>> deletePaymentReservationMap(
            @PathVariable String paymentId,
            @PathVariable String reservationId) {
        
        try {
            boolean success = adminPaymentService.deletePaymentReservationMap(paymentId, reservationId);
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("message", "결제-예약 연결이 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "결제-예약 연결 삭제에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "결제-예약 연결 삭제 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
} 