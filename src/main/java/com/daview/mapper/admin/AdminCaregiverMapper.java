package com.daview.mapper.admin; // 경로 수정

import com.daview.dto.CaregiverDTO; // DTO 임포트
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminCaregiverMapper {

    void insertCaregiver(CaregiverDTO caregiverDTO);

    List<CaregiverDTO> getAllCaregivers();

    void updateCaregiver(CaregiverDTO caregiverDTO);

    void deleteCaregiver(Long id);

    CaregiverDTO getCaregiverById(Long id);
}


