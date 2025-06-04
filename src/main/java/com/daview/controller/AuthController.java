package com.daview.controller;

import com.daview.dto.SignupRequest;
import com.daview.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public boolean signup(@RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody SignupRequest request) {
        return authService.login(request);
    }
}
