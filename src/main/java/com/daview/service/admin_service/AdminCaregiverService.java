package com.daview.service.admin_service; // 경로 수정

import com.daview.dto.CaregiverDTO; // DTO 임포트

import java.util.List;

public interface AdminCaregiverService {

    void addCaregiver(CaregiverDTO caregiverDTO);

    List<CaregiverDTO> getAllCaregivers();

    void updateCaregiver(Long id, CaregiverDTO caregiverDTO);

    void deleteCaregiver(Long id);

    CaregiverDTO getCaregiverById(Long id);
}
