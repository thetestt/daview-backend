package com.daview.service;

import com.daview.dto.PaymentDTO;

public interface PaymentService {
	int insertPayment(PaymentDTO payment);
	
	PaymentDTO selectPaymentById(String pymId);
}
