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
}
