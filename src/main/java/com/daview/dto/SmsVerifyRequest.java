package com.daview.dto;

public class SmsVerifyRequest {
	private String phone;
    private String code;
    public String getPhone() { return phone; }
    public String getCode() { return code; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setCode(String code) { this.code = code; }
}
