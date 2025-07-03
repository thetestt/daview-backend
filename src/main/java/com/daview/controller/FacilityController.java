package com.daview.controller;

import com.daview.dto.FacilityDTO;
import com.daview.dto.FacilitySearchFilterRequest;
import com.daview.service.FacilityService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/facilities")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class FacilityController {

    private final FacilityService facilityService;
    
    
    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    //실버타운 리스트
    @GetMapping("/silvertowns")
    public List<FacilityDTO> getSilvertowns() {
        return facilityService.getSilvertowns();
    }
    
    //실버타운 상세페이지 
    @GetMapping("/silvertown/{id}")
    public ResponseEntity<FacilityDTO> getFacilityDetail(@PathVariable("id") String id) {
    	System.out.println("💡 요청된 ID: " + id);
        FacilityDTO detail = facilityService.getFacilityDetail(id);
        System.out.println("💡 결과: " + detail);
        if (detail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detail);
    }
    
    //실버타운 검색박스 검색 
    @PostMapping("/silvertown/search")
    public List<FacilityDTO> searchSilvertowns(@RequestBody FacilitySearchFilterRequest request) {
    	System.out.println("🟢 요청 값: " + request.getLocation() + " / " + request.getCity());
        return facilityService.searchSilvertowns(request);
    }
    
    
    //요양원 리스트
    @GetMapping("/nursinghomes")
    public List<FacilityDTO> getNursingHomes() {
        return facilityService.getNursinghomes();
    }
    
    //요양원 상세페이지
    @GetMapping("/nursinghome/{id}")
    public ResponseEntity<FacilityDTO> getNursingHomeDetail(@PathVariable("id") String id) {
    	System.out.println("💡 요청된 ID: " + id);
        FacilityDTO detail = facilityService.getNursingHomeDetail(id);
        System.out.println("💡 결과: " + detail);
        if (detail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detail);
    }
    
    //요양원 검색박스 검색
    @PostMapping("/nursinghome/search")
    public List<FacilityDTO> searchNursinghomes(@RequestBody FacilitySearchFilterRequest request) {
        System.out.println("🟢 요양원 검색 요청 값: " + request);
        return facilityService.searchNursinghomes(request);
    }
    
    // =================== 기업 대시보드 관련 API ===================
    
    /**
     * 본인 시설 정보 조회 (JWT 토큰 기반)
     */
    @GetMapping("/me")
    public ResponseEntity<FacilityDTO> getMyFacilityProfile(HttpServletRequest request) {
        try {
            // JWT 토큰에서 memberId 추출
            Long memberId = (Long) request.getAttribute("memberId");
            
            if (memberId == null) {
                System.out.println("❌ JWT 토큰에서 memberId를 찾을 수 없습니다.");
                return ResponseEntity.status(401).build(); // Unauthorized
            }

            System.out.println("🏢 본인 시설 정보 조회 요청 - memberId: " + memberId);
            
            FacilityDTO facility = facilityService.getFacilityByMemberId(memberId);
            
            if (facility == null) {
                System.out.println("❌ memberId " + memberId + "에 해당하는 시설 정보가 없습니다.");
                return ResponseEntity.notFound().build();
            }

            System.out.println("✅ 시설 정보 조회 성공: " + facility.getFacilityName());
            return ResponseEntity.ok(facility);
            
        } catch (Exception e) {
            System.out.println("❌ 시설 정보 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }
    
    /**
     * 본인 시설 정보 수정 (JWT 토큰 기반)
     */
    @PutMapping("/me")
    public ResponseEntity<String> updateMyFacilityProfile(
            @RequestBody FacilityDTO facilityDTO, 
            HttpServletRequest request) {
        try {
            // JWT 토큰에서 memberId 추출
            Long memberId = (Long) request.getAttribute("memberId");
            
            if (memberId == null) {
                System.out.println("❌ JWT 토큰에서 memberId를 찾을 수 없습니다.");
                return ResponseEntity.status(401).body("인증이 필요합니다.");
            }

            System.out.println("🏢 시설 정보 수정 요청 - memberId: " + memberId);
            System.out.println("📝 수정할 데이터: " + facilityDTO.getFacilityName());
            
            // 요청한 사용자가 실제로 해당 시설의 소유자인지 확인
            FacilityDTO existingFacility = facilityService.getFacilityByMemberId(memberId);
            if (existingFacility == null) {
                System.out.println("❌ 수정 권한이 없습니다. memberId: " + memberId);
                return ResponseEntity.status(403).body("수정 권한이 없습니다.");
            }
            
            // memberId를 DTO에 설정하여 본인 시설만 수정하도록 보장
            facilityDTO.setMemberId(memberId);
            
            // 시설 정보 업데이트
            int result = facilityService.updateFacilityProfile(facilityDTO);
            
            if (result > 0) {
                System.out.println("✅ 시설 정보 수정 성공 - memberId: " + memberId);
                return ResponseEntity.ok("시설 정보가 성공적으로 수정되었습니다.");
            } else {
                System.out.println("❌ 시설 정보 수정 실패 - memberId: " + memberId);
                return ResponseEntity.status(500).body("시설 정보 수정에 실패했습니다.");
            }
            
        } catch (Exception e) {
            System.out.println("❌ 시설 정보 수정 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("서버 오류가 발생했습니다.");
        }
    }
}

