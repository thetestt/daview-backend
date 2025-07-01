package com.daview.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daview.dto.CancelRequestDTO;
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
	public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO payment) {
		payment.setPymId(UUID.randomUUID().toString());
		int result = paymentService.insertPayment(payment);

		if (result >= 1) {
			return ResponseEntity.ok(payment);
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
	    try {
	        int successCount = paymentReservationService.insertMap(list);
	        return ResponseEntity.ok(successCount + "건 매핑 완료");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body("매핑 중 오류: " + e.getMessage());
	    }
	}
	
	@GetMapping("/payments/member/{memberId}")
	public ResponseEntity<List<PaymentWithReservationsDTO>> getPaymentsByMemberId(@PathVariable Long memberId) {
		List<PaymentWithReservationsDTO> payments = paymentService.selectPaymentWithReservationsByMemberId(memberId);
		if (payments == null || payments.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(payments);
	}

	@GetMapping("/prod/{memberId}")
	public ResponseEntity<List<String>> getProdNmList(@PathVariable Long memberId) {
		List<String> prodNm = paymentService.getProdNmList(memberId);
		return ResponseEntity.ok(prodNm);
	}
	
	@PostMapping("/cancel")
	public ResponseEntity<String> cancelPayment(@RequestBody CancelRequestDTO cancel) {
	    String impUid = cancel.getImpUid();
	    String refundReason = cancel.getRefundReason();

	    try {
	        boolean result = paymentService.cancelPayment(impUid, refundReason);
	        if (result) {
	            return ResponseEntity.ok("환불 성공!");
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("환불 실패: 이미 취소되었거나 유효하지 않음");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("환불 중 오류: " + e.getMessage());
	    }
	}
	
	@GetMapping("/refunds/{memberId}")
	public ResponseEntity<List<PaymentWithReservationsDTO>> getRefundedPayments(@PathVariable Long memberId) {
	    List<PaymentWithReservationsDTO> refunds = paymentService.getRefundedPaymentsByMemberId(memberId);
	    if (refunds == null || refunds.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	    return ResponseEntity.ok(refunds);
	}
}
