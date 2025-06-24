package com.daview.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CouponDTO {
	private Long couponId;
    private String code;
    private String description;
    private int discount;
    private int firstOnly;
    private LocalDateTime createdAt;
}
