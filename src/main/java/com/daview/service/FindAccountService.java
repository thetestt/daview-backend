package com.daview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindAccountService {
	@Autowired
    private SmsService smsService;

    public String sendSmsCode(String name, String phone) {
        String result = smsService.sendSms(phone);
        if (result != null) {
            // 인증번호 저장 로직은 나중에 구현
            return "문자 전송 완료 (인증번호: " + result + ")";
        } else {
            return "문자 전송 실패";
        }
    }
    
    public String sendEmailCode(String name, String email) {
        // 여기 나중에 MailService 붙여서 인증번호 보내게 구현할 예정
        System.out.println("[이메일 인증] 이름: " + name + ", 이메일: " + email);
        return "이메일 인증번호 전송 완료 (가상)";
    }
}
