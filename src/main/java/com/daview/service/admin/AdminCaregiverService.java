package com.daview.service.admin; // 경로 수정

import com.daview.dto.CaregiverDTO; // DTO 임포트
import java.util.Map;

import java.util.List;

public interface AdminCaregiverService {

    void addCaregiver(CaregiverDTO caregiverDTO);

    List<CaregiverDTO> getAllCaregivers();

    void updateCaregiver(Long id, CaregiverDTO caregiverDTO);

    void deleteCaregiver(Long id);

    CaregiverDTO getCaregiverById(Long id);
    
    // 새로운 상품(요양사) 등록 메서드 추가
    void createCaregiverProduct(Map<String, Object> productData);
}
