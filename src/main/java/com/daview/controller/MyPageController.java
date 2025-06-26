package com.daview.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daview.dto.User;
import com.daview.service.MyPageService;

@RestController
@RequestMapping("/api/mypage")
public class MyPageController {

	@Autowired
	private MyPageService myPageService;

	// 로그인한 사용자 정보 반환 (마이페이지 프로필용)
	@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'COMPANY', 'CAREGIVER')")
	@GetMapping("/profile")
	public ResponseEntity<User> getProfile(Authentication authentication) {
		User user = (User) authentication.getPrincipal(); // JWT 필터에서 넣은 사용자 정보 꺼냄
		Long memberId = user.getMemberId();

		User profile = myPageService.getUserProfile(memberId);
		return ResponseEntity.ok(profile);
	}

	@PostMapping("/check-password")
	public ResponseEntity<Map<String, Boolean>> checkPassword(@RequestBody Map<String, String> request) {
		String username = request.get("username");
		String inputPassword = request.get("password");

		boolean isMatch = myPageService.checkPassword(username, inputPassword);

		Map<String, Boolean> response = new HashMap<>();
		response.put("success", isMatch);
		return ResponseEntity.ok(response);
	}

}
