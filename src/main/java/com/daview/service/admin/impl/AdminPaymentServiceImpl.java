package com.daview.service.admin.impl;

import com.daview.dto.PaymentDTO;
import com.daview.mapper.admin.AdminPaymentMapper;
import com.daview.service.admin.AdminPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminPaymentServiceImpl implements AdminPaymentService {

    @Autowired
    private AdminPaymentMapper adminPaymentMapper;

    @Override
    public Map<String, Object> getPaymentList(Map<String, Object> params) {
        try {
            List<PaymentDTO> payments = adminPaymentMapper.selectPaymentList(params);
            Long totalCount = adminPaymentMapper.selectPaymentCount(params);

            Map<String, Object> result = new HashMap<>();
            result.put("payments", payments);
            result.put("totalCount", totalCount != null ? totalCount : 0);

            return result;
        } catch (Exception e) {
            throw new RuntimeException("결제 목록 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public PaymentDTO getPaymentById(String paymentId) {
        try {
            return adminPaymentMapper.selectPaymentById(paymentId);
        } catch (Exception e) {
            throw new RuntimeException("결제 상세 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean updatePaymentStatus(String paymentId, Integer status) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("paymentId", paymentId);
            params.put("status", status);
            
            return adminPaymentMapper.updatePaymentStatus(params) > 0;
        } catch (Exception e) {
            throw new RuntimeException("결제 상태 변경 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> getPaymentTotalStatistics() {
        try {
            return adminPaymentMapper.selectPaymentTotalStatistics();
        } catch (Exception e) {
            throw new RuntimeException("전체 결제 통계 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> getPaymentStatusStatistics() {
        try {
            return adminPaymentMapper.selectPaymentStatusStatistics();
        } catch (Exception e) {
            throw new RuntimeException("상태별 결제 통계 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> getRecentPaymentStatistics() {
        try {
            return adminPaymentMapper.selectRecentPaymentStatistics();
        } catch (Exception e) {
            throw new RuntimeException("최근 7일 결제 통계 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> getPaymentMethodStatistics() {
        try {
            return adminPaymentMapper.selectPaymentMethodStatistics();
        } catch (Exception e) {
            throw new RuntimeException("결제 방법별 통계 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> getPaymentMethods() {
        try {
            return adminPaymentMapper.selectPaymentMethods();
        } catch (Exception e) {
            throw new RuntimeException("결제 방법 목록 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> getReservationTypes() {
        try {
            return adminPaymentMapper.selectReservationTypes();
        } catch (Exception e) {
            throw new RuntimeException("예약 타입 목록 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> getMonthlyPaymentStatistics() {
        try {
            return adminPaymentMapper.selectMonthlyPaymentStatistics();
        } catch (Exception e) {
            throw new RuntimeException("월별 결제 통계 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> getMemberPaymentStatistics() {
        try {
            return adminPaymentMapper.selectMemberPaymentStatistics();
        } catch (Exception e) {
            throw new RuntimeException("회원별 결제 통계 조회 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean createPaymentReservationMap(String paymentId, String reservationId) {
        try {
            return adminPaymentMapper.insertPaymentReservationMap(paymentId, reservationId) > 0;
        } catch (Exception e) {
            throw new RuntimeException("결제-예약 연결 생성 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deletePaymentReservationMap(String paymentId, String reservationId) {
        try {
            return adminPaymentMapper.deletePaymentReservationMap(paymentId, reservationId) > 0;
        } catch (Exception e) {
            throw new RuntimeException("결제-예약 연결 삭제 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
} 