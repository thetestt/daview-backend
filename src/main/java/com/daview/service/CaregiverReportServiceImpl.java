package com.daview.service;

import com.daview.dto.CaregiverReportDTO;
import com.daview.mapper.CaregiverReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CaregiverReportServiceImpl implements CaregiverReportService {
    
    @Autowired
    private CaregiverReportMapper caregiverReportMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<CaregiverReportDTO> getReportsByCaregiverId(Long caregiverId, int page, int size) {
        int offset = page * size;
        return caregiverReportMapper.getReportsByCaregiverId(caregiverId, offset, size);
    }
    
    @Override
    @Transactional(readOnly = true)
    public int getReportsCountByCaregiverId(Long caregiverId) {
        return caregiverReportMapper.getReportsCountByCaregiverId(caregiverId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CaregiverReportDTO getReportByDate(Long caregiverId, LocalDate reportDate) {
        return caregiverReportMapper.getReportByDate(caregiverId, reportDate);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CaregiverReportDTO getReportById(Long id) {
        return caregiverReportMapper.getReportById(id);
    }
    
    @Override
    public CaregiverReportDTO createReport(CaregiverReportDTO report) {
        // 기본값 설정
        if (report.getStatus() == null) {
            report.setStatus("pending");
        }
        if (report.getReportDate() == null) {
            report.setReportDate(LocalDate.now());
        }
        
        int result = caregiverReportMapper.insertReport(report);
        if (result > 0) {
            return caregiverReportMapper.getReportById(report.getId());
        }
        return null;
    }
    
    @Override
    public CaregiverReportDTO updateReport(CaregiverReportDTO report) {
        int result = caregiverReportMapper.updateReport(report);
        if (result > 0) {
            return caregiverReportMapper.getReportById(report.getId());
        }
        return null;
    }
    
    @Override
    public boolean deleteReport(Long id) {
        int result = caregiverReportMapper.deleteReport(id);
        return result > 0;
    }
    
    @Override
    public boolean updateReportStatus(Long id, String status) {
        int result = caregiverReportMapper.updateReportStatus(id, status);
        return result > 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CaregiverReportDTO> getRecentReports(Long caregiverId, int limit) {
        return caregiverReportMapper.getRecentReports(caregiverId, limit);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CaregiverReportDTO> getReportsByDateRange(Long caregiverId, LocalDate startDate, LocalDate endDate) {
        return caregiverReportMapper.getReportsByDateRange(caregiverId, startDate, endDate);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CaregiverReportDTO> getReportsByStatus(Long caregiverId, String status) {
        return caregiverReportMapper.getReportsByStatus(caregiverId, status);
    }
} 