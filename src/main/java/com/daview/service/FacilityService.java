package com.daview.service;

import com.daview.dto.FacilityDTO;
import com.daview.mapper.FacilityMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FacilityService {

    private final FacilityMapper facilityMapper;

    public FacilityService(FacilityMapper facilityMapper) {
        this.facilityMapper = facilityMapper;
    }

    public List<FacilityDTO> getSilvertowns() {
        return facilityMapper.getAllSilvertowns();
    }

    public FacilityDTO getFacilityDetail(String facilityId) {
        // ✅ 이름 수정 (XML과 동일하게 getSilvertownDetail)
        FacilityDTO facility = facilityMapper.getSilvertownDetail(facilityId);

        if (facility != null) {
            facility.setPhotos(facilityMapper.getFacilityPhotos(facilityId));
            facility.setTags(facilityMapper.getFacilityTags(facilityId));
            facility.setNotices(facilityMapper.getFacilityNotices(facilityId));
        }

        return facility;
    }
    
    
    public List<FacilityDTO> getNursinghomes() {
        return facilityMapper.getAllNursingHomes();
    }

}
