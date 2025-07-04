package com.daview.service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.daview.dto.LoginRequest;
import com.daview.dto.SignupRequest;
import com.daview.dto.User;
import com.daview.mapper.UserMapper;
import com.daview.util.JwtUtil;

@Service
public class AuthService {
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CouponService couponService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean signup(SignupRequest request) {
		if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
	        throw new IllegalArgumentException("아이디는 필수입니다.");
	    }
	    if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
	        throw new IllegalArgumentException("비밀번호는 필수입니다.");
	    }
	    if (request.getName() == null || request.getName().trim().isEmpty()) {
	        throw new IllegalArgumentException("이름은 필수입니다.");
	    }
	    if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
	        throw new IllegalArgumentException("이메일은 필수입니다.");
	    }
	    if (request.getGender() == null || request.getGender().trim().isEmpty()) {
	        throw new IllegalArgumentException("성별은 필수입니다.");
	    }
	    if (request.getRole() == null || request.getRole().trim().isEmpty()) {
	        throw new IllegalArgumentException("회원 유형은 필수입니다.");
	    }
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
		user.setGender(request.getGender());
		user.setAgreeSms(request.isAgreeSms());
		user.setAgreeEmail(request.isAgreeEmail());
		user.setAgreePush(request.isAgreePush());




		userMapper.insertUser(user);

		User savedUser = userMapper.findByUsername(user.getUsername());

		couponService.issueWelcomeCoupon(savedUser.getMemberId());

		return true;
	}

	public Map<String, Object> login(LoginRequest request) {
	    Map<String, Object> response = new HashMap<>();

	    User user = userMapper.findByUsername(request.getUsername());

	    // user가 null인 경우 바로 에러 반환
	    if (user == null) {
	        response.put("success", false);
	        response.put("message", "존재하지 않는 아이디입니다.");
	        return response;
	    }

	    // 비밀번호 일치 여부 확인
	    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	        response.put("success", false);
	        response.put("message", "비밀번호가 일치하지 않습니다.");
	        return response;
	    }

	    // 토큰 발급
	    String token = jwtUtil.generateToken(user.getUsername(), user.getRole(), user.getMemberId());

	    response.put("success", true);
	    response.put("token", token);
	    response.put("username", user.getUsername());
	    response.put("role", user.getRole());

	    return response;
	}


	public boolean checkUsernameDuplicate(String username) {
		return userMapper.countByUsername(username) > 0;
	}

	public String findUsernameByPhone(String name, String phone) {
		return userMapper.findUsernameByPhone(name, phone);
	}

	private String generateTempPassword() {
		int length = 10;
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		}
		return sb.toString();
	}
	
	
	public void checkPhoneBlockedForSignup(String phone) {
	    User user = userMapper.findWithdrawnUserWithin14Days(phone);
	    if (user != null) {
	        throw new IllegalStateException("해당 번호는 탈퇴 후 14일 이내 재가입이 불가합니다.");
	    }
	}
	
	// 회원가입 전에 모든 조건 검사
    public void validateSignup(SignupRequest request) {
        checkPhoneBlockedForSignup(request.getPhone());

    }

    // 실제 회원 가입 처리
    public void registerUser(SignupRequest request) {
    	// 회원 DB 저장 로직
    }
}


