package com.daview.service.admin.impl;

import com.daview.dto.AdminUserDto;
import com.daview.mapper.admin.AdminUserMapper;
import com.daview.service.admin.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 관리자용 유저 관리 서비스 구현
 * 실제 비즈니스 로직과 데이터 처리 담당
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
    
    private final AdminUserMapper adminUserMapper;
    
    // 유효한 정렬 필드 목록
    private static final Set<String> VALID_SORT_FIELDS = Set.of(
        "memberId", "username", "name", "email", "role", "createAt", "withdrawn"
    );
    
    @Autowired
    public AdminUserServiceImpl(AdminUserMapper adminUserMapper) {
        this.adminUserMapper = adminUserMapper;
    }
    
    @Override
    public Map<String, Object> getAllUsersWithPagination(
            int page, int size, String sortBy, String sortDirection,
            String role, String search, String startDate, String endDate) {
        
        try {
            System.out.println("=== AdminUserService: 유저 목록 조회 시작 ===");
            System.out.println("페이지: " + page + ", 크기: " + size);
            System.out.println("정렬: " + sortBy + " " + sortDirection);
            System.out.println("필터 - 역할: " + role + ", 검색어: " + search);
            System.out.println("날짜 필터 - 시작: " + startDate + ", 종료: " + endDate);
            
            // 입력값 검증 및 기본값 설정
            if (page < 0) page = 0;
            if (size <= 0) size = 10;
            if (size > 100) size = 100; // 최대 100개로 제한
            
            sortBy = validateSortField(sortBy);
            if (!"ASC".equalsIgnoreCase(sortDirection) && !"DESC".equalsIgnoreCase(sortDirection)) {
                sortDirection = "DESC";
            }
            
            // 오프셋 계산
            int offset = page * size;
            
            // 데이터 조회
            List<AdminUserDto> users = adminUserMapper.findAllUsersForAdmin(
                offset, size, sortBy, sortDirection, role, search, startDate, endDate
            );
            
            // 전체 개수 조회
            int totalElements = adminUserMapper.countUsersForAdmin(role, search, startDate, endDate);
            int totalPages = (int) Math.ceil((double) totalElements / size);
            
            // 결과 맵 생성
            Map<String, Object> result = new HashMap<>();
            result.put("users", users);
            result.put("totalElements", totalElements);
            result.put("totalPages", totalPages);
            result.put("currentPage", page);
            result.put("pageSize", size);
            result.put("hasNext", page < totalPages - 1);
            result.put("hasPrevious", page > 0);
            
            System.out.println("조회 완료 - 유저 수: " + users.size() + ", 전체: " + totalElements);
            
            return result;
            
        } catch (Exception e) {
            System.err.println("=== AdminUserService: 유저 목록 조회 오류 ===");
            e.printStackTrace();
            throw new RuntimeException("유저 목록 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Map<String, Object> getUserDetail(Long memberId) {
        try {
            System.out.println("=== AdminUserService: 유저 상세 조회 - ID: " + memberId + " ===");
            
            if (memberId == null || memberId <= 0) {
                throw new IllegalArgumentException("유효하지 않은 회원 ID입니다.");
            }
            
            AdminUserDto user = adminUserMapper.findUserDetailById(memberId);
            
            if (user == null) {
                throw new RuntimeException("해당 ID의 유저를 찾을 수 없습니다: " + memberId);
            }
            
            Map<String, Object> result = new HashMap<>();
            result.put("user", user);
            
            System.out.println("유저 상세 조회 완료 - " + user.getUsername());
            
            return result;
            
        } catch (Exception e) {
            System.err.println("=== AdminUserService: 유저 상세 조회 오류 ===");
            e.printStackTrace();
            throw new RuntimeException("유저 상세 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean updateUserStatus(String memberId, String status) {
        try {
            System.out.println("=== AdminUserService: 유저 상태 변경 ===");
            System.out.println("회원 ID: " + memberId + ", 새 상태: " + status);
            
            // 입력값 검증
            if (memberId == null || memberId.trim().isEmpty()) {
                throw new IllegalArgumentException("회원 ID가 필요합니다.");
            }
            
            if (status == null || status.trim().isEmpty()) {
                throw new IllegalArgumentException("상태 값이 필요합니다.");
            }
            
            Long memberIdLong;
            try {
                memberIdLong = Long.parseLong(memberId);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("유효하지 않은 회원 ID 형식입니다: " + memberId);
            }
            
            // 상태 값 변환
            Integer withdrawnValue;
            switch (status.toUpperCase()) {
                case "ACTIVE":
                    withdrawnValue = 0;
                    break;
                case "INACTIVE":
                case "WITHDRAWN":
                    withdrawnValue = 1;
                    break;
                default:
                    throw new IllegalArgumentException("유효하지 않은 상태 값입니다: " + status);
            }
            
            // 데이터베이스 업데이트
            int updatedRows = adminUserMapper.updateUserStatus(memberIdLong, withdrawnValue);
            
            boolean success = updatedRows > 0;
            System.out.println("상태 변경 " + (success ? "성공" : "실패") + " - 영향받은 행: " + updatedRows);
            
            return success;
            
        } catch (Exception e) {
            System.err.println("=== AdminUserService: 유저 상태 변경 오류 ===");
            e.printStackTrace();
            throw new RuntimeException("유저 상태 변경 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean withdrawUser(String memberId) {
        try {
            System.out.println("=== AdminUserService: 유저 탈퇴 처리 ===");
            System.out.println("회원 ID: " + memberId);
            
            // 입력값 검증
            if (memberId == null || memberId.trim().isEmpty()) {
                throw new IllegalArgumentException("회원 ID가 필요합니다.");
            }
            
            Long memberIdLong;
            try {
                memberIdLong = Long.parseLong(memberId);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("유효하지 않은 회원 ID 형식입니다: " + memberId);
            }
            
            // 사용자 존재 여부 확인
            AdminUserDto user = adminUserMapper.findUserDetailById(memberIdLong);
            if (user == null) {
                throw new RuntimeException("해당 ID의 유저를 찾을 수 없습니다: " + memberId);
            }
            
            // 이미 탈퇴한 유저인지 확인
            if (user.getWithdrawn() != null && user.getWithdrawn() == 1) {
                throw new RuntimeException("이미 탈퇴한 유저입니다.");
            }
            
            // 데이터베이스 업데이트 (phone, username 제외하고 모든 컬럼을 null로, withdrawn = 1)
            int updatedRows = adminUserMapper.withdrawUser(memberIdLong);
            
            boolean success = updatedRows > 0;
            System.out.println("탈퇴 처리 " + (success ? "성공" : "실패") + " - 영향받은 행: " + updatedRows);
            
            return success;
            
        } catch (Exception e) {
            System.err.println("=== AdminUserService: 유저 탈퇴 처리 오류 ===");
            e.printStackTrace();
            throw new RuntimeException("유저 탈퇴 처리 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getUserStatistics() {
        try {
            System.out.println("=== AdminUserService: 유저 통계 조회 ===");
            
            // 역할별 통계
            List<Map<String, Object>> roleStats = adminUserMapper.getUserCountByRole();
            
            // 상태별 통계
            List<Map<String, Object>> statusStats = adminUserMapper.getUserCountByStatus();
            
            // 결과 맵 생성
            Map<String, Object> result = new HashMap<>();
            result.put("roleStatistics", roleStats);
            result.put("statusStatistics", statusStats);
            
            // 총 사용자 수 계산
            int totalUsers = statusStats.stream()
                .mapToInt(stat -> ((Number) stat.get("count")).intValue())
                .sum();
            result.put("totalUsers", totalUsers);
            
            // 활성 사용자 수 계산
            int activeUsers = statusStats.stream()
                .filter(stat -> "ACTIVE".equals(stat.get("status")))
                .mapToInt(stat -> ((Number) stat.get("count")).intValue())
                .sum();
            result.put("activeUsers", activeUsers);
            
            System.out.println("통계 조회 완료 - 총 사용자: " + totalUsers + ", 활성 사용자: " + activeUsers);
            
            return result;
            
        } catch (Exception e) {
            System.err.println("=== AdminUserService: 유저 통계 조회 오류 ===");
            e.printStackTrace();
            throw new RuntimeException("유저 통계 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Map<String, Object>> getSignupStatistics() {
        try {
            System.out.println("=== AdminUserService: 가입 통계 조회 ===");
            
            List<Map<String, Object>> monthlyStats = adminUserMapper.getMonthlySignupStats();
            
            System.out.println("월별 가입 통계 조회 완료 - 데이터 수: " + monthlyStats.size());
            
            return monthlyStats;
            
        } catch (Exception e) {
            System.err.println("=== AdminUserService: 가입 통계 조회 오류 ===");
            e.printStackTrace();
            throw new RuntimeException("가입 통계 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<AdminUserDto> getUsersByRole(String role) {
        try {
            System.out.println("=== AdminUserService: 역할별 유저 조회 - 역할: " + role + " ===");
            
            if (role == null || role.trim().isEmpty()) {
                throw new IllegalArgumentException("역할이 필요합니다.");
            }
            
            List<AdminUserDto> users = adminUserMapper.findUsersByRole(role.trim().toUpperCase());
            
            System.out.println("역할별 유저 조회 완료 - 유저 수: " + users.size());
            
            return users;
            
        } catch (Exception e) {
            System.err.println("=== AdminUserService: 역할별 유저 조회 오류 ===");
            e.printStackTrace();
            throw new RuntimeException("역할별 유저 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Map<String, Object> getNewUserStatistics() {
        try {
            System.out.println("=== AdminUserService: 신규 가입자 통계 조회 ===");
            
            // 오늘 가입자 수
            int todayNewUsers = adminUserMapper.getNewUsersCount("TODAY");
            
            // 이번 주 가입자 수
            int thisWeekNewUsers = adminUserMapper.getNewUsersCount("WEEK");
            
            // 이번 달 가입자 수
            int thisMonthNewUsers = adminUserMapper.getNewUsersCount("MONTH");
            
            Map<String, Object> result = new HashMap<>();
            result.put("todayNewUsers", todayNewUsers);
            result.put("thisWeekNewUsers", thisWeekNewUsers);
            result.put("thisMonthNewUsers", thisMonthNewUsers);
            
            System.out.println("신규 가입자 통계 조회 완료 - 오늘: " + todayNewUsers + ", 이번 주: " + thisWeekNewUsers + ", 이번 달: " + thisMonthNewUsers);
            
            return result;
            
        } catch (Exception e) {
            System.err.println("=== AdminUserService: 신규 가입자 통계 조회 오류 ===");
            e.printStackTrace();
            throw new RuntimeException("신규 가입자 통계 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String validateSortField(String sortBy) {
        if (sortBy == null || sortBy.trim().isEmpty()) {
            return "createAt"; // 기본값
        }
        
        String trimmedSortBy = sortBy.trim();
        if (VALID_SORT_FIELDS.contains(trimmedSortBy)) {
            return trimmedSortBy;
        }
        
        System.out.println("유효하지 않은 정렬 필드: " + sortBy + ", 기본값 사용: createAt");
        return "createAt"; // 기본값으로 대체
    }
} 