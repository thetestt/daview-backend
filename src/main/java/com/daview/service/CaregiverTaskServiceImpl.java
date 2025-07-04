package com.daview.service;

import com.daview.dto.CaregiverTaskDTO;
import com.daview.mapper.CaregiverTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class CaregiverTaskServiceImpl implements CaregiverTaskService {
    
    @Autowired
    private CaregiverTaskMapper caregiverTaskMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<CaregiverTaskDTO> getTasksByCaregiverId(Long caregiverId, int page, int size) {
        int offset = page * size;
        return caregiverTaskMapper.getTasksByCaregiverId(caregiverId, offset, size);
    }
    
    @Override
    @Transactional(readOnly = true)
    public int getTasksCountByCaregiverId(Long caregiverId) {
        return caregiverTaskMapper.getTasksCountByCaregiverId(caregiverId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CaregiverTaskDTO> getTasksByDate(Long caregiverId, LocalDate taskDate) {
        return caregiverTaskMapper.getTasksByDate(caregiverId, taskDate);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CaregiverTaskDTO getTaskById(Long id) {
        return caregiverTaskMapper.getTaskById(id);
    }
    
    @Override
    public CaregiverTaskDTO createTask(CaregiverTaskDTO task) {
        // 기본값 설정
        if (task.getCompleted() == null) {
            task.setCompleted(false);
        }
        if (task.getTaskDate() == null) {
            task.setTaskDate(LocalDate.now());
        }
        
        // ID 수동 생성 (현재 시간 기반)
        if (task.getId() == null) {
            task.setId(System.currentTimeMillis());
        }
        
        int result = caregiverTaskMapper.insertTask(task);
        if (result > 0) {
            return task; // 생성된 task 그대로 반환
        }
        return null;
    }
    
    @Override
    public CaregiverTaskDTO updateTask(CaregiverTaskDTO task) {
        int result = caregiverTaskMapper.updateTask(task);
        if (result > 0) {
            return caregiverTaskMapper.getTaskById(task.getId());
        }
        return null;
    }
    
    @Override
    public boolean deleteTask(Long id) {
        int result = caregiverTaskMapper.deleteTask(id);
        return result > 0;
    }
    
    @Override
    public boolean toggleTaskCompletion(Long id, Boolean completed) {
        int result = caregiverTaskMapper.toggleTaskCompletion(id, completed);
        return result > 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CaregiverTaskDTO> getTodayTasks(Long caregiverId) {
        return caregiverTaskMapper.getTodayTasks(caregiverId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CaregiverTaskDTO> getWeeklyTasks(Long caregiverId) {
        LocalDate now = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate startOfWeek = now.with(weekFields.dayOfWeek(), 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        
        return caregiverTaskMapper.getWeeklyTasks(caregiverId, startOfWeek, endOfWeek);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CaregiverTaskDTO> getTasksByDateRange(Long caregiverId, LocalDate startDate, LocalDate endDate) {
        return caregiverTaskMapper.getTasksByDateRange(caregiverId, startDate, endDate);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CaregiverTaskDTO> getTasksByCompletion(Long caregiverId, Boolean completed) {
        return caregiverTaskMapper.getTasksByCompletion(caregiverId, completed);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CaregiverTaskDTO> getTasksByType(Long caregiverId, String taskType) {
        return caregiverTaskMapper.getTasksByType(caregiverId, taskType);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CaregiverTaskDTO> getRecentTasks(Long caregiverId, int limit) {
        return caregiverTaskMapper.getRecentTasks(caregiverId, limit);
    }
    
    @Override
    @Transactional(readOnly = true)
    public int getTodayCompletedTasksCount(Long caregiverId) {
        return caregiverTaskMapper.getTodayCompletedTasksCount(caregiverId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public int getWeeklyTasksCount(Long caregiverId) {
        LocalDate now = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate startOfWeek = now.with(weekFields.dayOfWeek(), 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        
        return caregiverTaskMapper.getWeeklyTasksCount(caregiverId, startOfWeek, endOfWeek);
    }
} 