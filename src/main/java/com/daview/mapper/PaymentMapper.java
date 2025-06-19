package com.daview.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.daview.dto.PaymentDTO;

@Mapper
public interface PaymentMapper {
	int insertPayment(PaymentDTO payment);
	
	PaymentDTO selectPaymentById(String pymId);
}
