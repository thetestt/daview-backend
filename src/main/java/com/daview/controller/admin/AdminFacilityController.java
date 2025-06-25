// ✅ 관리자 요양원 CRUD - Controller + Service
// 📁 src/main/java/com/daview/controller/admin/AdminFacilityController.java

package com.daview.controller.admin;

import com.daview.dto.FacilityDTO;
import com.daview.service.admin.AdminFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/facilities")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminFacilityController {

    @Autowired
    private AdminFacilityService facilityService;

    // 요양원 등록
    @PostMapping
    public ResponseEntity<String> addFacility(@RequestBody FacilityDTO facilityDTO) {
        try {
            facilityService.addFacility(facilityDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("요양원이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("요양원 등록에 실패했습니다: " + e.getMessage());
        }
    }

    // 전체 요양원 목록 조회
    @GetMapping
    public ResponseEntity<List<FacilityDTO>> getAllFacilities() {
        try {
            List<FacilityDTO> facilities = facilityService.getAllFacilities();
            return ResponseEntity.ok(facilities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 특정 요양원 조회
    @GetMapping("/{id}")
    public ResponseEntity<FacilityDTO> getFacilityById(@PathVariable("id") String id) {
        try {
            FacilityDTO facility = facilityService.getFacilityById(id);
            if (facility != null) {
                return ResponseEntity.ok(facility);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 요양원 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateFacility(@PathVariable("id") String id, @RequestBody FacilityDTO facilityDTO) {
        try {
            facilityService.updateFacility(id, facilityDTO);
            return ResponseEntity.ok("요양원 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("요양원 정보 수정에 실패했습니다: " + e.getMessage());
        }
    }

    // 요양원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFacility(@PathVariable("id") String id) {
        try {
            facilityService.deleteFacility(id);
            return ResponseEntity.ok("요양원이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("요양원 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    // 지역별 요양원 검색
    @GetMapping("/search")
    public ResponseEntity<List<FacilityDTO>> searchFacilitiesByLocation(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String facilityType) {
        try {
            List<FacilityDTO> facilities = facilityService.getAllFacilities();
            return ResponseEntity.ok(facilities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}