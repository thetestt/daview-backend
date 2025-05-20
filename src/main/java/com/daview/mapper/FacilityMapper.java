package com.daview.mapper;

import com.daview.dto.FacilityDTO;
import com.daview.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FacilityMapper {

    List<FacilityDTO> getAllSilvertowns();

    FacilityDTO getSilvertownDetail(@Param("facilityId") String facilityId);

    List<String> getFacilityPhotos(@Param("facilityId") String facilityId);

    List<String> getFacilityTags(@Param("facilityId") String facilityId);

    List<NoticeDTO> getFacilityNotices(@Param("facilityId") String facilityId);
}