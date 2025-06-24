package com.daview.controller.admin; // 경로 수정

import com.daview.dto.CaregiverDTO; // DTO 임포트
import com.daview.service.admin.AdminCaregiverService; // 서비스 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/caregivers")
@CrossOrigin(originPatterns = "*", allowCredentials = "true") // 이 컨트롤러만 CORS 허용
public class AdminCaregiverController {

    @Autowired
    private AdminCaregiverService caregiverService;

    // 간병인 등록
    @PostMapping
    public ResponseEntity<String> addCaregiver(@RequestBody CaregiverDTO caregiverDTO) {
        try {
            caregiverService.addCaregiver(caregiverDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("간병인이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("간병인 등록에 실패했습니다: " + e.getMessage());
        }
    }

    // 전체 간병인 목록 조회
    @GetMapping
    public ResponseEntity<List<CaregiverDTO>> getAllCaregivers() {
        try {
            List<CaregiverDTO> caregivers = caregiverService.getAllCaregivers();
            return ResponseEntity.ok(caregivers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 특정 간병인 조회
    @GetMapping("/{id}")
    public ResponseEntity<CaregiverDTO> getCaregiverById(@PathVariable("id") String id) {
        try {
            Long caregiverId = Long.parseLong(id);
            CaregiverDTO caregiver = caregiverService.getCaregiverById(caregiverId);
            if (caregiver != null) {
                return ResponseEntity.ok(caregiver);
            }
            return ResponseEntity.notFound().build();
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 간병인 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCaregiver(@PathVariable("id") String id, @RequestBody CaregiverDTO caregiverDTO) {
        try {
            Long caregiverId = Long.parseLong(id);
            caregiverService.updateCaregiver(caregiverId, caregiverDTO);
            return ResponseEntity.ok("간병인 정보가 성공적으로 수정되었습니다.");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("잘못된 ID 형식입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("간병인 정보 수정에 실패했습니다: " + e.getMessage());
        }
    }

    // 간병인 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCaregiver(@PathVariable("id") String id) {
        try {
            // UUID 문자열을 그대로 사용
            caregiverService.deleteCaregiver(id);
            return ResponseEntity.ok("간병인이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("간병인 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    // 지역별 간병인 검색 (추가 기능)
    @GetMapping("/search")
    public ResponseEntity<List<CaregiverDTO>> searchCaregiversByLocation(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String workType) {
        try {
            // 이 기능은 service에 메서드 추가 필요
            List<CaregiverDTO> caregivers = caregiverService.getAllCaregivers();
            return ResponseEntity.ok(caregivers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
