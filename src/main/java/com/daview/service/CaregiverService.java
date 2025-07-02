package com.daview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daview.dto.CaregiverDTO;
import com.daview.dto.CaregiverFilterDTO;
import com.daview.dto.CaregiverCareerDTO;
import com.daview.mapper.CaregiverMapper;

@Service
public class CaregiverService {

    @Autowired
    private CaregiverMapper caregiverMapper;

    public List<CaregiverDTO> getAllCaregivers() {
        List<CaregiverDTO> caregivers = caregiverMapper.getAllCaregivers();
        for (CaregiverDTO caregiver : caregivers) {
            caregiver.setCertificates(caregiverMapper.getCertificatesByCaregiverId(caregiver.getCaregiverId()));
            caregiver.setCareer(caregiverMapper.getCareerByCaregiverId(caregiver.getCaregiverId()));
        }
        return caregivers;
    }
    
    
    public CaregiverDTO getCaregiverById(String caregiverId) {
        CaregiverDTO dto = caregiverMapper.getCaregiverById(caregiverId);
        if (dto != null) {
            dto.setCertificates(caregiverMapper.getCertificatesByCaregiverId(caregiverId));
            dto.setCareer(caregiverMapper.getCareerByCaregiverId(caregiverId));
        }
        return dto;
    }
    
    public List<CaregiverDTO> searchByKeyword(String keyword) {
        return caregiverMapper.searchCaregiversByKeyword(keyword);
    }
    
    
    public List<CaregiverDTO> searchCaregiversWithFilters(CaregiverFilterDTO filters) {
        return caregiverMapper.searchCaregiversWithFilters(filters);
    }
 
    // 개인 프로필 관리 메소들
    public CaregiverDTO getCaregiverProfileByMemberId(Long memberId) {
        CaregiverDTO dto = caregiverMapper.findCaregiverInfoByMemberId(memberId);
        if (dto != null) {
            dto.setCertificates(caregiverMapper.getCertificatesByCaregiverId(dto.getCaregiverId()));
            dto.setCareer(caregiverMapper.getCareerByCaregiverId(dto.getCaregiverId()));
        }
        return dto;
    }
    
    @Transactional
    public void updateCaregiverProfile(CaregiverDTO caregiverDTO) {
        // 기본 프로필 정보 업데이트
        caregiverMapper.updateCaregiverProfile(caregiverDTO);
        
        // 기존 자격증과 경력 삭제
        caregiverMapper.deleteCaregiverCertificates(caregiverDTO.getCaregiverId());
        caregiverMapper.deleteCaregiverCareer(caregiverDTO.getCaregiverId());
        
        // 새로운 자격증 추가
        if (caregiverDTO.getCertificates() != null) {
            for (String certificate : caregiverDTO.getCertificates()) {
                if (certificate != null && !certificate.trim().isEmpty()) {
                    caregiverMapper.insertCaregiverCertificate(caregiverDTO.getCaregiverId(), certificate);
                }
            }
        }
        
        // 새로운 경력 추가
        if (caregiverDTO.getCareer() != null) {
            for (CaregiverCareerDTO career : caregiverDTO.getCareer()) {
                if (career.getCompanyName() != null && !career.getCompanyName().trim().isEmpty()) {
                    caregiverMapper.insertCaregiverCareer(caregiverDTO.getCaregiverId(), career);
                }
            }
        }
    }
}

