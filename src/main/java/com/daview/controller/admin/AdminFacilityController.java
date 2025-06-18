// ✅ 관리자 요양원 CRUD - Controller + Service
// 📁 src/main/java/com/daview/controller/admin/AdminFacilityController.java

package com.daview.controller.admin;

import com.daview.dto.FacilityDTO;
import com.daview.service.admin.AdminFacilityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/facility")
public class AdminFacilityController {

    private final AdminFacilityService facilityService;

    public AdminFacilityController(AdminFacilityService facilityService) {
        this.facilityService = facilityService;
    }

    // 전체 조회
    @GetMapping
    public List<FacilityDTO> getAllFacilities() {
        return facilityService.getAllFacilities();
    }

    // 등록
    @PostMapping
    public void createFacility(@RequestBody FacilityDTO dto) {
        facilityService.createFacility(dto);
    }

    // 수정
    @PutMapping("/{id}")
    public void updateFacility(@PathVariable String id, @RequestBody FacilityDTO dto) {
    dto.setFacilityId(id);  // ✅ 문자열 UUID 그대로 설정
    facilityService.updateFacility(dto);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
    }
}