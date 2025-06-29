package com.daview.service;

import java.util.List;

import com.daview.dto.ReservationDTO;

public interface ReservationService {
	int insertReservation(ReservationDTO reservation);

	List<ReservationDTO> selectReservationById(Long memberId);

	int deleteReservation(String rsvId);

	int deleteAllReservation();
	
	void updateReservationCount(List<ReservationDTO> updates);
	
	int updateReservationStatus(List<String> rsvIds, int rsvType);
	
	List<ReservationDTO> selectReservationByPaymentId(String pymId);

}
