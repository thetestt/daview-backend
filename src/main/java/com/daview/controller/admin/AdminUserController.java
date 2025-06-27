package com.daview.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    // 전체 유저 목록 조회 (관리자용)
    @GetMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String search) {
        try {
            System.out.println("=== AdminUserController: 전체 유저 목록 조회 요청 ===");
            System.out.println("필터 - 역할: " + role + ", 검색어: " + search);
            
            List<Map<String, Object>> users = new ArrayList<>();
            
            // 더미 데이터 - 다양한 역할의 사용자들
            String[] userData = {
                // USER 역할
                "1,user01,김철수,user01@email.com,010-1234-5678,USER,ACTIVE,2024-01-15",
                "2,user02,이영희,user02@email.com,010-2345-6789,USER,ACTIVE,2024-01-16",
                "3,user03,박민수,user03@email.com,010-3456-7890,USER,INACTIVE,2024-01-17",
                "4,user04,최수진,user04@email.com,010-4567-8901,USER,ACTIVE,2024-01-18",
                "5,user05,정재훈,user05@email.com,010-5678-9012,USER,ACTIVE,2024-01-19",
                
                // CAREGIVER 역할
                "35,care01,김철수,care01@email.com,010-1111-2222,CAREGIVER,ACTIVE,2024-01-10",
                "36,care02,이영희,care02@email.com,010-2222-3333,CAREGIVER,ACTIVE,2024-01-11", 
                "37,care03,박민수,care03@email.com,010-3333-4444,CAREGIVER,ACTIVE,2024-01-12",
                "38,care04,최수진,care04@email.com,010-4444-5555,CAREGIVER,ACTIVE,2024-01-13",
                "39,care05,정재훈,care05@email.com,010-5555-6666,CAREGIVER,ACTIVE,2024-01-14",
                "40,care06,한소영,care06@email.com,010-6666-7777,CAREGIVER,ACTIVE,2024-01-15",
                "41,care07,오준호,care07@email.com,010-7777-8888,CAREGIVER,ACTIVE,2024-01-16",
                "42,care08,우미래,care08@email.com,010-8888-9999,CAREGIVER,INACTIVE,2024-01-17",
                "43,care09,장현우,care09@email.com,010-9999-0000,CAREGIVER,ACTIVE,2024-01-18",
                "44,care10,문서연,care10@email.com,010-0000-1111,CAREGIVER,ACTIVE,2024-01-19",
                
                // COMPANY 역할
                "50,comp01,삼성헬스케어,comp01@samsung.com,02-1234-5678,COMPANY,ACTIVE,2024-01-05",
                "51,comp02,LG복지재단,comp02@lg.com,02-2345-6789,COMPANY,ACTIVE,2024-01-06",
                "52,comp03,현대실버타운,comp03@hyundai.com,02-3456-7890,COMPANY,ACTIVE,2024-01-07",
                
                // ADMIN 역할
                "100,admin01,관리자,admin01@daview.com,02-9999-9999,ADMIN,ACTIVE,2024-01-01"
            };
            
            for (String data : userData) {
                String[] parts = data.split(",");
                Map<String, Object> user = new HashMap<>();
                user.put("id", Integer.parseInt(parts[0]));
                user.put("username", parts[1]);
                user.put("name", parts[2]);
                user.put("email", parts[3]);
                user.put("phone", parts[4]);
                user.put("role", parts[5]);
                user.put("status", parts[6]);
                user.put("createdAt", parts[7]);
                
                // 필터링 적용
                boolean includeUser = true;
                
                // 역할 필터
                if (role != null && !role.isEmpty() && !parts[5].equals(role)) {
                    includeUser = false;
                }
                
                // 검색 필터 (이름, 이메일, 전화번호)
                if (search != null && !search.trim().isEmpty()) {
                    String searchLower = search.toLowerCase();
                    if (!parts[2].toLowerCase().contains(searchLower) && 
                        !parts[3].toLowerCase().contains(searchLower) && 
                        !parts[4].toLowerCase().contains(searchLower)) {
                        includeUser = false;
                    }
                }
                
                if (includeUser) {
                    users.add(user);
                }
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("users", users);
            
            System.out.println("필터링된 사용자 수: " + users.size());
            
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
    
    // 유저 상태 변경 API
    @PatchMapping(value = "/{userId}/status", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> updateUserStatus(
            @PathVariable String userId, 
            @RequestBody Map<String, Object> statusData) {
        try {
            String newStatus = (String) statusData.get("status");
            System.out.println("=== 유저 상태 변경 요청 ===");
            System.out.println("유저 ID: " + userId + ", 새 상태: " + newStatus);
            
            // 실제로는 DB 업데이트 로직이 들어가야 합니다
            // 현재는 성공 응답만 반환
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "유저 상태가 성공적으로 변경되었습니다.");
            
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
} 