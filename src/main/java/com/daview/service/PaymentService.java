package com.daview.service;

import java.util.List;

import com.daview.dto.PaymentDTO;
import com.daview.dto.PaymentWithReservationsDTO;

public interface PaymentService {
	int insertPayment(PaymentDTO payment);
	
	PaymentDTO selectPaymentById(String pymId);
	
	List<PaymentWithReservationsDTO> selectPaymentWithReservationsByMemberId(Long memberId);
	
	List<String> getProdNmList(Long memberId);
	
	boolean cancelPayment(String impUid, String reason) throws Exception;
	
	List<PaymentWithReservationsDTO> getRefundedPaymentsByMemberId(Long memberId);
}
