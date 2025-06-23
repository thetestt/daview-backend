package com.daview.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.daview.dto.ChangePasswordRequest;
import com.daview.dto.LoginRequest;
import com.daview.dto.SignupRequest;
import com.daview.service.AuthService;
import com.daview.dto.User;
import com.daview.mapper.UserMapper;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

public class AuthController {
	
	@Autowired
	private UserMapper userMapper;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	public boolean signup(@RequestBody SignupRequest request) {
		return authService.signup(request);
	}

	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody LoginRequest request) {
		return authService.login(request);
	}

	@GetMapping("/check-username")
	public ResponseEntity<Boolean> checkUsername(@RequestParam String username) {
		boolean exists = authService.checkUsernameDuplicate(username);
		return ResponseEntity.ok(exists);
	}

	@PostMapping("/find-username/phone")
	public ResponseEntity<?> findUsernameByPhone(@RequestBody Map<String, String> payload) {
		String name = payload.get("name");
		String phone = payload.get("phone");
		String username = authService.findUsernameByPhone(name, phone);

		if (username != null) {
			return ResponseEntity.ok().body(username);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("일치하는 회원이 없습니다.");
		}

	}
	
	@PostMapping("/account/change-password")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
	    String username = request.getUsername();
	    String newPassword = request.getNewPassword();

	    User user = userMapper.findByUsername(username);
	    
	    if (passwordEncoder.matches(newPassword, user.getPassword())) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	            .body("기존 비밀번호와 동일합니다. 다른 비밀번호를 입력하세요.");
	    }

	    // 비밀번호 업데이트
	    String encoded = passwordEncoder.encode(newPassword);
	    userMapper.updatePassword(username, encoded);

	    return ResponseEntity.ok("비밀번호 변경 완료");
	}





}
