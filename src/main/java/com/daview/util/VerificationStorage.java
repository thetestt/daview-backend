package com.daview.util;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class VerificationStorage {
	private final ConcurrentHashMap<String, String> phoneCodeMap = new ConcurrentHashMap<>();

    // 인증번호 저장
    public void saveCode(String phone, String code) {
        phoneCodeMap.put(phone, code);
    }

    // 인증번호 가져오기
    public String getCode(String phone) {
        return phoneCodeMap.get(phone);
    }

    // 인증번호 삭제 (성공 or 만료 시)
    public void removeCode(String phone) {
        phoneCodeMap.remove(phone);
    }

    // 인증번호 검증
    public boolean verify(String phone, String inputCode) {
        String saved = phoneCodeMap.get(phone);
        return saved != null && saved.equals(inputCode);
    }
}
