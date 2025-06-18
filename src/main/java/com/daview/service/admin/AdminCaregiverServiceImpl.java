package com.daview.service.admin; // 경로 수정

import com.daview.dto.CaregiverDTO;
import com.daview.mapper.admin.AdminCaregiverMapper; // Mapper 임포트
import com.daview.service.admin.AdminCaregiverService; // 서비스 인터페이스 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCaregiverServiceImpl implements AdminCaregiverService {

    @Autowired
    private AdminCaregiverMapper caregiverMapper;

    @Override
    public void addCaregiver(CaregiverDTO caregiverDTO) {
        caregiverMapper.insertCaregiver(caregiverDTO);
    }

    @Override
    public List<CaregiverDTO> getAllCaregivers() {
        return caregiverMapper.getAllCaregivers();
    }

    @Override
    public void updateCaregiver(Long id, CaregiverDTO caregiverDTO) {
        caregiverDTO.setMemberId(id);
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
}
