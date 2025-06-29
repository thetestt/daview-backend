package com.daview.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daview.dto.PaymentReservationMapDTO;
import com.daview.mapper.PaymentReservationMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentReservationServiceImpl implements PaymentReservationService {
	
	private final PaymentReservationMapper mapper;
	
	@Override
	@Transactional
	public int insertMap(List<PaymentReservationMapDTO> dtoList) {
		int total = 0;
		for(PaymentReservationMapDTO dto : dtoList) {
			int result = mapper.insertMap(dto);
			if(result != 1) {
				throw new RuntimeException("예약 ID " + dto.getRsvId() + "매핑 실패");
			}
			total += result;
		}
		return total;
	}
}
