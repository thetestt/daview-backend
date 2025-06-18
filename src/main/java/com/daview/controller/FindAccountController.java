package com.daview.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daview.dto.VerificationRequest;
import com.daview.service.FindAccountService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/account")
public class FindAccountController {

	@Autowired
	private FindAccountService findAccountService;

	// 전화번호 인증번호 전송
	@PostMapping("/send-sms-code")
	public String sendSmsCode(@RequestBody Map<String, String> request) {
	    String name = request.get("name");
	    String phone = request.get("phone");
	    return findAccountService.sendSmsCode(name, phone);
	}
	// 인증번호 인증하기
	@PostMapping("/verify-code")
	public String verifyCode(@RequestBody VerificationRequest request) {
	    return findAccountService.verifyCode(request.getPhone(), request.getCode());
	}

	@PostMapping("/find-id/phone")
	public ResponseEntity<String> findUsernameByPhone(@RequestParam String name, @RequestParam String phone) {
		String username = findAccountService.findUsernameByPhone(name, phone);
		if (username != null) {
			return ResponseEntity.ok("회원님의 아이디는 " + username + " 입니다.");
		} else {
			return ResponseEntity.status(404).body("일치하는 회원 정보가 없습니다.");
		}
	}

}
