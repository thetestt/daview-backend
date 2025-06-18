// ✅ 서비스 인터페이스
// 📁 src/main/java/com/daview/service/admin_service/AdminFacilityService.java

package com.daview.service.admin_service;

import com.daview.dto.FacilityDTO;
import java.util.List;

public interface AdminFacilityService {
    List<FacilityDTO> getAllFacilities();
    void createFacility(FacilityDTO dto);
    void updateFacility(FacilityDTO dto);
    void deleteFacility(Long id);
}
