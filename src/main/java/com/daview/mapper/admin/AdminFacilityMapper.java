// ✅ 매퍼 인터페이스 추가
// 📁 src/main/java/com/daview/mapper/admin/AdminFacilityMapper.java

package com.daview.mapper.admin;

import com.daview.dto.FacilityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminFacilityMapper {
    void insertFacility(FacilityDTO facilityDTO);
    List<FacilityDTO> getAllFacilities();
    void updateFacility(FacilityDTO facilityDTO);
    void deleteFacility(String id);  // Soft Delete
    void restoreFacility(String id);  // Soft Delete 취소 (복구)
    void permanentDeleteFacility(String id);  // Hard Delete (관리자 전용)
    FacilityDTO getFacilityById(String id);
    
    // 시설 사진 관련 메소드
    void insertFacilityPhoto(@Param("facilityId") String facilityId, 
                            @Param("photoUrl") String photoUrl, 
                            @Param("isThumbnail") boolean isThumbnail);
    void updateFacilityPhoto(@Param("facilityId") String facilityId, 
                            @Param("photoUrl") String photoUrl);
    void deleteFacilityPhoto(@Param("facilityId") String facilityId, 
                            @Param("isThumbnail") Boolean isThumbnail);
}
