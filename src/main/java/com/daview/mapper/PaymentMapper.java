package com.daview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.daview.dto.PaymentDTO;
import com.daview.dto.PaymentWithReservationsDTO;

@Mapper
public interface PaymentMapper {
	int insertPayment(PaymentDTO payment);

	PaymentDTO selectPaymentById(String pymId);

	List<PaymentWithReservationsDTO> selectPaymentWithReservationsByMemberId(Long memberId);

	List<String> getProdNmList(Long memberId);

	int updatePaymentStatusByImpUid(@Param("impUid") String impUid, @Param("pymStatus") int pymStatus, @Param("refundReason") String refundReason);
	
	List<PaymentWithReservationsDTO> selectRefundedPaymentsByMemberId(Long memberId);
}
