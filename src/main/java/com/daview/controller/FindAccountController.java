package com.daview.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
        return findAccountService.sendSmsCode(name, phone);
    }

    // 이메일 인증번호 전송
    @PostMapping("/send-email-code")
    public String sendEmailCode(@RequestParam String name, @RequestParam String email) {
        return findAccountService.sendEmailCode(name, email);
    }
}
