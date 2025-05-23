package com.daview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.daview.dto.CaregiverDTO;
import com.daview.service.CaregiverService;
import com.daview.security.UserPrincipal; // JWT 인증 후 사용자 정보 담는 클래스

@RestController
@RequestMapping("/api/caregivers")
@CrossOrigin(origins = "http://localhost:3000")
public class CaregiverController {

    @Autowired
    private CaregiverService caregiverService;

    // ✅ 전체 요양사 목록 반환
    @GetMapping("/")
    public List<CaregiverDTO> getAllCaregivers() {
        return caregiverService.getAllCaregivers();
    }

    // ✅ 특정 ID로 요양사 조회
    @GetMapping("/{id}")
    public CaregiverDTO getCaregiverById(@PathVariable("id") String caregiverId) {
        return caregiverService.getCaregiverById(caregiverId);
    }

    // ✅ 로그인한 요양사 본인 정보 반환
    @GetMapping("/me")
    public ResponseEntity<CaregiverDTO> getMyCaregiverProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long memberId = userPrincipal.getId(); // 로그인한 사용자의 member_id
        CaregiverDTO dto = caregiverService.getCaregiverByMemberId(memberId);
        return ResponseEntity.ok(dto);
    }
}
