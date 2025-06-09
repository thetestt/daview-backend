package com.daview.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.daview.dto.User;
import com.daview.dto.LoginRequest;
import com.daview.dto.SignupRequest;
import com.daview.mapper.UserMapper;
import com.daview.util.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean signup(SignupRequest request) {
        if (userMapper.findByUsername(request.getUsername()) != null) {
            return false;
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // 비번 암호화
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());

        userMapper.insertUser(user);
        return true;
    }

    public Map<String, Object> login(LoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        User user = userMapper.findByUsername(request.getUsername());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            response.put("success", false);
            return response;
        }

        String token = JwtUtil.generateToken(user.getUsername());

        response.put("success", true);
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("role", user.getRole());

        return response;
    }
}
