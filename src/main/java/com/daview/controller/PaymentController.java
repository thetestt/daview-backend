package com.daview.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daview.dto.PaymentDTO;
import com.daview.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
	
	private final PaymentService paymentService;
	
	@PostMapping
	public ResponseEntity<String> createPayment(@RequestBody PaymentDTO payment){
		int result = paymentService.insertPayment(payment);
		if(result >= 1) {
			return ResponseEntity.ok("결제 정보 등록 성공");
		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 정보 등록 실패");
		}
	}
	
	@GetMapping("/{pymId}")
	public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable String pymId){
		PaymentDTO payment = paymentService.selectPaymentById(pymId);
		if(payment != null) {
			return ResponseEntity.ok(payment);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		
	}
}
