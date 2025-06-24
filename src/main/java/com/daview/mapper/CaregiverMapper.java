package com.daview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.daview.dto.CaregiverCareerDTO;
import com.daview.dto.CaregiverDTO;
import com.daview.dto.CaregiverFilterDTO;

public interface CaregiverMapper {
    List<CaregiverDTO> getAllCaregivers();
    List<String> getCertificatesByCaregiverId(String caregiverId);
    List<CaregiverCareerDTO> getCareerByCaregiverId(String caregiverId);
    CaregiverDTO getCaregiverById(String caregiverId);
    List<CaregiverDTO> searchCaregiversByKeyword(String keyword);
    List<CaregiverDTO> searchCaregiversWithFilters(CaregiverFilterDTO filters);
    String findDefaultMessageByCaregiverId(@Param("facilityId") String caregiverId);
    
    // 새로운 상품(요양사) 등록 관련 메서드들
    int insertCaregiver(CaregiverDTO caregiverDTO);
    int insertCaregiverCareer(@Param("caregiverId") String caregiverId, @Param("career") CaregiverCareerDTO career);
    int insertCaregiverCertificate(@Param("caregiverId") String caregiverId, @Param("certificateName") String certificateName);
    
    CaregiverDTO findCaregiverInfoByMemberId(@Param("memberId") Long memberId);
    CaregiverDTO findCaregiverByCaregiverId(@Param("facilityId") String caregiverId);
}
