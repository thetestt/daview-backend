package com.daview.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daview.dto.ReservationDTO;
import com.daview.mapper.ReservationMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

	private final ReservationMapper reservationMapper;

	@Override
	public int insertReservation(ReservationDTO reservation) {
		return reservationMapper.insertReservation(reservation);
	}

	@Override
	public List<ReservationDTO> selectReservationById(Long memberId) {
		return reservationMapper.selectReservationById(memberId);
	}

	@Override
	public int deleteReservation(String rsvId) {
		return reservationMapper.deleteReservation(rsvId);
	}

	@Override
	@Transactional
	public int deleteAllReservation() {
		return reservationMapper.deleteAllReservation();
	}

	@Override
	@Transactional
	public void updateReservationCount(List<ReservationDTO> updates) {
		for (ReservationDTO update : updates) {
			int rows = reservationMapper.updateReservationCount(update.getRsvId(), update.getRsvCnt());

			if (rows == 0) {
				throw new RuntimeException("예약 ID " + update.getRsvId() + "에 해당하는 데이터가 없습니다.");
			}
		}
	}

	@Override
	@Transactional
	public int updateReservationStatus(List<String> rsvIds, int rsvType) {
		int result = 0;
		for(String rsvId : rsvIds) {
			int updated = reservationMapper.updateReservationStatus(rsvId, rsvType);
			if(updated == 0) {
				throw new RuntimeException("예약 ID" + rsvId + "를 업데이트하지 못했습니다.");
			}
			result += updated;
		}
		return result;
	}

	@Override
	public List<ReservationDTO> selectReservationByPaymentId(String pymId) {
		return reservationMapper.selectReservationByPaymentId(pymId);
	}
}
