package com.daview.dto;

import lombok.Data;

@Data
public class VerificationRequest {
	private String phone;
    private String code;
}
