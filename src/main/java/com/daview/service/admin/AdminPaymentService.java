package com.daview.service.admin;

import com.daview.dto.PaymentDTO;

import java.util.List;
import java.util.Map;

public interface AdminPaymentService {
    
    // 결제 목록 조회 (페이징, 검색, 필터링)
    Map<String, Object> getPaymentList(Map<String, Object> params);
    
    // 결제 상세 조회 (String으로 변경)
    PaymentDTO getPaymentById(String paymentId);
    
    // 결제 상태 변경
    boolean updatePaymentStatus(String paymentId, Integer status);
    
    // 전체 결제 통계 조회
    Map<String, Object> getPaymentTotalStatistics();
    
    // 상태별 결제 통계
    List<Map<String, Object>> getPaymentStatusStatistics();
    
    // 최근 7일 결제 통계
    List<Map<String, Object>> getRecentPaymentStatistics();
    
    // 결제 방법별 통계
    List<Map<String, Object>> getPaymentMethodStatistics();
    
    // 결제 방법 목록 조회
    List<Map<String, Object>> getPaymentMethods();
    
    // 예약 타입 목록 조회
    List<Map<String, Object>> getReservationTypes();
    
    // 월별 결제 통계
    List<Map<String, Object>> getMonthlyPaymentStatistics();
    
    // 회원별 결제 통계
    List<Map<String, Object>> getMemberPaymentStatistics();
    
    // 결제-예약 연결 생성
    boolean createPaymentReservationMap(String paymentId, String reservationId);
    
    // 결제-예약 연결 삭제
    boolean deletePaymentReservationMap(String paymentId, String reservationId);
} 