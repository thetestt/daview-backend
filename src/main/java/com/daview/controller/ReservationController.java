package com.daview.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daview.dto.ReservationDTO;
import com.daview.service.ReservationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;

	@GetMapping("/member/{memberId}")
	public ResponseEntity<?> selectReservationById(@PathVariable int memberId) {
		List<ReservationDTO> reservation = reservationService.selectReservationById(memberId);

		if (!reservation.isEmpty()) {
			return ResponseEntity.ok(reservation);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("예약을 찾을 수 없습니다.");
		}
	}

	@PostMapping
	public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservation) {
		int result = reservationService.insertReservation(reservation);

		if (result == 1) {
			List<ReservationDTO> savedList = reservationService.selectReservationById(reservation.getMemberId());

			if (!savedList.isEmpty()) {
				return ResponseEntity.ok(savedList.get(0));
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/{rsvId}/delete")
	public ResponseEntity<?> deleteReservation(@PathVariable String rsvId) {
		int deleted = reservationService.deleteReservation(rsvId);

		if (deleted == 1) {
			return ResponseEntity.ok().body("예약 삭제 완료");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("예약을 찾을 수 없습니다.");
		}
	}

	@PutMapping("/deleteAll")
	public ResponseEntity<?> deleteAllReservation() {
		int count = reservationService.deleteAllReservation();
		return ResponseEntity.ok().body(count + "개의 모든 예약이 삭제되었습니다.");
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateReservationCount(@RequestBody List<ReservationDTO> updates){
		try {
			reservationService.updateReservationCount(updates);
			return ResponseEntity.ok("예약 수량이 수정되었습니다.");
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예약 수량 수정 중 오류가 발생했습니다.");
		}
	}
}
