package com.daview.mapper.admin;

import com.daview.dto.PaymentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminPaymentMapper {
    
    // 결제 목록 조회
    List<PaymentDTO> selectPaymentList(@Param("params") Map<String, Object> params);
    
    // 결제 개수 조회
    Long selectPaymentCount(@Param("params") Map<String, Object> params);
    
    // 결제 상세 조회 (String으로 변경)
    PaymentDTO selectPaymentById(@Param("paymentId") String paymentId);
    
    // 결제 상태 변경
    int updatePaymentStatus(@Param("params") Map<String, Object> params);
    
    // 전체 결제 통계
    Map<String, Object> selectPaymentTotalStatistics();
    
    // 상태별 결제 통계
    List<Map<String, Object>> selectPaymentStatusStatistics();
    
    // 최근 7일 결제 통계
    List<Map<String, Object>> selectRecentPaymentStatistics();
    
    // 결제 방법별 통계
    List<Map<String, Object>> selectPaymentMethodStatistics();
    
    // 결제 방법 목록 조회
    List<Map<String, Object>> selectPaymentMethods();
    
    // 예약 타입 목록 조회
    List<Map<String, Object>> selectReservationTypes();
    
    // 월별 결제 통계
    List<Map<String, Object>> selectMonthlyPaymentStatistics();
    
    // 회원별 결제 통계
    List<Map<String, Object>> selectMemberPaymentStatistics();
    
    // 결제-예약 연결 생성
    int insertPaymentReservationMap(@Param("paymentId") String paymentId, @Param("reservationId") String reservationId);
    
    // 결제-예약 연결 삭제
    int deletePaymentReservationMap(@Param("paymentId") String paymentId, @Param("reservationId") String reservationId);
} 