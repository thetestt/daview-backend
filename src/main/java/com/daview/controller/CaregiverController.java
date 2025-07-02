package com.daview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daview.dto.CaregiverDTO;
import com.daview.dto.CaregiverFilterDTO;
import com.daview.service.CaregiverService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CaregiverController {

    @Autowired
    private CaregiverService caregiverService;

    @GetMapping("/api/caregivers/")
    public List<CaregiverDTO> getAllCaregivers() {
        return caregiverService.getAllCaregivers();
    }

    @GetMapping("/api/caregivers/{id}")
    public CaregiverDTO getCaregiverById(@PathVariable("id") String caregiverId) {
        return caregiverService.getCaregiverById(caregiverId);
    }
    
    @PostMapping("/api/caregivers/search")
    public ResponseEntity<List<CaregiverDTO>> searchCaregivers(@RequestBody CaregiverFilterDTO filters) {
        List<CaregiverDTO> results = caregiverService.searchCaregiversWithFilters(filters);
        return ResponseEntity.ok(results);
    }
    
    // 개인 프로필 관리 API
    @GetMapping("/api/caregiver/my-profile")
    public ResponseEntity<CaregiverDTO> getMyProfile(HttpServletRequest request) {
        try {
            Long memberId = (Long) request.getAttribute("memberId");
            if (memberId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            CaregiverDTO caregiverProfile = caregiverService.getCaregiverProfileByMemberId(memberId);
            if (caregiverProfile == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(caregiverProfile);
        } catch (Exception e) {
            System.err.println("프로필 조회 오류: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping("/api/caregiver/my-profile")
    public ResponseEntity<String> updateMyProfile(@RequestBody CaregiverDTO caregiverDTO, HttpServletRequest request) {
        try {
            Long memberId = (Long) request.getAttribute("memberId");
            if (memberId == null) {
                return ResponseEntity.badRequest().body("인증 정보가 없습니다.");
            }
            
            // 현재 사용자의 요양사 정보 조회
            CaregiverDTO existingCaregiver = caregiverService.getCaregiverProfileByMemberId(memberId);
            if (existingCaregiver == null) {
                return ResponseEntity.status(404).body("요양사 정보를 찾을 수 없습니다.");
            }
            
            // 요양사 ID 설정 (보안을 위해 기존 ID 사용)
            caregiverDTO.setCaregiverId(existingCaregiver.getCaregiverId());
            caregiverDTO.setMemberId(memberId);
            
            caregiverService.updateCaregiverProfile(caregiverDTO);
            return ResponseEntity.ok("프로필이 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            System.err.println("프로필 수정 오류: " + e.getMessage());
            return ResponseEntity.internalServerError().body("프로필 수정 중 오류가 발생했습니다.");
        }
    }
}
