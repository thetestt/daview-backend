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
        return caregiverMapperMain.getAllCaregivers();
    }

    @Override
    public void updateCaregiver(Long id, CaregiverDTO caregiverDTO) {
        caregiverDTO.setCaregiverId(String.valueOf(id));
        caregiverMapper.updateCaregiver(caregiverDTO);
    }

    @Override
    public void deleteCaregiver(Long id) {
        caregiverMapper.deleteCaregiver(id);
    }

    @Override
    public CaregiverDTO getCaregiverById(Long id) {
        return caregiverMapper.getCaregiverById(id);
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
