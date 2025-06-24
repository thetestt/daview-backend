package com.daview.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daview.dto.PaymentDTO;
import com.daview.dto.PaymentWithReservationsDTO;
import com.daview.mapper.PaymentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	
	private final PaymentMapper paymentMapper;
	
	@Override
	public int insertPayment(PaymentDTO payment) {
		return paymentMapper.insertPayment(payment);
	}
	
	@Override
	public PaymentDTO selectPaymentById(String pymId) {
		return paymentMapper.selectPaymentById(pymId);
	}
	
	@Override
	public List<PaymentWithReservationsDTO> selectPaymentWithReservationsByMemberId(Long memberId){
		return paymentMapper.selectPaymentWithReservationsByMemberId(memberId);
	}
}
