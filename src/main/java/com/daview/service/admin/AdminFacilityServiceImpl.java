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
        System.out.println("===== 시설 등록 시작 =====");
        System.out.println("시설명: " + facilityDTO.getFacilityName());
        System.out.println("사진 URL: " + facilityDTO.getPhotoUrl());
        
        // 1. UUID 생성하여 facility_id 설정
        String facilityId = java.util.UUID.randomUUID().toString();
        facilityDTO.setFacilityId(facilityId);
        System.out.println("✅ 생성된 UUID: " + facilityId);
        
        // 2. 시설 기본 정보 등록
        facilityMapper.insertFacility(facilityDTO);
        System.out.println("✅ 시설 기본 정보 등록 완료, facility_id: " + facilityId);
        
        // 3. 사진 URL이 있으면 facility_photo 테이블에도 등록
        if (facilityDTO.getPhotoUrl() != null && !facilityDTO.getPhotoUrl().trim().isEmpty()) {
            try {
                if (facilityId != null) {
                    facilityMapper.insertFacilityPhoto(facilityId, facilityDTO.getPhotoUrl(), true);
                    System.out.println("✅ 시설 사진 등록 완료: facility_id=" + facilityId + ", url=" + facilityDTO.getPhotoUrl());
                } else {
                    System.err.println("❌ 시설 ID가 null이어서 사진 등록 실패");
                }
            } catch (Exception e) {
                System.err.println("❌ 시설 사진 등록 실패: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("⚠️ 사진 URL이 없어서 facility_photo 테이블 등록 생략");
        }
        
        System.out.println("===== 시설 등록 완료 =====");
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
        System.out.println("===== 시설 상품 수정 시작 =====");
        System.out.println("Facility ID: " + id);
        System.out.println("수정할 데이터: " + facilityDTO);
        
        // 기존 데이터 조회
        FacilityDTO existingFacility = facilityMapper.getFacilityById(id);
        if (existingFacility == null) {
            System.err.println("시설을 찾을 수 없습니다: " + id);
            throw new RuntimeException("시설을 찾을 수 없습니다: " + id);
        }

        System.out.println("기존 시설 정보: " + existingFacility.getFacilityName());

        // 변경된 필드만 업데이트
        facilityDTO.setFacilityId(id);

        // null이거나 빈 값인 필드는 null로 설정하여 매퍼에서 무시되도록 함
        if (facilityDTO.getFacilityName() == null || facilityDTO.getFacilityName().trim().isEmpty()) {
            facilityDTO.setFacilityName(null);
        }
        if (facilityDTO.getFacilityCharge() == null || facilityDTO.getFacilityCharge() <= 0) {
            facilityDTO.setFacilityCharge(null);
        }
        if (facilityDTO.getFacilityType() == null || facilityDTO.getFacilityType().trim().isEmpty()) {
            facilityDTO.setFacilityType(null);
        }
        if (facilityDTO.getFacilityAddressLocation() == null || facilityDTO.getFacilityAddressLocation().trim().isEmpty()) {
            facilityDTO.setFacilityAddressLocation(null);
        }
        if (facilityDTO.getFacilityAddressCity() == null || facilityDTO.getFacilityAddressCity().trim().isEmpty()) {
            facilityDTO.setFacilityAddressCity(null);
        }
        if (facilityDTO.getFacilityTheme() == null || facilityDTO.getFacilityTheme().trim().isEmpty()) {
            facilityDTO.setFacilityTheme(null);
        }
        if (facilityDTO.getFacilityDetailAddress() == null || facilityDTO.getFacilityDetailAddress().trim().isEmpty()) {
            facilityDTO.setFacilityDetailAddress(null);
        }
        if (facilityDTO.getFacilityHomepage() == null || facilityDTO.getFacilityHomepage().trim().isEmpty()) {
            facilityDTO.setFacilityHomepage(null);
        }
        if (facilityDTO.getFacilityPhone() == null || facilityDTO.getFacilityPhone().trim().isEmpty()) {
            facilityDTO.setFacilityPhone(null);
        }
        if (facilityDTO.getDefaultMessage() == null || facilityDTO.getDefaultMessage().trim().isEmpty()) {
            facilityDTO.setDefaultMessage(null);
        }

        System.out.println("정제된 수정 데이터:");
        System.out.println("- 시설명: " + facilityDTO.getFacilityName());
        System.out.println("- 시설유형: " + facilityDTO.getFacilityType());
        System.out.println("- 월별이용료: " + facilityDTO.getFacilityCharge());
        System.out.println("- 테마: " + facilityDTO.getFacilityTheme());
        System.out.println("- 지역: " + facilityDTO.getFacilityAddressLocation());
        System.out.println("- 시/군/구: " + facilityDTO.getFacilityAddressCity());
        System.out.println("- 상세주소: " + facilityDTO.getFacilityDetailAddress());
        System.out.println("- 전화번호: " + facilityDTO.getFacilityPhone());
        System.out.println("- 홈페이지: " + facilityDTO.getFacilityHomepage());
        System.out.println("- 기본메시지: " + facilityDTO.getDefaultMessage());

        try {
            facilityMapper.updateFacility(facilityDTO);
            System.out.println("===== 시설 상품 수정 완료 =====");
        } catch (Exception e) {
            System.err.println("시설 수정 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("시설 수정에 실패했습니다: " + e.getMessage());
        }
    }

    @Override
    public void deleteFacility(String id) {
        facilityMapper.deleteFacility(id);
        System.out.println("===== 요양원 소프트 삭제 완료 =====");
        System.out.println("Facility ID: " + id + " (trash_can = 1)");
    }

    @Override
    public void restoreFacility(String id) {
        facilityMapper.restoreFacility(id);
        System.out.println("===== 요양원 복구 완료 =====");
        System.out.println("Facility ID: " + id + " (trash_can = 0)");
    }

    @Override
    public void permanentDeleteFacility(String id) {
        facilityMapper.permanentDeleteFacility(id);
        System.out.println("===== 요양원 영구 삭제 완료 =====");
        System.out.println("Facility ID: " + id + " (완전히 삭제됨)");
    }

    @Override
    public FacilityDTO getFacilityById(String id) {
        return facilityMapper.getFacilityById(id);
    }

    // 시설 사진 관련 메소드 구현
    @Override
    public void addFacilityPhoto(String facilityId, String photoUrl, boolean isThumbnail) {
        try {
            facilityMapper.insertFacilityPhoto(facilityId, photoUrl, isThumbnail);
            System.out.println("시설 사진 등록 성공: " + facilityId + " -> " + photoUrl);
        } catch (Exception e) {
            System.err.println("시설 사진 등록 실패: " + e.getMessage());
            throw new RuntimeException("시설 사진 등록에 실패했습니다: " + e.getMessage());
        }
    }

    @Override
    public void updateFacilityPhoto(String facilityId, String photoUrl) {
        try {
            facilityMapper.updateFacilityPhoto(facilityId, photoUrl);
            System.out.println("시설 사진 수정 성공: " + facilityId + " -> " + photoUrl);
        } catch (Exception e) {
            System.err.println("시설 사진 수정 실패: " + e.getMessage());
            throw new RuntimeException("시설 사진 수정에 실패했습니다: " + e.getMessage());
        }
    }

    @Override
    public void deleteFacilityPhoto(String facilityId, Boolean isThumbnail) {
        try {
            facilityMapper.deleteFacilityPhoto(facilityId, isThumbnail);
            System.out.println("시설 사진 삭제 성공: " + facilityId);
        } catch (Exception e) {
            System.err.println("시설 사진 삭제 실패: " + e.getMessage());
            throw new RuntimeException("시설 사진 삭제에 실패했습니다: " + e.getMessage());
        }
    }
}
