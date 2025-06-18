package com.daview.controller.admin_controller; // 경로 수정

import com.daview.dto.CaregiverDTO; // DTO 임포트
import com.daview.service.admin_service.AdminCaregiverService; // 서비스 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/caregivers")
public class AdminCaregiverController {

    @Autowired
    private AdminCaregiverService caregiverService;

    @PostMapping
    public String addCaregiver(@RequestBody CaregiverDTO caregiverDTO) {
        caregiverService.addCaregiver(caregiverDTO);
        return "Caregiver added successfully!";
    }

    @GetMapping
    public List<CaregiverDTO> getAllCaregivers() {
        return caregiverService.getAllCaregivers();
    }

    @PutMapping("/{id}")
    public String updateCaregiver(@PathVariable("id") Long id, @RequestBody CaregiverDTO caregiverDTO) {
        caregiverService.updateCaregiver(id, caregiverDTO);
        return "Caregiver updated successfully!";
    }

    @DeleteMapping("/{id}")
    public String deleteCaregiver(@PathVariable("id") Long id) {
        caregiverService.deleteCaregiver(id);
        return "Caregiver deleted successfully!";
    }

    @GetMapping("/{id}")
    public CaregiverDTO getCaregiverById(@PathVariable("id") Long id) {
        return caregiverService.getCaregiverById(id);
    }
}
