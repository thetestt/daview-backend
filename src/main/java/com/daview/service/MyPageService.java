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
    
    //마이페이지 - 비밀번호 변경
    @Autowired
    private PasswordEncoder passwordEncoder; // BCryptPasswordEncoder

    public boolean checkPassword(String username, String inputPassword) {
        String hashedPassword = userMapper.getPasswordByUsername(username);
        if (hashedPassword == null) return false;
        
        System.out.println("[DEBUG] username: " + username);
        System.out.println("[DEBUG] 입력 비번: " + inputPassword);
        System.out.println("[DEBUG] DB 비번: " + hashedPassword);


        return passwordEncoder.matches(inputPassword, hashedPassword);
    }
    
    public void saveRefundAccount(String username, String bankName, String accountNumber) {
    	System.out.println("저장할 username: " + username);
    	System.out.println("저장할 bankName: " + bankName);
    	System.out.println("저장할 accountNumber: " + accountNumber);
        userMapper.updateRefundAccount(username, bankName, accountNumber);
    }

    public User getRefundAccount(String username) {
        return userMapper.getRefundAccount(username);
    }

    public void deleteRefundAccount(String username) {
        userMapper.deleteRefundAccount(username);
    }

    public void updateMarketingConsent(String username, boolean marketingAgreed) {
        userMapper.updateMarketingConsent(username, marketingAgreed);
    }
    
    public void updateMarketingItem(String username, String type, boolean value) {
        switch (type) {
            case "sms":
                userMapper.updateAgreeSms(username, value);
                break;
            case "email":
                userMapper.updateAgreeEmail(username, value);
                break;
            case "push":
                userMapper.updateAgreePush(username, value);
                break;
            default:
                throw new IllegalArgumentException("Invalid marketing type");
        }
    }
    
    public void withdrawUser(String username, String reason) {
        userMapper.withdrawUser(username, reason);
    }


}
