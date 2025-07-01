package com.daview.controller.admin;

import com.daview.dto.AdminUserDto;
import com.daview.service.admin.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 관리자용 유저 관리 컨트롤러
 * HTTP 요청을 받아 적절한 서비스로 전달하고 응답을 반환
 */
@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @Autowired
    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    /**
     * 전체 유저 목록 조회 (페이지네이션과 필터링 지원)
     * GET /api/admin/users
     */
    @GetMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        try {
            System.out.println("=== AdminUserController: 전체 유저 목록 조회 요청 ===");
            System.out.println("페이지: " + page + ", 크기: " + size + ", 정렬: " + sortBy + " " + sortDirection);
            System.out.println("필터 - 역할: " + role + ", 검색어: " + search + ", 시작일: " + startDate + ", 종료일: " + endDate);
            
            Map<String, Object> result = adminUserService.getAllUsersWithPagination(
                page, size, sortBy, sortDirection, role, search, startDate, endDate);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", result);
            
            @SuppressWarnings("unchecked")
            List<AdminUserDto> users = (List<AdminUserDto>) result.get("users");
            System.out.println("조회된 유저 수: " + users.size());
            System.out.println("전체 유저 수: " + result.get("totalElements"));
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("유저 목록 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "유저 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 특정 유저 상세 정보 조회
     * GET /api/admin/users/{id}
     */
    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getUserDetail(@PathVariable("id") Long id) {
        try {
            System.out.println("=== 유저 상세 조회 요청: " + id + " ===");
            
            Map<String, Object> userDetail = adminUserService.getUserDetail(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", userDetail);
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("유저 상세 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "유저 상세 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 유저 상태 변경
     * PATCH /api/admin/users/{userId}/status
     */
    @PatchMapping(value = "/{userId}/status", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> updateUserStatus(
            @PathVariable String userId, 
            @RequestBody Map<String, Object> statusData) {
        try {
            String newStatus = (String) statusData.get("status");
            System.out.println("=== 유저 상태 변경 요청 ===");
            System.out.println("유저 ID: " + userId + ", 새 상태: " + newStatus);
            
            boolean success = adminUserService.updateUserStatus(userId, newStatus);
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("success", true);
                response.put("message", "유저 상태가 성공적으로 변경되었습니다.");
            } else {  
                response.put("success", false);
                response.put("message", "유저 상태 변경에 실패했습니다.");
            }
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("유저 상태 변경 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "유저 상태 변경에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 유저 탈퇴 처리
     * PATCH /api/admin/users/{userId}/withdraw
     */
    @PatchMapping(value = "/{userId}/withdraw", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> withdrawUser(@PathVariable String userId) {
        try {
            System.out.println("=== 유저 탈퇴 처리 요청 ===");
            System.out.println("유저 ID: " + userId);
            
            boolean success = adminUserService.withdrawUser(userId);
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("success", true);
                response.put("message", "유저가 성공적으로 탈퇴 처리되었습니다.");
            } else {  
                response.put("success", false);
                response.put("message", "유저 탈퇴 처리에 실패했습니다.");
            }
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("유저 탈퇴 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "유저 탈퇴 처리에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 전체 유저 통계 조회
     * GET /api/admin/users/statistics
     */
    @GetMapping(value = "/statistics", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getUserStatistics() {
        try {
            System.out.println("=== 유저 통계 조회 요청 ===");
            
            Map<String, Object> statistics = adminUserService.getUserStatistics();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", statistics);
            
            System.out.println("통계 조회 완료");
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("유저 통계 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "유저 통계 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 월별 가입자 수 통계 조회
     * GET /api/admin/users/statistics/signup
     */
    @GetMapping(value = "/statistics/signup", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getSignupStatistics() {
        try {
            System.out.println("=== 가입 통계 조회 요청 ===");
            
            List<Map<String, Object>> signupStats = adminUserService.getSignupStatistics();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", signupStats);
            
            System.out.println("가입 통계 조회 완료 - 데이터 수: " + signupStats.size());
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("가입 통계 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "가입 통계 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 신규 가입자 통계 조회 (일/주/월별)
     * GET /api/admin/users/statistics/new-users
     */
    @GetMapping(value = "/statistics/new-users", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getNewUserStatistics() {
        try {
            System.out.println("=== 신규 가입자 통계 조회 요청 ===");
            
            Map<String, Object> newUserStats = adminUserService.getNewUserStatistics();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("data", newUserStats);
            
            System.out.println("신규 가입자 통계 조회 완료");
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("신규 가입자 통계 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "신규 가입자 통계 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    /**
     * 특정 역할의 유저 목록 조회 (상품 등록 시 사용)
     * GET /api/admin/users/by-role/{role}
     */
    @GetMapping(value = "/by-role/{role}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getUsersByRole(@PathVariable String role) {
        try {
            System.out.println("=== 역할별 유저 목록 조회 요청: " + role + " ===");
            
            List<AdminUserDto> users = adminUserService.getUsersByRole(role);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("users", users);
            
            System.out.println("역할별 유저 조회 완료 - 유저 수: " + users.size());
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("역할별 유저 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "역할별 유저 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }
} 