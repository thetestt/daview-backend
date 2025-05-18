package com.daview.mapper;

import com.daview.dto.FacilityDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FacilityMapper {
    List<FacilityDTO> getAllSilvertowns(); // 실버타운만 필터링
}