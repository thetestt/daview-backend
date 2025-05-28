package com.daview.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.daview.dto.LoginRequest;
import com.daview.util.JwtUtil;

@Service
public class AuthService {
    public Map<String, Object> login(LoginRequest request) {
        Map<String, Object> result = new HashMap<>();

        // 테스트용 로그인 검사
        if (request.getUsername().equals("test") && request.getPassword().equals("1234")) {
            String token = JwtUtil.generateToken(request.getUsername());
            result.put("success", true);
            result.put("token", token);

        } else {
            result.put("success", false);
        }

        System.out.println("입력한 아이디: " + request.getUsername());
        System.out.println("입력한 비밀번호: " + request.getPassword());

        return result;
    }
}
