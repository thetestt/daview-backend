package com.daview.controller.admin;

import com.daview.dto.AdminNoticeDTO;
import com.daview.service.admin.AdminNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/notices")
@CrossOrigin(origins = "*")
public class AdminNoticeController {

    @Autowired
    private AdminNoticeService adminNoticeService;

    // 공지사항 목록 조회 (페이징, 검색)
    @GetMapping
    public ResponseEntity<Map<String, Object>> getNoticeList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "created_at") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("search", search != null ? search : "");
            params.put("sortBy", sortBy);
            params.put("sortDir", sortDir.toUpperCase()); // DESC/ASC 대문자로 변환
            params.put("offset", page * size);
            params.put("limit", size);
            
            System.out.println("=== AdminNoticeController getNoticeList 파라미터 ===");
            System.out.println("search: " + search);
            System.out.println("sortBy: " + sortBy);
            System.out.println("sortDir: " + sortDir);
            System.out.println("offset: " + (page * size));
            System.out.println("limit: " + size);
            
            Map<String, Object> result = adminNoticeService.getNoticeList(params);
            
            Map<String, Object> response = new HashMap<>();
            response.put("content", result.get("notices"));
            response.put("totalElements", result.get("totalCount"));
            response.put("totalPages", (int) Math.ceil((Long) result.get("totalCount") / (double) size));
            response.put("currentPage", page);
            response.put("size", size);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // 에러 스택 트레이스 출력
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "공지사항 목록 조회 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    // 공지사항 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getNoticeDetail(@PathVariable Long id) {
        try {
            AdminNoticeDTO notice = adminNoticeService.getNoticeById(id);
            
            Map<String, Object> response = new HashMap<>();
            if (notice != null) {
                response.put("notice", notice);
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "공지사항을 찾을 수 없습니다.");
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "공지사항 상세 조회 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    // 공지사항 등록
    @PostMapping
    public ResponseEntity<Map<String, Object>> createNotice(@RequestBody AdminNoticeDTO notice) {
        try {
            boolean success = adminNoticeService.createNotice(notice);
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("message", "공지사항이 성공적으로 등록되었습니다.");
                response.put("noticeId", notice.getId());
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "공지사항 등록에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "공지사항 등록 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    // 공지사항 수정
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateNotice(
            @PathVariable Long id, 
            @RequestBody AdminNoticeDTO notice) {
        
        try {
            notice.setId(id);
            boolean success = adminNoticeService.updateNotice(notice);
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("message", "공지사항이 성공적으로 수정되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "공지사항 수정에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "공지사항 수정 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    // 공지사항 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteNotice(@PathVariable Long id) {
        try {
            boolean success = adminNoticeService.deleteNotice(id);
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("message", "공지사항이 성공적으로 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "공지사항 삭제에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "공지사항 삭제 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    // 여러 공지사항 삭제
    @DeleteMapping("/batch")
    public ResponseEntity<Map<String, Object>> deleteNotices(@RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            List<Long> ids = (List<Long>) request.get("ids");
            
            if (ids == null || ids.isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "삭제할 공지사항 ID가 필요합니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            boolean success = adminNoticeService.deleteNotices(ids);
            
            Map<String, Object> response = new HashMap<>();
            if (success) {
                response.put("message", "선택한 공지사항이 성공적으로 삭제되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "공지사항 삭제에 실패했습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "공지사항 일괄 삭제 실패");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
} 