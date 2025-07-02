package com.daview.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.daview.dto.ChangePasswordRequest;
import com.daview.dto.LoginRequest;
import com.daview.dto.SignupRequest;
import com.daview.dto.SmsRequest;
import com.daview.dto.SmsVerifyRequest;
import com.daview.dto.User;
import com.daview.mapper.UserMapper;
import com.daview.service.AuthService;
import com.daview.service.SmsService;
import com.daview.util.EmailUtil;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    @Autowired
    private SmsService smsService;

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
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            authService.checkPhoneBlockedForSignup(request.getPhone());
            boolean result = authService.signup(request);
            return ResponseEntity.ok(result);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
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
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal User user,
                                            @RequestBody ChangePasswordRequest request) {
        String newPassword = request.getNewPassword();
        String username = user.getUsername();

        User dbUser = userMapper.findByUsername(username);

        if (passwordEncoder.matches(newPassword, dbUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("기존 비밀번호와 동일합니다. 다른 비밀번호를 입력하세요.");
        }

        String encoded = passwordEncoder.encode(newPassword);
        userMapper.updatePassword(username, encoded);

        return ResponseEntity.ok("비밀번호 변경 완료");
    }

    @PostMapping("/email/send")
    public ResponseEntity<String> sendEmailCode(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");

        String username = userMapper.findUsernameByEmail(name, email);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("일치하는 회원 정보가 없습니다.");
        }

        try {
            String code = String.valueOf((int) ((Math.random() * 900000) + 100000));
            String subject = "[다뷰] 이메일 인증번호";
            String text = "인증번호: " + code;

            emailUtil.sendEmail(email, subject, text);
            session.setAttribute("emailCode", code);

            return ResponseEntity.ok("이메일 발송 완료");
        } catch (Exception e) {
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

        if (savedCode == null || !savedCode.equals(code)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증번호가 올바르지 않습니다.");
        }

        String username = userMapper.findUsernameByEmail(name, email);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 정보로 가입된 아이디가 없습니다.");
        }

        return ResponseEntity.ok(username);
    }

    @PostMapping("/signup/send-sms-code")
    public ResponseEntity<?> sendSignupSmsCode(@RequestBody SmsRequest request) {
        smsService.sendSignupSmsCode(request.getPhone());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup/verify-sms-code")
    public ResponseEntity<?> verifySignupSmsCode(@RequestBody SmsVerifyRequest request) {
        boolean verified = smsService.verifySignupSmsCode(request.getPhone(), request.getCode());
        if (verified) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다.");
        }
    }
}
