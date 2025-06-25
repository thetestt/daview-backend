// ✅ 매퍼 인터페이스 추가
// 📁 src/main/java/com/daview/mapper/admin/AdminFacilityMapper.java

package com.daview.mapper.admin;

import com.daview.dto.FacilityDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminFacilityMapper {
    void insertFacility(FacilityDTO facilityDTO);
    List<FacilityDTO> getAllFacilities();
    void updateFacility(FacilityDTO facilityDTO);
    void deleteFacility(String id);  // Soft Delete
    void restoreFacility(String id);  // Soft Delete 취소 (복구)
    void permanentDeleteFacility(String id);  // Hard Delete (관리자 전용)
    FacilityDTO getFacilityById(String id);
}
