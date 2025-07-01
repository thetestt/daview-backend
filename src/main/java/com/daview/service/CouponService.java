package com.daview.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daview.dto.CouponDTO;
import com.daview.dto.UserCoupon;
import com.daview.mapper.UserMapper;

@Service
public class CouponService {
	@Autowired
    private UserMapper userMapper;

    // 회원가입 시 쿠폰 발급
    public void issueWelcomeCoupon(Long memberId) {
        CouponDTO welcomeCoupon = userMapper.findCouponByCode("WELCOME");

        if (welcomeCoupon != null && welcomeCoupon.getFirstOnly() == 1) {
            Map<String, Object> checkParams = new HashMap<>();
            checkParams.put("member_id", memberId);
            checkParams.put("coupon_id", welcomeCoupon.getCouponId());

            int exists = userMapper.hasUserReceivedCoupon(checkParams);
            if (exists == 0) {
                Map<String, Object> params = new HashMap<>();
                params.put("member_id", memberId);
                params.put("coupon_id", welcomeCoupon.getCouponId());
                params.put("received_at", LocalDateTime.now());
                params.put("expired_at", LocalDateTime.now().plusMonths(1));

                userMapper.insertUserCoupon(params);
            }
        }
    }

    // 내 쿠폰 목록 조회
    public List<UserCoupon> getMyCoupons(Long memberId) {
        return userMapper.getCouponsByMemberId(memberId);
    }
    
    public boolean useCoupon(Long memberId, Long couponId) {
        int updated = userMapper.useUserCoupon(memberId, couponId);
        return updated > 0;
    }
}
