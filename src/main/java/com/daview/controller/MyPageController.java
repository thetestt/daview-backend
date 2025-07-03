package com.daview.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	@PreAuthorize("hasAnyRole('USER', 'ADMIN', 'COMPANY', 'CAREGIVER')")
	@GetMapping("/profile")
	public ResponseEntity<User> getProfile(@AuthenticationPrincipal User user) {
		return ResponseEntity.ok(user);
	}

	@PostMapping("/check-password")
	public ResponseEntity<Map<String, Boolean>> checkPassword(@AuthenticationPrincipal User user,
			@RequestBody Map<String, String> request) {

		String inputPassword = request.get("password");
		String username = user.getUsername(); // ✅ 이제 오류 안 남

		boolean isMatch = myPageService.checkPassword(username, inputPassword);

		Map<String, Boolean> response = new HashMap<>();
		response.put("success", isMatch);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/account/refund")
	public ResponseEntity<?> saveRefundAccount(@AuthenticationPrincipal User user,
			@RequestBody Map<String, String> req) {
		String bankName = req.get("bankName");
		String accountNumber = req.get("accountNumber");
		myPageService.saveRefundAccount(user.getUsername(), bankName, accountNumber);
		return ResponseEntity.ok().body("saved");
	}

	@GetMapping("/account/refund")
	public ResponseEntity<?> getRefundAccount(@AuthenticationPrincipal User user) {
	    Map<String, Object> refundInfo = new HashMap<>();
	    refundInfo.put("bankName", user.getBankName());
	    refundInfo.put("accountNumber", user.getAccountNumber());
	    return ResponseEntity.ok(refundInfo);
	}


	@DeleteMapping("/account/refund")
	public ResponseEntity<?> deleteRefundAccount(@AuthenticationPrincipal User user) {
		myPageService.deleteRefundAccount(user.getUsername());
		return ResponseEntity.ok().body("deleted");
	}

	@PatchMapping("/account/marketing")
	public ResponseEntity<?> updateMarketingItem(@AuthenticationPrincipal User user,
			@RequestBody Map<String, Object> req) {
		String type = (String) req.get("type");
		boolean value = (Boolean) req.get("value");

		myPageService.updateMarketingItem(user.getUsername(), type, value);
		return ResponseEntity.ok().body("updated");
	}

	@PostMapping("/account/withdraw")
	public ResponseEntity<?> withdraw(@AuthenticationPrincipal User user, @RequestBody Map<String, String> req) {
		String reason = req.get("reason");
		myPageService.withdrawUser(user.getUsername(), reason);
		return ResponseEntity.ok("탈퇴 처리 완료");
	}

	@PatchMapping("/account/username")
	public ResponseEntity<?> changeUsername(@RequestBody Map<String, String> request,
			@AuthenticationPrincipal User user) {
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

	@PostMapping("/profile-image")
	public ResponseEntity<?> uploadProfileImage(@AuthenticationPrincipal User user,
	                                            @RequestPart("file") MultipartFile file) {
	    if (file.isEmpty()) {
	        return ResponseEntity.badRequest().body("파일이 없습니다.");
	    }

	    try {
	        myPageService.saveProfileImage(user.getMemberId(), file); // ✅ 이름 정리
	        return ResponseEntity.ok("이미지 업로드 성공");
	    } catch (IOException e) {
	        return ResponseEntity.status(500).body("이미지 업로드 실패");
	    }
	}

	
	@GetMapping("/profile-image")
	public ResponseEntity<String> getProfileImage(@AuthenticationPrincipal User user) {

	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
	    }


	    String imagePath = myPageService.getProfileImagePath(user.getMemberId());
	    if (imagePath == null || imagePath.trim().isEmpty()) {
	        imagePath = "/uploads/profile/default-profile.png";
	    }

	    return ResponseEntity.ok(imagePath);
	}
	
	@PostMapping("/profile-image/default")
	public ResponseEntity<?> resetToDefault(@AuthenticationPrincipal User user) {
	    try {
	        myPageService.resetProfileImageToDefault(user.getMemberId());
	        return ResponseEntity.ok("기본 이미지로 변경됨");
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("기본 이미지 설정 실패");
	    }
	}


	
}
