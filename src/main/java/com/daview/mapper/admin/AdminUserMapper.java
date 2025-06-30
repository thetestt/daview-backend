package com.daview.mapper.admin;

import com.daview.dto.AdminUserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminUserMapper {
    
    // 전체 유저 목록 조회 (페이지네이션 지원)
    List<AdminUserDto> findAllUsersForAdmin(
        @Param("offset") int offset,
        @Param("limit") int limit,
        @Param("sortBy") String sortBy,
        @Param("sortDirection") String sortDirection,
        @Param("role") String role,
        @Param("search") String search,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate
    );
    
    // 전체 유저 수 조회 (필터링 조건 적용)
    int countUsersForAdmin(
        @Param("role") String role,
        @Param("search") String search,
        @Param("startDate") String startDate,
        @Param("endDate") String endDate
    );
    
    // 특정 유저 상세 정보 조회
    AdminUserDto findUserDetailById(@Param("memberId") Long memberId);
    
    // 유저 상태 변경 (활성/비활성)
    int updateUserStatus(@Param("memberId") Long memberId, @Param("withdrawn") Integer withdrawn);
    
    // 유저 탈퇴 처리 (개인정보 삭제)
    int withdrawUser(@Param("memberId") Long memberId);
    
    // 역할별 유저 수 통계
    List<Map<String, Object>> getUserCountByRole();
    
    // 상태별 유저 수 통계
    List<Map<String, Object>> getUserCountByStatus();
    
    // 월별 가입자 수 통계 (최근 12개월)
    List<Map<String, Object>> getMonthlySignupStats();
    
    // 특정 역할의 유저 목록 조회 (상품 등록 시 사용)
    List<AdminUserDto> findUsersByRole(@Param("role") String role);
    
    /**
     * 신규 가입자 수 조회 (기간별)
     */
    int getNewUsersCount(@Param("period") String period);
} 