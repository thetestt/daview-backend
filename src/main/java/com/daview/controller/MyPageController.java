package com.daview.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daview.dto.ReviewDTO;
import com.daview.dto.User;
import com.daview.mapper.UserMapper;
import com.daview.service.MyPageService;
import com.daview.service.ReviewService;


@RestController
@RequestMapping("/api/mypage")
public class MyPageController {
	
	@Autowired
	private UserMapper userMapper;


	@Autowired
	private MyPageService myPageService;
	
	@Autowired
	private ReviewService reviewService;


	// 로그인한 사용자 정보 반환 (마이페이지 프로필용)
	@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'COMPANY', 'CAREGIVER')")
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile(Authentication authentication) {
	    if (authentication == null || !authentication.isAuthenticated()) {
	        return ResponseEntity.status(401).body("인증되지 않은 사용자입니다");
	    }
	    
	    System.out.println(">>> authentication: " + authentication);
	    System.out.println(">>> principal: " + authentication.getPrincipal());


	    User user = (User) authentication.getPrincipal();
	    System.out.println("[컨트롤러] 유저 username: " + user.getUsername());
	    System.out.println("[컨트롤러] 유저 memberId: " + user.getMemberId());

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
	
	@PostMapping("/account/refund")
	public ResponseEntity<?> saveRefundAccount(@RequestBody Map<String, String> req) {
	    String username = req.get("username");
	    String bankName = req.get("bankName");
	    String accountNumber = req.get("accountNumber");
	    myPageService.saveRefundAccount(username, bankName, accountNumber);
	    return ResponseEntity.ok().body("saved");
	}

	@GetMapping("/account/refund")
	public ResponseEntity<?> getRefundAccount(@RequestParam String username) {
	    User user = myPageService.getRefundAccount(username);
	    return ResponseEntity.ok().body(user);
	}

	@DeleteMapping("/account/refund")
	public ResponseEntity<?> deleteRefundAccount(@RequestParam String username) {
	    myPageService.deleteRefundAccount(username);
	    return ResponseEntity.ok().body("deleted");
	}

	@PatchMapping("/account/marketing")
	public ResponseEntity<?> updateMarketingItem(@RequestBody Map<String, Object> req) {
	    String username = (String) req.get("username");
	    String type = (String) req.get("type");
	    boolean value = (Boolean) req.get("value");

	    myPageService.updateMarketingItem(username, type, value);
	    return ResponseEntity.ok().body("updated");
	}
	
	@PostMapping("/account/withdraw")
	public ResponseEntity<?> withdraw(@RequestBody Map<String, String> req) {
	    String username = req.get("username");
	    String reason = req.get("reason");

	    myPageService.withdrawUser(username, reason);
	    return ResponseEntity.ok("탈퇴 처리 완료");
	}
	
	@PatchMapping("/account/username")
	public ResponseEntity<?> changeUsername(
	    @RequestBody Map<String, String> request,
	    @AuthenticationPrincipal User user // ✅ 인증된 사용자 정보
	) {
	    String newUsername = request.get("newUsername");
	    String currentUsername = user.getUsername();

	    if (newUsername == null || newUsername.isBlank()) {
	        return ResponseEntity.badRequest().body("아이디는 비어있을 수 없습니다.");
	    }

	    if (newUsername.equals(currentUsername)) {
	        return ResponseEntity.badRequest().body("현재 사용 중인 아이디입니다.");
	    }

	    boolean exists = userMapper.existsByUsername(newUsername); 
	    if (exists) {
	        return ResponseEntity.badRequest().body("이미 사용 중인 아이디입니다.");
	    }

	    userMapper.updateUsername(currentUsername, newUsername);

	    return ResponseEntity.ok("아이디 변경 완료");
	}







}
