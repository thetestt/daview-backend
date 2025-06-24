package com.daview.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserCoupon {
	private Long myCouponId;
    private Long memberId;
    private Long couponId;
    private LocalDateTime receivedAt;
    private LocalDateTime usedAt;
    private LocalDateTime expiredAt;

    // 조인해서 가져올 필드
    private String description;
    private int discount;
}
