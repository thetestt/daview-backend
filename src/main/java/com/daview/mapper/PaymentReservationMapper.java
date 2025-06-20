package com.daview.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.daview.dto.PaymentReservationMapDTO;

@Mapper
public interface PaymentReservationMapper {
	int insertMap(PaymentReservationMapDTO dto);
}
