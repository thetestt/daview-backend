package com.daview.service;

import org.springframework.stereotype.Service;

import com.daview.dto.PaymentReservationMapDTO;
import com.daview.mapper.PaymentReservationMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentReservationServiceImpl implements PaymentReservationService {
	
	private final PaymentReservationMapper mapper;
	
	@Override
	public int insertMap(PaymentReservationMapDTO dto) {
		return mapper.insertMap(dto);
	}
}
