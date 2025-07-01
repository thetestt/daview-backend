package com.daview.controller;

import com.daview.service.admin.AdminNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/site-notices")
@CrossOrigin(origins = "*")
public class SiteNoticeController {

    @Autowired
    private AdminNoticeService adminNoticeService;

    // 메인페이지용 최신 공지사항 조회
    @GetMapping("/latest")
    public ResponseEntity<Map<String, Object>> getLatestNotices(
            @RequestParam(defaultValue = "5") int limit) {
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("search", "");
            params.put("sortBy", "created_at");
            params.put("sortDir", "DESC");
            params.put("offset", 0);
            params.put("limit", limit);
            
            Map<String, Object> result = adminNoticeService.getNoticeList(params);
            
            Map<String, Object> response = new HashMap<>();
            response.put("notices", result.get("notices"));
            response.put("totalCount", result.get("totalCount"));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "공지사항 조회 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    // 공지사항 상세 조회 (팝업용)
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getNoticeDetail(@PathVariable Long id) {
        try {
            var notice = adminNoticeService.getNoticeById(id);
            
            Map<String, Object> response = new HashMap<>();
            if (notice != null) {
                response.put("notice", notice);
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "공지사항을 찾을 수 없습니다.");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "공지사항 상세 조회 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
} 