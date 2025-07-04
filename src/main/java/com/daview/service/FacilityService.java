package com.daview.service;

import com.daview.dto.FacilityDTO;
import com.daview.dto.FacilitySearchFilterRequest;
import com.daview.mapper.FacilityMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.ArrayList;

@Service
public class FacilityService {

    private final FacilityMapper facilityMapper;

    
    //실버타운
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
    
    
    
    //요양원  
    
    public List<FacilityDTO> getNursinghomes() {
        return facilityMapper.getAllNursingHomes();
    }

    
    public FacilityDTO getNursingHomeDetail(String facilityId) {
        // ✅ 이름 수정 (XML과 동일하게 getSilvertownDetail)
        FacilityDTO facility = facilityMapper.getNursingHomeDetail(facilityId);

        if (facility != null) {
            facility.setPhotos(facilityMapper.getFacilityPhotos(facilityId));
            facility.setTags(facilityMapper.getFacilityTags(facilityId));
            facility.setNotices(facilityMapper.getFacilityNotices(facilityId));
        }

        return facility;
    }
    
    
    public List<FacilityDTO> searchNursinghomes(FacilitySearchFilterRequest request) {
        return facilityMapper.searchNursinghomeWithFilters(request);
    }
    
    
    
    
    
    //전체검색
    
    public List<FacilityDTO> searchFacilitiesByKeyword(String keyword) {
        return facilityMapper.searchFacilitiesByKeyword(keyword);
    }
    
    public List<FacilityDTO> searchSilvertowns(FacilitySearchFilterRequest request) {
        return facilityMapper.searchSilvertownWithFilters(request);
    }
    
    // =================== 기업 대시보드 관련 메소드 ===================
    
    /**
     * memberId로 본인 시설 정보 조회
     */
    public FacilityDTO getFacilityByMemberId(Long memberId) {
        try {
            System.out.println("🏢 memberId로 시설 정보 조회: " + memberId);
            FacilityDTO facility = facilityMapper.findByMemberId(memberId);
            
            if (facility != null) {
                System.out.println("✅ 시설 정보 조회 성공: " + facility.getFacilityName());
                
                // 시설 추가 정보도 조회 (photos, tags, notices)
                if (facility.getFacilityId() != null) {
                    facility.setPhotos(facilityMapper.getFacilityPhotos(facility.getFacilityId()));
                    facility.setTags(facilityMapper.getFacilityTags(facility.getFacilityId()));
                    facility.setNotices(facilityMapper.getFacilityNotices(facility.getFacilityId()));
                }
                
                // 서비스 목록도 조회
                try {
                    List<String> services = facilityMapper.getFacilityServices(memberId);
                    facility.setServices(services);
                    System.out.println("✅ 서비스 목록 조회 완료: " + services.size() + "개");
                } catch (Exception e) {
                    System.out.println("⚠️ 서비스 목록 조회 중 오류 (무시): " + e.getMessage());
                    facility.setServices(new ArrayList<>());
                }
            } else {
                System.out.println("❌ memberId " + memberId + "에 해당하는 시설이 없습니다.");
            }
            
            return facility;
        } catch (Exception e) {
            System.out.println("❌ 시설 정보 조회 중 오류: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 시설 정보 수정
     */
    public int updateFacilityProfile(FacilityDTO facilityDTO) {
        try {
            System.out.println("🏢 시설 정보 수정 시작 - memberId: " + facilityDTO.getMemberId());
            
            // 필수 필드 검증
            if (facilityDTO.getFacilityName() == null || facilityDTO.getFacilityName().trim().isEmpty()) {
                System.out.println("❌ 시설명이 필수입니다.");
                return 0;
            }
            
            if (facilityDTO.getFacilityType() == null || facilityDTO.getFacilityType().trim().isEmpty()) {
                System.out.println("❌ 시설 유형이 필수입니다.");
                return 0;
            }
            
            // 시설 기본 정보 업데이트
            int result = facilityMapper.updateFacilityProfile(facilityDTO);
            
            if (result > 0) {
                System.out.println("✅ 시설 기본 정보 수정 성공");
                
                // 사진 업데이트 (있는 경우) - camelCase와 snake_case 모두 체크
                String photoUrl = facilityDTO.getPhotoUrl();
                if (photoUrl == null || photoUrl.trim().isEmpty()) {
                    photoUrl = facilityDTO.getPhoto_url(); // snake_case도 체크
                }
                if (photoUrl != null && !photoUrl.trim().isEmpty()) {
                    try {
                        // 기존 썸네일 사진 삭제
                        facilityMapper.deleteFacilityThumbnail(facilityDTO.getMemberId());
                        
                        // 새로운 썸네일 사진 추가
                        facilityMapper.insertFacilityThumbnail(facilityDTO.getMemberId(), photoUrl);
                        
                        System.out.println("✅ 시설 사진 업데이트 완료: " + photoUrl);
                    } catch (Exception e) {
                        System.out.println("⚠️ 시설 사진 업데이트 중 오류 (기본 정보는 저장됨): " + e.getMessage());
                    }
                }
                
                // 서비스 항목 업데이트 (있는 경우)
                if (facilityDTO.getServices() != null && !facilityDTO.getServices().isEmpty()) {
                    try {
                        // 기존 서비스 태그 삭제 후 새로 추가
                        facilityMapper.deleteFacilityServiceTags(facilityDTO.getMemberId());
                        
                        for (String service : facilityDTO.getServices()) {
                            facilityMapper.insertFacilityServiceTag(facilityDTO.getMemberId(), service);
                        }
                        System.out.println("✅ 서비스 항목 업데이트 완료");
                    } catch (Exception e) {
                        System.out.println("⚠️ 서비스 항목 업데이트 중 오류 (기본 정보는 저장됨): " + e.getMessage());
                    }
                }
            } else {
                System.out.println("❌ 시설 정보 수정 실패");
            }
            
            return result;
            
        } catch (Exception e) {
            System.out.println("❌ 시설 정보 수정 중 오류: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}
    
    
    

