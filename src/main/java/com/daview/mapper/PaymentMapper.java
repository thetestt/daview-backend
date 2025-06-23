package com.daview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.daview.dto.PaymentDTO;
import com.daview.dto.PaymentWithReservationsDTO;

@Mapper
public interface PaymentMapper {
	int insertPayment(PaymentDTO payment);
	
	PaymentDTO selectPaymentById(String pymId);
	
	List<PaymentWithReservationsDTO> selectPaymentWithReservationsByMemberId(Long memberId);
}
