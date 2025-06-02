package com.daview.service;

import com.daview.domain.user.User;
import com.daview.dto.LoginRequest;
import com.daview.dto.SignupRequest;
import com.daview.repository.UserRepository;
import com.daview.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public boolean signup(SignupRequest request) {
        // username 중복 확인
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return false;
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        userRepository.save(user);
        return true;
    }

    public Map<String, Object> login(LoginRequest request) {
        Map<String, Object> result = new HashMap<>();

        userRepository.findByUsername(request.getUsername()).ifPresentOrElse(user -> {
            if (user.getPassword().equals(request.getPassword())) {
                String token = JwtUtil.generateToken(user.getUsername());
                result.put("success", true);
                result.put("token", token);
                result.put("username", user.getUsername());
            } else {
                result.put("success", false);
            }
        }, () -> result.put("success", false));

        return result;
    }
}
