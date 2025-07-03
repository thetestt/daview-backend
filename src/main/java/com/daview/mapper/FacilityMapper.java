package com.daview.mapper;

import com.daview.dto.FacilityDTO;
import com.daview.dto.FacilitySearchFilterRequest;
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
    
    
    List<FacilityDTO> getAllNursingHomes();

    FacilityDTO getNursingHomeDetail(@Param("facilityId") String facilityId);
    
    List<FacilityDTO> searchByTypeAndKeyword(
            @Param("type") String type,
            @Param("keyword") String keyword
        );
    
    List<FacilityDTO> searchSilvertownWithFilters(FacilitySearchFilterRequest request);
 
    List<FacilityDTO> searchNursinghomeWithFilters(FacilitySearchFilterRequest request);

	List<FacilityDTO> searchFacilitiesByKeyword(String keyword);
	
    String findDefaultMessageByFacilityId(@Param("facilityId") String facilityId);
    //String findDefaultMessageByMemberId(@Param("memberId") Long memberId);
    
    //FacilityDTO findFacilityInfoById(@Param("facilityId") String facilityId);
    //FacilityDTO findByMemberId(@Param("memberId") Long memberId);
    FacilityDTO findByFacilityId(String facilityId);
 // member_id로 시설 조회
    FacilityDTO findByMemberId(@Param("memberId") Long memberId);
    
    // =================== 기업 대시보드 관련 메소드 ===================
    
    /**
     * 시설 기본 정보 업데이트
     */
    int updateFacilityProfile(FacilityDTO facilityDTO);
    
    /**
     * 시설 서비스 태그 삭제 (memberId 기준)
     */
    int deleteFacilityServiceTags(@Param("memberId") Long memberId);
    
    /**
     * 시설 서비스 태그 추가
     */
    int insertFacilityServiceTag(@Param("memberId") Long memberId, @Param("service") String service);
    
    /**
     * 시설의 서비스 목록 조회
     */
    List<String> getFacilityServices(@Param("memberId") Long memberId);
}