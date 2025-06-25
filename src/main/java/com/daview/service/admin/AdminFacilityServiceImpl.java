// ✅ 서비스 구현체
// 📁 src/main/java/com/daview/service/admin/AdminFacilityServiceImpl.java

package com.daview.service.admin;

import com.daview.dto.FacilityDTO;
import com.daview.mapper.admin.AdminFacilityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminFacilityServiceImpl implements AdminFacilityService {

    @Autowired
    private AdminFacilityMapper facilityMapper;

    @Override
    public void addFacility(FacilityDTO facilityDTO) {
        facilityMapper.insertFacility(facilityDTO);
    }

    @Override
    public List<FacilityDTO> getAllFacilities() {
        List<FacilityDTO> facilities = facilityMapper.getAllFacilities();
        
        System.out.println("DB에서 조회된 요양원 수: " + facilities.size());
        System.out.println("=== 응답 데이터 ===");
        System.out.println("총 요양원 수: " + facilities.size());
        if (!facilities.isEmpty()) {
            System.out.println("첫 번째 요양원: " + facilities.get(0).getFacilityName());
        }
        System.out.println("=== 응답 반환 성공 ===");
        
        return facilities;
    }



    @Override
    public void updateFacility(String id, FacilityDTO facilityDTO) {
        facilityDTO.setFacilityId(id);
        facilityMapper.updateFacility(facilityDTO);
        System.out.println("===== 요양원 상품 수정 완료 =====");
        System.out.println("Facility ID: " + facilityDTO.getFacilityId());
    }

    @Override
    public void deleteFacility(String id) {
        facilityMapper.deleteFacility(id);
    }

    @Override
    public FacilityDTO getFacilityById(String id) {
        return facilityMapper.getFacilityById(id);
    }
}
