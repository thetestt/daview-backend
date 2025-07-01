package com.daview.service.admin; // 경로 수정

import com.daview.dto.CaregiverDTO;
import com.daview.dto.CaregiverCareerDTO;
import com.daview.mapper.admin.AdminCaregiverMapper; // Mapper 임포트
import com.daview.mapper.CaregiverMapper;
import com.daview.service.admin.AdminCaregiverService; // 서비스 인터페이스 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AdminCaregiverServiceImpl implements AdminCaregiverService {

    @Autowired
    private AdminCaregiverMapper caregiverMapper;
    
    @Autowired
    private CaregiverMapper caregiverMapperMain;

    @Override
    public void addCaregiver(CaregiverDTO caregiverDTO) {
        caregiverMapper.insertCaregiver(caregiverDTO);
    }

    @Override
    public List<CaregiverDTO> getAllCaregivers() {
        // AdminCaregiverMapper의 최적화된 JOIN 쿼리 사용 (N+1 문제 해결)
        List<CaregiverDTO> caregivers = caregiverMapper.getAllCaregivers();
        
        System.out.println("DB에서 조회된 간병인 수: " + caregivers.size());
        System.out.println("=== 응답 데이터 ===");
        System.out.println("총 간병인 수: " + caregivers.size());
        if (!caregivers.isEmpty()) {
            System.out.println("첫 번째 간병인: " + caregivers.get(0).getUsername());
        }
        System.out.println("=== 응답 반환 성공 ===");
        
        return caregivers;
    }

    @Override
    public void updateCaregiver(String id, CaregiverDTO caregiverDTO) {
        // 기존 데이터 조회
        CaregiverDTO existingCaregiver = caregiverMapper.getCaregiverById(id);
        if (existingCaregiver == null) {
            throw new RuntimeException("간병인을 찾을 수 없습니다: " + id);
        }

        // 변경된 필드만 업데이트
        caregiverDTO.setCaregiverId(id);

        // 변경되지 않은 필드는 기존 값으로 설정
        if (caregiverDTO.getHopeWorkAreaLocation() == null || caregiverDTO.getHopeWorkAreaLocation().trim().isEmpty()) {
            caregiverDTO.setHopeWorkAreaLocation(null);
        }
        if (caregiverDTO.getHopeWorkAreaCity() == null || caregiverDTO.getHopeWorkAreaCity().trim().isEmpty()) {
            caregiverDTO.setHopeWorkAreaCity(null);
        }
        if (caregiverDTO.getHopeWorkPlace() == null || caregiverDTO.getHopeWorkPlace().trim().isEmpty()) {
            caregiverDTO.setHopeWorkPlace(null);
        }
        if (caregiverDTO.getHopeWorkType() == null || caregiverDTO.getHopeWorkType().trim().isEmpty()) {
            caregiverDTO.setHopeWorkType(null);
        }
        if (caregiverDTO.getHopeEmploymentType() == null || caregiverDTO.getHopeEmploymentType().trim().isEmpty()) {
            caregiverDTO.setHopeEmploymentType(null);
        }
        if (caregiverDTO.getEducationLevel() == null || caregiverDTO.getEducationLevel().trim().isEmpty()) {
            caregiverDTO.setEducationLevel(null);
        }
        if (caregiverDTO.getIntroduction() == null || caregiverDTO.getIntroduction().trim().isEmpty()) {
            caregiverDTO.setIntroduction(null);
        }
        // hopeWorkAmount는 int 타입이므로 0으로 설정하면 매퍼에서 무시됨
        if (caregiverDTO.getHopeWorkAmount() <= 0) {
            caregiverDTO.setHopeWorkAmount(existingCaregiver.getHopeWorkAmount());
        }

        caregiverMapper.updateCaregiver(caregiverDTO);
        System.out.println("===== 간병인 상품 수정 완료 =====");
        System.out.println("Caregiver ID: " + caregiverDTO.getCaregiverId());
    }

    @Override
    public void deleteCaregiver(String id) {
        caregiverMapper.deleteCaregiver(id);
    }

    @Override
    public CaregiverDTO getCaregiverById(Long id) {
        // String 타입 ID로 변환하여 AdminCaregiverMapper의 최적화된 JOIN 쿼리 사용
        return caregiverMapper.getCaregiverById(String.valueOf(id));
    }
    
    @Override
    @Transactional
    public void createCaregiverProduct(Map<String, Object> productData) {
        try {
            // 1. UUID로 caregiver_id 생성
            String caregiverId = UUID.randomUUID().toString();
            
            // 2. CaregiverDTO 생성 및 데이터 매핑
            CaregiverDTO caregiverDTO = new CaregiverDTO();
            caregiverDTO.setCaregiverId(caregiverId);
            
            // member_id는 프론트엔드에서 전달받거나, 기본값 설정 (실제로는 회원가입된 사용자 ID여야 함)
            Object memberIdObj = productData.get("member_id");
            if (memberIdObj != null) {
                caregiverDTO.setMemberId(Long.valueOf(memberIdObj.toString()));
            } else {
                throw new RuntimeException("member_id가 필요합니다. 회원가입된 사용자만 요양사로 등록 가능합니다.");
            }
            
            // 기본 정보 매핑
            caregiverDTO.setHopeWorkAreaLocation((String) productData.get("hope_work_area_location"));
            caregiverDTO.setHopeWorkAreaCity((String) productData.get("hope_work_area_city"));
            caregiverDTO.setHopeWorkPlace((String) productData.get("hope_work_place"));
            caregiverDTO.setHopeWorkType((String) productData.get("hope_work_type"));
            caregiverDTO.setHopeEmploymentType((String) productData.get("hope_employment_type"));
            caregiverDTO.setEducationLevel((String) productData.get("education_level"));
            caregiverDTO.setIntroduction((String) productData.get("introduction"));
            
            // 희망급여 처리
            Object hopeWorkAmountObj = productData.get("hope_work_amount");
            if (hopeWorkAmountObj != null) {
                caregiverDTO.setHopeWorkAmount(Integer.valueOf(hopeWorkAmountObj.toString()));
            }
            
            // 3. caregiver 테이블에 메인 데이터 삽입
            caregiverMapperMain.insertCaregiver(caregiverDTO);
            
            // 4. 경력 정보가 있으면 caregiver_career 테이블에 삽입
            String companyName = (String) productData.get("company_name");
            String startDate = (String) productData.get("start_date");
            String endDate = (String) productData.get("end_date");
            
            if (companyName != null && !companyName.trim().isEmpty()) {
                CaregiverCareerDTO careerDTO = new CaregiverCareerDTO();
                careerDTO.setCompanyName(companyName);
                careerDTO.setStartDate(startDate);
                careerDTO.setEndDate(endDate);
                
                caregiverMapperMain.insertCaregiverCareer(caregiverId, careerDTO);
            }
            
            // 5. 자격증 정보가 있으면 caregiver_certificates 테이블에 삽입
            String certificateName = (String) productData.get("certificate_name");
            if (certificateName != null && !certificateName.trim().isEmpty()) {
                // 여러 자격증을 줄바꿈이나 쉼표로 구분해서 입력한 경우 처리
                String[] certificates = certificateName.split("[,\n]");
                for (String cert : certificates) {
                    String trimmedCert = cert.trim();
                    if (!trimmedCert.isEmpty()) {
                        caregiverMapperMain.insertCaregiverCertificate(caregiverId, trimmedCert);
                    }
                }
            }
            
            System.out.println("===== 새로운 요양사 상품 등록 완료 =====");
            System.out.println("Caregiver ID: " + caregiverId);
            System.out.println("Member ID: " + caregiverDTO.getMemberId());
            
        } catch (Exception e) {
            System.err.println("상품 등록 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("상품 등록에 실패했습니다: " + e.getMessage());
        }
    }
}
