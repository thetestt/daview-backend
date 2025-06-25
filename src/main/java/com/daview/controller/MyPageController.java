package com.daview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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


}
