package com.daview.controller;

import com.daview.dto.CaregiverTaskDTO;
import com.daview.service.CaregiverTaskService;
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
@RequestMapping("/api/caregiver/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class CaregiverTaskController {
    
    @Autowired
    private CaregiverTaskService caregiverTaskService;
    
    // 일정 목록 조회 (페이징)
    @GetMapping
    public ResponseEntity<?> getTasks(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Long caregiverId = extractCaregiverIdFromToken(request);
            if (caregiverId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증이 필요합니다."));
            }
            
            List<CaregiverTaskDTO> tasks = caregiverTaskService.getTasksByCaregiverId(caregiverId, page, size);
            int totalCount = caregiverTaskService.getTasksCountByCaregiverId(caregiverId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("tasks", tasks);
            response.put("totalCount", totalCount);
            response.put("currentPage", page);
            response.put("pageSize", size);
            response.put("totalPages", (int) Math.ceil((double) totalCount / size));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "일정 목록 조회 중 오류가 발생했습니다."));
        }
    }
    
    // 특정 날짜의 일정 목록 조회
    @GetMapping("/date/{date}")
    public ResponseEntity<?> getTasksByDate(
            HttpServletRequest request,
            @PathVariable String date) {
        try {
            Long caregiverId = extractCaregiverIdFromToken(request);
            if (caregiverId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증이 필요합니다."));
            }
            
            LocalDate taskDate = LocalDate.parse(date);
            List<CaregiverTaskDTO> tasks = caregiverTaskService.getTasksByDate(caregiverId, taskDate);
            
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "일정 조회 중 오류가 발생했습니다."));
        }
    }
    
    // 일정 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        try {
            CaregiverTaskDTO task = caregiverTaskService.getTaskById(id);
            if (task == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "일정을 찾을 수 없습니다."));
            }
            return ResponseEntity.ok(task);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "일정 조회 중 오류가 발생했습니다."));
        }
    }
    
    // 일정 생성
    @PostMapping
    public ResponseEntity<?> createTask(
            HttpServletRequest request,
            @RequestBody CaregiverTaskDTO taskDTO) {
        try {
            Long caregiverId = extractCaregiverIdFromToken(request);
            if (caregiverId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증이 필요합니다."));
            }
            
            taskDTO.setCaregiverId(caregiverId);
            CaregiverTaskDTO createdTask = caregiverTaskService.createTask(taskDTO);
            
            if (createdTask != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "일정 생성에 실패했습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "일정 생성 중 오류가 발생했습니다."));
        }
    }
    
    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(
            @PathVariable Long id,
            @RequestBody CaregiverTaskDTO taskDTO) {
        try {
            taskDTO.setId(id);
            CaregiverTaskDTO updatedTask = caregiverTaskService.updateTask(taskDTO);
            
            if (updatedTask != null) {
                return ResponseEntity.ok(updatedTask);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "일정 수정에 실패했습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "일정 수정 중 오류가 발생했습니다."));
        }
    }
    
    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            boolean deleted = caregiverTaskService.deleteTask(id);
            
            if (deleted) {
                return ResponseEntity.ok(Map.of("message", "일정이 삭제되었습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "일정 삭제에 실패했습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "일정 삭제 중 오류가 발생했습니다."));
        }
    }
    
    // 일정 완료 상태 토글
    @PatchMapping("/{id}/completion")
    public ResponseEntity<?> toggleTaskCompletion(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> request) {
        try {
            Boolean completed = request.get("completed");
            boolean updated = caregiverTaskService.toggleTaskCompletion(id, completed);
            
            if (updated) {
                return ResponseEntity.ok(Map.of("message", "일정 완료 상태가 변경되었습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "일정 상태 변경에 실패했습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "일정 상태 변경 중 오류가 발생했습니다."));
        }
    }
    
    // 오늘의 일정 목록 조회
    @GetMapping("/today")
    public ResponseEntity<?> getTodayTasks(HttpServletRequest request) {
        try {
            Long caregiverId = extractCaregiverIdFromToken(request);
            if (caregiverId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증이 필요합니다."));
            }
            
            List<CaregiverTaskDTO> tasks = caregiverTaskService.getTodayTasks(caregiverId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "오늘 일정 조회 중 오류가 발생했습니다."));
        }
    }
    
    // 이번 주 일정 목록 조회
    @GetMapping("/weekly")
    public ResponseEntity<?> getWeeklyTasks(HttpServletRequest request) {
        try {
            Long caregiverId = extractCaregiverIdFromToken(request);
            if (caregiverId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증이 필요합니다."));
            }
            
            List<CaregiverTaskDTO> tasks = caregiverTaskService.getWeeklyTasks(caregiverId);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "이번 주 일정 조회 중 오류가 발생했습니다."));
        }
    }
    
    // 특정 기간의 일정 목록 조회
    @GetMapping("/range")
    public ResponseEntity<?> getTasksByDateRange(
            HttpServletRequest request,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            Long caregiverId = extractCaregiverIdFromToken(request);
            if (caregiverId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증이 필요합니다."));
            }
            
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            
            List<CaregiverTaskDTO> tasks = caregiverTaskService.getTasksByDateRange(caregiverId, start, end);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "기간별 일정 조회 중 오류가 발생했습니다."));
        }
    }
    
    // 최근 일정 목록 조회 (대시보드용)
    @GetMapping("/recent")
    public ResponseEntity<?> getRecentTasks(
            HttpServletRequest request,
            @RequestParam(defaultValue = "5") int limit) {
        try {
            Long caregiverId = extractCaregiverIdFromToken(request);
            if (caregiverId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증이 필요합니다."));
            }
            
            List<CaregiverTaskDTO> tasks = caregiverTaskService.getRecentTasks(caregiverId, limit);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "최근 일정 조회 중 오류가 발생했습니다."));
        }
    }
    
    // 통계 정보 조회
    @GetMapping("/stats")
    public ResponseEntity<?> getTaskStats(HttpServletRequest request) {
        try {
            Long caregiverId = extractCaregiverIdFromToken(request);
            if (caregiverId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "인증이 필요합니다."));
            }
            
            int todayCompleted = caregiverTaskService.getTodayCompletedTasksCount(caregiverId);
            int weeklyTotal = caregiverTaskService.getWeeklyTasksCount(caregiverId);
            
            Map<String, Object> stats = new HashMap<>();
            stats.put("todayCompleted", todayCompleted);
            stats.put("weeklyTotal", weeklyTotal);
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "통계 조회 중 오류가 발생했습니다."));
        }
    }
    
    // 요청에서 요양사 ID 추출
    private Long extractCaregiverIdFromToken(HttpServletRequest request) {
        try {
            return (Long) request.getAttribute("memberId");
        } catch (Exception e) {
            return null;
        }
    }
} 