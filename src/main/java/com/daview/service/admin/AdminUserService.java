package com.daview.service.admin;

import com.daview.dto.AdminUserDto;

import java.util.List;
import java.util.Map;

/**
 * 관리자용 유저 관리 서비스 인터페이스
 * 유저 조회, 상태 관리, 통계 등의 비즈니스 로직 정의
 */
public interface AdminUserService {
    
    /**
     * 전체 유저 목록 조회 (페이지네이션과 필터링 지원)
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     * @param sortBy 정렬 기준
     * @param sortDirection 정렬 방향
     * @param role 역할 필터
     * @param search 검색어
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 페이징된 유저 목록과 메타 데이터
     */
    Map<String, Object> getAllUsersWithPagination(
        int page, int size, String sortBy, String sortDirection,
        String role, String search, String startDate, String endDate
    );
    
    /**
     * 특정 유저 상세 정보 조회
     * @param memberId 회원 ID
     * @return 유저 상세 정보
     */
    Map<String, Object> getUserDetail(Long memberId);
    
    /**
     * 유저 상태 변경
     * @param memberId 회원 ID
     * @param status 새로운 상태 ("ACTIVE" 또는 "INACTIVE")
     * @return 변경 성공 여부
     */
    boolean updateUserStatus(String memberId, String status);
    
    /**
     * 전체 유저 통계 조회
     * @return 통계 데이터 맵
     */
    Map<String, Object> getUserStatistics();
    
    /**
     * 월별 가입자 수 통계 조회
     * @return 월별 가입자 수 리스트
     */
    List<Map<String, Object>> getSignupStatistics();
    
    /**
     * 신규 가입자 통계 조회 (일/주/월별)
     * @return 신규 가입자 통계 데이터
     */
    Map<String, Object> getNewUserStatistics();
    
    /**
     * 특정 역할의 유저 목록 조회 (상품 등록 시 사용)
     * @param role 역할
     * @return 해당 역할의 유저 목록
     */
    List<AdminUserDto> getUsersByRole(String role);
    
    /**
     * 정렬 필드 유효성 검증
     * @param sortBy 정렬 필드
     * @return 유효한 정렬 필드
     */
    String validateSortField(String sortBy);
} 