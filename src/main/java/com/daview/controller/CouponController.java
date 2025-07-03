package com.daview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.daview.dto.User;
import com.daview.dto.UserCoupon;
import com.daview.service.CouponService;

@RestController
@RequestMapping("api/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/my")
    public List<UserCoupon> getMyCoupons(@AuthenticationPrincipal User user) {
        return couponService.getMyCoupons(user.getMemberId());
    }

    @PostMapping("/use")
    public ResponseEntity<String> useCoupon(@AuthenticationPrincipal User user,
                                            @RequestParam("couponId") Long couponId) {
        boolean success = couponService.useCoupon(user.getMemberId(), couponId);

        if (success) {
            return ResponseEntity.ok("쿠폰이 성공적으로 사용되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("쿠폰 사용에 실패했습니다. 이미 사용했거나 존재하지 않는 쿠폰입니다.");
        }
    }
}
