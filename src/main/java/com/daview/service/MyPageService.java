package com.daview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.daview.dto.User;
import com.daview.mapper.UserMapper;

@Service
public class MyPageService {
	@Autowired
    private UserMapper userMapper;

    public User getUserProfile(Long memberId) {
        return userMapper.findByMemberId(memberId);
    }
    
    @Autowired
    private PasswordEncoder passwordEncoder; // BCryptPasswordEncoder 등

    public boolean checkPassword(String username, String inputPassword) {
        String hashedPassword = userMapper.getPasswordByUsername(username);
        if (hashedPassword == null) return false;

        return passwordEncoder.matches(inputPassword, hashedPassword);
    }
}
