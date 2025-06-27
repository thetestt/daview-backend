package com.daview.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daview.dto.PaymentDTO;
import com.daview.dto.PaymentReservationMapDTO;
import com.daview.dto.PaymentWithReservationsDTO;
import com.daview.service.PaymentReservationService;
import com.daview.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

	private final PaymentService paymentService;

	private final PaymentReservationService paymentReservationService;

	@PostMapping
	public ResponseEntity<String> createPayment(@RequestBody PaymentDTO payment) {
		int result = paymentService.insertPayment(payment);
		if (result >= 1) {
			return ResponseEntity.ok("결제 정보 등록 성공");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 정보 등록 실패");
		}
	}

	@GetMapping("/{pymId}")
	public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable String pymId) {
		PaymentDTO payment = paymentService.selectPaymentById(pymId);
		if (payment != null) {
			return ResponseEntity.ok(payment);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}

	@PostMapping("/map")
	public ResponseEntity<String> mapReservationsToPayment(@RequestBody List<PaymentReservationMapDTO> list) {
		int successCount = 0;

		for (PaymentReservationMapDTO dto : list) {
			successCount += paymentReservationService.insertMap(dto);
		}
		return ResponseEntity.ok(successCount + "건 매핑 완료");
	}

	@GetMapping("/payments/member/{memberId}")
	public ResponseEntity<List<PaymentWithReservationsDTO>> getPaymentsByMemberId(@PathVariable Long memberId){
		List<PaymentWithReservationsDTO> payments = paymentService.selectPaymentWithReservationsByMemberId(memberId);
		if(payments == null || payments.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(payments);
	}
	
	@GetMapping("/prod/{memberId}")
	public ResponseEntity<List<String>> getProdNmList(@PathVariable Long memberId){
		List<String> prodNm = paymentService.getProdNmList(memberId);
		return ResponseEntity.ok(prodNm);
	}
}
