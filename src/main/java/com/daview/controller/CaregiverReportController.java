package com.daview.controller;

import com.daview.dto.CaregiverReportDTO;
import com.daview.service.CaregiverReportService;
import com.daview.service.CaregiverService;
import com.daview.dto.CaregiverDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/caregiver/reports")
@CrossOrigin(origins = "http://localhost:3000")
public class CaregiverReportController {
    
    @Autowired
    private CaregiverReportService caregiverReportService;
    
    @Autowired
    private CaregiverService caregiverService;
    
    // 보고서 목록 조회 (페이징)
    @GetMapping
    public ResponseEntity<?> getReports(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            String caregiverId = extractCaregiverIdFromToken(request);
            if (caregiverId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증이 필요합니다."));
            }
            
            List<CaregiverReportDTO> reports = caregiverReportService.getReportsByCaregiverId(caregiverId, page, size);
            int totalCount = caregiverReportService.getReportsCountByCaregiverId(caregiverId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("reports", reports);
            response.put("totalCount", totalCount);
            response.put("currentPage", page);
            response.put("pageSize", size);
            response.put("totalPages", (int) Math.ceil((double) totalCount / size));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "보고서 목록 조회 중 오류가 발생했습니다."));
        }
    }
    
    // 특정 날짜의 보고서 조회
    @GetMapping("/date/{date}")
    public ResponseEntity<?> getReportByDate(
            HttpServletRequest request,
            @PathVariable String date) {
        try {
            String caregiverId = extractCaregiverIdFromToken(request);
            if (caregiverId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증이 필요합니다."));
            }
            
            LocalDate reportDate = LocalDate.parse(date);
            CaregiverReportDTO report = caregiverReportService.getReportByDate(caregiverId, reportDate);
            
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "보고서 조회 중 오류가 발생했습니다."));
        }
    }
    
    // 보고서 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable Long id) {
        try {
            CaregiverReportDTO report = caregiverReportService.getReportById(id);
            if (report == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "보고서를 찾을 수 없습니다."));
            }
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "보고서 조회 중 오류가 발생했습니다."));
        }
    }
    
    // 보고서 생성
    @PostMapping
    public ResponseEntity<?> createReport(
            HttpServletRequest request,
            @RequestBody CaregiverReportDTO reportDTO) {
        try {
            String caregiverId = extractCaregiverIdFromToken(request);
            if (caregiverId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증이 필요합니다."));
            }
            
            reportDTO.setCaregiverId(caregiverId);
            CaregiverReportDTO createdReport = caregiverReportService.createReport(reportDTO);
            
            if (createdReport != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "보고서 생성에 실패했습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "보고서 생성 중 오류가 발생했습니다."));
        }
    }
    
    // 보고서 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(
            @PathVariable Long id,
            @RequestBody CaregiverReportDTO reportDTO) {
        try {
            reportDTO.setId(id);
            CaregiverReportDTO updatedReport = caregiverReportService.updateReport(reportDTO);
            
            if (updatedReport != null) {
                return ResponseEntity.ok(updatedReport);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "보고서 수정에 실패했습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "보고서 수정 중 오류가 발생했습니다."));
        }
    }
    
    // 보고서 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable Long id) {
        try {
            boolean deleted = caregiverReportService.deleteReport(id);
            
            if (deleted) {
                return ResponseEntity.ok(Map.of("message", "보고서가 삭제되었습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "보고서 삭제에 실패했습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "보고서 삭제 중 오류가 발생했습니다."));
        }
    }
    
    // 보고서 상태 변경
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateReportStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        try {
            String status = request.get("status");
            boolean updated = caregiverReportService.updateReportStatus(id, status);
            
            if (updated) {
                return ResponseEntity.ok(Map.of("message", "보고서 상태가 변경되었습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "보고서 상태 변경에 실패했습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "보고서 상태 변경 중 오류가 발생했습니다."));
        }
    }
    
    // 최근 보고서 목록 조회 (대시보드용)
    @GetMapping("/recent")
    public ResponseEntity<?> getRecentReports(
            HttpServletRequest request,
            @RequestParam(defaultValue = "5") int limit) {
        try {
            String caregiverId = extractCaregiverIdFromToken(request);
            if (caregiverId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증이 필요합니다."));
            }
            
            List<CaregiverReportDTO> reports = caregiverReportService.getRecentReports(caregiverId, limit);
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "최근 보고서 조회 중 오류가 발생했습니다."));
        }
    }
    
    // 특정 기간의 보고서 목록 조회
    @GetMapping("/range")
    public ResponseEntity<?> getReportsByDateRange(
            HttpServletRequest request,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            String caregiverId = extractCaregiverIdFromToken(request);
            if (caregiverId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증이 필요합니다."));
            }
            
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            
            List<CaregiverReportDTO> reports = caregiverReportService.getReportsByDateRange(caregiverId, start, end);
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "기간별 보고서 조회 중 오류가 발생했습니다."));
        }
    }
    
    // 요청에서 요양사 ID 추출
    private String extractCaregiverIdFromToken(HttpServletRequest request) {
        try {
            Long memberId = (Long) request.getAttribute("memberId");
            if (memberId == null) {
                return null;
            }
            
            // memberId로 caregiver 정보 조회
            CaregiverDTO caregiverProfile = caregiverService.getCaregiverProfileByMemberId(memberId);
            if (caregiverProfile == null) {
                return null;
            }
            
            return caregiverProfile.getCaregiverId();
        } catch (Exception e) {
            System.err.println("extractCaregiverIdFromToken 오류: " + e.getMessage());
            return null;
        }
    }
} 