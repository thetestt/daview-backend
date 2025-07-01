// ✅ 서비스 인터페이스
// 📁 src/main/java/com/daview/service/admin_service/AdminFacilityService.java

package com.daview.service.admin;

import com.daview.dto.FacilityDTO;
import java.util.List;

public interface AdminFacilityService {
    void addFacility(FacilityDTO facilityDTO);
    List<FacilityDTO> getAllFacilities();
    void updateFacility(String id, FacilityDTO facilityDTO);
    void deleteFacility(String id);  // Soft Delete
    void restoreFacility(String id);  // Soft Delete 취소 (복구)
    void permanentDeleteFacility(String id);  // Hard Delete (관리자 전용)
    FacilityDTO getFacilityById(String id);
    
    // 시설 사진 관련 메소드
    void addFacilityPhoto(String facilityId, String photoUrl, boolean isThumbnail);
    void updateFacilityPhoto(String facilityId, String photoUrl);
    void deleteFacilityPhoto(String facilityId, Boolean isThumbnail);
}
