package com.daview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daview.service.FindAccountService;

@RestController
@RequestMapping("/api/account")
public class FindAccountController {

	@Autowired
	private FindAccountService findAccountService;

	// 전화번호 인증번호 전송
	@PostMapping("/send-sms-code")
	public String sendSmsCode(@RequestParam String name, @RequestParam String phone) {
		System.out.println("요청 이름: " + name);
		System.out.println("요청 전화번호: " + phone);
		
		return findAccountService.sendSmsCode(name, phone);
	}

	@PostMapping("/verify-code")
	public String verifyCode(@RequestParam String phone, @RequestParam String code) {
		return findAccountService.verifyCode(phone, code);
	}

	@PostMapping("/find-id/phone")
	public ResponseEntity<String> findUsernameByPhone(@RequestParam String name, @RequestParam String phone) {
		String username = findAccountService.findUsernameByPhone(name, phone);
		if (username != null) {
			return ResponseEntity.ok("회원님의 아이디는: " + username);
		} else {
			return ResponseEntity.status(404).body("일치하는 회원 정보가 없습니다.");
		}
	}

}
