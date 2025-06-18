// ✅ 서비스 구현체
// 📁 src/main/java/com/daview/service/admin_service/AdminFacilityServiceImpl.java

package com.daview.service.admin_service;

import com.daview.dto.FacilityDTO;
import com.daview.mapper.admin_mapper.AdminFacilityMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminFacilityServiceImpl implements AdminFacilityService {

    private final AdminFacilityMapper facilityMapper;

    public AdminFacilityServiceImpl(AdminFacilityMapper facilityMapper) {
        this.facilityMapper = facilityMapper;
    }

    @Override
    public List<FacilityDTO> getAllFacilities() {
        return facilityMapper.selectAll();
    }

    @Override
    public void createFacility(FacilityDTO dto) {
        facilityMapper.insert(dto);
    }

    @Override
    public void updateFacility(FacilityDTO dto) {
        facilityMapper.update(dto);
    }

    @Override
    public void deleteFacility(Long id) {
        facilityMapper.delete(id);
    }
}
