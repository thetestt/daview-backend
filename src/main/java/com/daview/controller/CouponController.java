package com.daview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
