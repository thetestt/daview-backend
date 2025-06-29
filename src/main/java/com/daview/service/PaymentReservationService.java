package com.daview.service;

import java.util.List;

import com.daview.dto.PaymentReservationMapDTO;

public interface PaymentReservationService {

	int insertMap(List<PaymentReservationMapDTO> dtoList);
}
