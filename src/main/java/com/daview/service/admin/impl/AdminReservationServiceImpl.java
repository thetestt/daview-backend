package com.daview.service.admin.impl;

import com.daview.dto.ReservationDTO;
import com.daview.mapper.admin.AdminReservationMapper;
import com.daview.service.admin.AdminReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 관리자용 예약 관리 서비스 구현체
 */
@Service
public class AdminReservationServiceImpl implements AdminReservationService {

    private final AdminReservationMapper adminReservationMapper;

    @Autowired
    public AdminReservationServiceImpl(AdminReservationMapper adminReservationMapper) {
        this.adminReservationMapper = adminReservationMapper;
    }

    @Override
    public Map<String, Object> getAllReservationsWithPagination(
            int page, int size, String sortBy, String sortDirection,
            String status, String search, String startDate, String endDate, String prodType) {
        
        try {
            // 정렬 필드 검증
            sortBy = validateSortField(sortBy);
            
            // 페이지네이션 파라미터 계산
            int offset = page * size;
            
            // 검색 조건 설정
            Map<String, Object> params = new HashMap<>();
            params.put("offset", offset);
            params.put("limit", size);
            params.put("sortBy", sortBy);
            params.put("sortDirection", sortDirection.toUpperCase());
            
            if (status != null && !status.trim().isEmpty()) {
                params.put("status", status);
            }
            if (search != null && !search.trim().isEmpty()) {
                params.put("search", "%" + search.trim() + "%");
            }
            if (startDate != null && !startDate.trim().isEmpty()) {
                params.put("startDate", startDate);
            }
            if (endDate != null && !endDate.trim().isEmpty()) {
                params.put("endDate", endDate);
            }
            if (prodType != null && !prodType.trim().isEmpty()) {
                params.put("prodType", prodType);
            }
            
            System.out.println("=== AdminReservationService: 예약 목록 조회 ===");
            System.out.println("파라미터: " + params);
            
            // 데이터 조회
            List<Map<String, Object>> reservations = adminReservationMapper.getAllReservationsWithPagination(params);
            int totalCount = adminReservationMapper.getReservationCount(params);
            
            // 페이지 정보 계산
            int totalPages = (int) Math.ceil((double) totalCount / size);
            
            // 결과 맵 생성
            Map<String, Object> result = new HashMap<>();
            result.put("reservations", reservations);
            result.put("totalElements", totalCount);
            result.put("totalPages", totalPages);
            result.put("currentPage", page);
            result.put("pageSize", size);
            result.put("isFirst", page == 0);
            result.put("isLast", page == totalPages - 1);
            
            System.out.println("조회 결과 - 총 " + totalCount + "건, " + reservations.size() + "건 반환");
            
            return result;
            
        } catch (Exception e) {
            System.err.println("예약 목록 조회 중 오류: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("예약 목록 조회에 실패했습니다.", e);
        }
    }

    @Override
    public Map<String, Object> getReservationDetail(String rsvId) {
        try {
            System.out.println("=== AdminReservationService: 예약 상세 조회 - " + rsvId + " ===");
            
            Map<String, Object> reservationDetail = adminReservationMapper.getReservationDetail(rsvId);
            
            if (reservationDetail == null) {
                throw new RuntimeException("해당 예약을 찾을 수 없습니다: " + rsvId);
            }
            
            System.out.println("예약 상세 조회 완료: " + rsvId);
            return reservationDetail;
            
        } catch (Exception e) {
            System.err.println("예약 상세 조회 중 오류: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("예약 상세 조회에 실패했습니다.", e);
        }
    }

    @Override
    public boolean updateReservationStatus(String rsvId, String status) {
        try {
            System.out.println("=== AdminReservationService: 예약 상태 변경 - " + rsvId + " -> " + status + " ===");
            
            // 상태 값을 숫자로 변환 (기존 DB 구조에 맞춤)
            int rsvType = convertStatusToRsvType(status);
            
            Map<String, Object> params = new HashMap<>();
            params.put("rsvId", rsvId);
            params.put("rsvType", rsvType);
            
            int result = adminReservationMapper.updateReservationStatus(params);
            
            if (result > 0) {
                System.out.println("예약 상태 변경 성공: " + rsvId);
                return true;
            } else {
                System.out.println("예약 상태 변경 실패: " + rsvId);
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("예약 상태 변경 중 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, Object> getReservationStatistics() {
        try {
            System.out.println("=== AdminReservationService: 예약 통계 조회 ===");
            
            Map<String, Object> statistics = new HashMap<>();
            
            // 전체 예약 수
            int totalReservations = adminReservationMapper.getTotalReservationCount();
            statistics.put("totalReservations", totalReservations);
            
            // 상태별 예약 수
            List<Map<String, Object>> statusStats = adminReservationMapper.getReservationsByStatus();
            statistics.put("statusStats", statusStats);
            
            // 이번 달 예약 수
            int thisMonthReservations = adminReservationMapper.getThisMonthReservationCount();
            statistics.put("thisMonthReservations", thisMonthReservations);
            
            // 오늘 예약 수
            int todayReservations = adminReservationMapper.getTodayReservationCount();
            statistics.put("todayReservations", todayReservations);
            
            System.out.println("예약 통계 조회 완료");
            return statistics;
            
        } catch (Exception e) {
            System.err.println("예약 통계 조회 중 오류: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("예약 통계 조회에 실패했습니다.", e);
        }
    }

    @Override
    public List<Map<String, Object>> getMonthlyReservationStatistics() {
        try {
            System.out.println("=== AdminReservationService: 월별 예약 통계 조회 ===");
            
            List<Map<String, Object>> monthlyStats = adminReservationMapper.getMonthlyReservationStats();
            
            System.out.println("월별 예약 통계 조회 완료");
            return monthlyStats;
            
        } catch (Exception e) {
            System.err.println("월별 예약 통계 조회 중 오류: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Map<String, Object> getReservationStatusStatistics() {
        try {
            System.out.println("=== AdminReservationService: 예약 상태별 통계 조회 ===");
            
            List<Map<String, Object>> statusStats = adminReservationMapper.getReservationsByStatus();
            
            Map<String, Object> result = new HashMap<>();
            result.put("statusBreakdown", statusStats);
            
            System.out.println("예약 상태별 통계 조회 완료");
            return result;
            
        } catch (Exception e) {
            System.err.println("예약 상태별 통계 조회 중 오류: " + e.getMessage());
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    @Override
    public boolean deleteReservation(String rsvId) {
        try {
            System.out.println("=== AdminReservationService: 예약 삭제 - " + rsvId + " ===");
            
            int result = adminReservationMapper.deleteReservation(rsvId);
            
            if (result > 0) {
                System.out.println("예약 삭제 성공: " + rsvId);
                return true;
            } else {
                System.out.println("예약 삭제 실패: " + rsvId);
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("예약 삭제 중 오류: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String validateSortField(String sortBy) {
        // 허용되는 정렬 필드들
        Set<String> allowedSortFields = Set.of(
            "rsvId", "rsvDate", "memberId", "prodNm", "prodPrice", 
            "rsvType", "rsvCnt", "prodType", "createdAt"
        );
        
        if (sortBy == null || !allowedSortFields.contains(sortBy)) {
            return "rsvDate"; // 기본값
        }
        
        return sortBy;
    }

    /**
     * 상태 문자열을 rsvType 숫자로 변환
     */
    private int convertStatusToRsvType(String status) {
        switch (status.toUpperCase()) {
            case "PENDING":
                return 1; // 대기중
            case "APPROVED":
                return 2; // 승인됨
            case "REJECTED":
                return 0; // 거절됨
            case "CANCELLED":
                return 4; // 취소됨
            case "COMPLETED":
                return 3; // 완료됨
            default:
                return 1; // 기본값: 대기중
        }
    }
} 