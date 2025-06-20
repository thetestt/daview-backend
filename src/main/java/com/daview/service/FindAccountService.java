package com.daview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.daview.mapper.UserMapper;
import com.daview.util.VerificationStorage;

@Service
public class FindAccountService {
	@Autowired
	private SmsService smsService;

	@Autowired
	private VerificationStorage verificationStorage;

	@Autowired
	private UserMapper userMapper;

	public String sendSmsCode(String name, String phone) {
		String result = smsService.sendSms(phone);
		if (result != null) {
			verificationStorage.saveCode(phone, result);
			return "문자 전송 완료";
		} else {
			return "문자 전송 실패";
		}
	}

	// 인증번호 검증 로직
	public boolean verifyCode(String phone, String inputCode) {
		boolean success = verificationStorage.verify(phone, inputCode);
		if (success) {
			verificationStorage.removeCode(phone);
		}
		return success;
	}

	public String findUsernameByPhone(String name, String phone) {

		System.out.println("찾으려는 이름: " + name);
		System.out.println("찾으려는 전화번호: " + phone);

		String username = userMapper.findUsernameByPhone(name, phone);
		System.out.println("조회 결과 아이디: " + username);

		return username;
	}
	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean updatePassword(String username, String rawPassword) {
	    String encodedPassword = passwordEncoder.encode(rawPassword);
	    int updated = userMapper.updatePassword(username, encodedPassword);
	    return updated > 0;
	}

}
