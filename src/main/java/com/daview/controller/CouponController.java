package com.daview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daview.dto.UserCoupon;
import com.daview.service.CouponService;

@RestController
@RequestMapping("/coupon")
public class CouponController {
	@Autowired
    private CouponService couponService;

    @GetMapping("/my")
    public List<UserCoupon> getMyCoupons(@RequestAttribute("memberId") Long memberId) {
        return couponService.getMyCoupons(memberId);
    }
    
    @PostMapping("/use")
	public ResponseEntity<String> useCoupon(@RequestAttribute("memberId") Long memberId,
			@RequestParam("couponId") Long couponId) {
		boolean success = couponService.useCoupon(memberId, couponId);

		if (success) {
			return ResponseEntity.ok("쿠폰이 성공적으로 사용되었습니다.");
		} else {
			return ResponseEntity.badRequest().body("쿠폰 사용에 실패했습니다. 이미 사용했거나 존재하지 않는 쿠폰입니다.");
		}
	}
}
