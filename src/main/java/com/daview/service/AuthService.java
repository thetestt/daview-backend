package com.daview.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.daview.dto.LoginRequest;
import com.daview.dto.SignupRequest;
import com.daview.util.JwtUtil;

@Service
public class AuthService {

    public boolean signup(SignupRequest request) {
        System.out.println("회원가입 요청: " + request.getUsername());
        return true; // 일단 성공했다고 생각
    }

    public Map<String, Object> login(LoginRequest request) {
        Map<String, Object> result = new HashMap<>();

        if (request.getUsername().equals("test") && request.getPassword().equals("ch1234567")) {
            String token = JwtUtil.generateToken(request.getUsername());
            result.put("success", true);
            result.put("token", token);
            result.put("username", request.getUsername());
        } else {
            result.put("success", false);
        }

        System.out.println("입력한 아이디: " + request.getUsername());
        System.out.println("입력한 비밀번호: " + request.getPassword());

        return result;
    }
}
