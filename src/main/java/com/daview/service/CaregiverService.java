package com.daview.service;

import com.daview.dto.CaregiverDTO;
import java.util.List;

public interface CaregiverService {
    List<CaregiverDTO> getAllCaregivers();
    CaregiverDTO getCaregiverById(String caregiverId);
    CaregiverDTO getCaregiverByMemberId(Long memberId);
} 