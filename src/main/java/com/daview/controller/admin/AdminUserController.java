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

    // 요양사 역할 회원 목록 조회
    @GetMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getCaregiverUsers() {
        try {
            System.out.println("=== AdminUserController: 회원 목록 조회 요청 ===");
            
            List<Map<String, Object>> users = new ArrayList<>();
            
            // 실제 CAREGIVER 역할 사용자들
            String[] caregiverData = {
                "35,care01,김똥자,care01@email.com",
                "36,care02,유똥자,care02@email.com", 
                "37,care03,박똥자,care03@email.com",
                "38,care04,최똥자,care04@email.com",
                "39,care05,정똥자,care05@email.com",
                "40,care06,한똥자,care06@email.com",
                "41,care07,오똥자,care07@email.com",
                "42,care08,우똥자,care08@email.com",
                "43,care09,장똥자,care09@email.com",
                "44,care10,문똥자,care10@email.com"
            };
            
            for (String data : caregiverData) {
                String[] parts = data.split(",");
                Map<String, Object> user = new HashMap<>();
                user.put("member_id", Integer.parseInt(parts[0]));
                user.put("username", parts[1]);
                user.put("name", parts[2]);
                user.put("email", parts[3]);
                user.put("suggested_product_name", "요양사 " + parts[2]);
                users.add(user);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("users", users);
            
            System.out.println("사용자 수: " + users.size());
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("회원 목록 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "회원 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }
} 