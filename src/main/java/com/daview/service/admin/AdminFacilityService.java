// ✅ 서비스 인터페이스
// 📁 src/main/java/com/daview/service/admin_service/AdminFacilityService.java

package com.daview.service.admin;

import com.daview.dto.FacilityDTO;
import java.util.List;

public interface AdminFacilityService {
    void addFacility(FacilityDTO facilityDTO);
    List<FacilityDTO> getAllFacilities();
    void updateFacility(String id, FacilityDTO facilityDTO);
    void deleteFacility(String id);
    FacilityDTO getFacilityById(String id);
}
