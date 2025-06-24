package com.daview.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daview.dto.ChangePasswordRequest;
import com.daview.dto.LoginRequest;
import com.daview.dto.SignupRequest;
import com.daview.dto.User;
import com.daview.mapper.UserMapper;
import com.daview.service.AuthService;
import com.daview.util.EmailUtil;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

public class AuthController {

	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private HttpSession session;

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
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("기존 비밀번호와 동일합니다. 다른 비밀번호를 입력하세요.");
		}

		// 비밀번호 업데이트
		String encoded = passwordEncoder.encode(newPassword);
		userMapper.updatePassword(username, encoded);

		return ResponseEntity.ok("비밀번호 변경 완료");
	}

	@PostMapping("/email/send")
	public ResponseEntity<String> sendEmailCode(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		try {
			// 6자리 숫자 인증번호 생성
			String code = String.valueOf((int) ((Math.random() * 900000) + 100000));

			// 이메일 내용 구성
			String subject = "[다뷰] 이메일 인증번호";
			String text = "인증번호: " + code;

			System.out.println(">> 받은 이메일: " + email);
			System.out.println(">> 생성된 인증번호: " + code);
			System.out.println("보낸 인증번호: " + code);

			// 이메일 전송
			emailUtil.sendEmail(email, subject, text);

			// 세션에 인증번호 저장
			session.setAttribute("emailCode", code);

			return ResponseEntity.ok("이메일 발송 완료");
		} catch (Exception e) {
			System.out.println(">> 이메일 전송 에러: " + e.getMessage());
			e.printStackTrace(); // 콘솔에 전체 에러 로그
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 전송 실패");
		}
	}

	@PostMapping("/email/verify")
	public ResponseEntity<Boolean> verifyEmailCode(@RequestBody Map<String, String> request) {
		String inputCode = request.get("code");
		String sessionCode = (String) session.getAttribute("emailCode");

		boolean isMatch = inputCode != null && inputCode.equals(sessionCode);

		return ResponseEntity.ok(isMatch);
	}
	
	@PostMapping("/find-id")
	public ResponseEntity<?> findUsernameByEmail(@RequestBody Map<String, String> request, HttpSession session) {
	    String name = request.get("name");
	    String email = request.get("email");
	    String code = request.get("code"); 
	    String savedCode = (String) session.getAttribute("emailCode");
	    
	    System.out.println("세션 ID: " + session.getId());
	    System.out.println("받은 code: " + code);
	    System.out.println("세션 저장 code: " + savedCode);

	    if (savedCode == null || !savedCode.equals(code)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증번호가 올바르지 않습니다.");
	    }

	    String username = userMapper.findUsernameByEmail(name, email);
	    System.out.println("입력값 name: " + name + ", email: " + email);
	    System.out.println("DB에서 찾은 username: " + username);
	    if (username == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 정보로 가입된 아이디가 없습니다.");
	    }

	    return ResponseEntity.ok(username); 
	}



}
