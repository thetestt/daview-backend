package com.daview.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private Long memberId;
    private String username;
    private String password;
    private String name;
    private String email;
    private String gender;
    private String phone;
    private String role;
    private boolean agreeSms;
    private boolean agreeEmail;
    private boolean agreePush;

    public boolean isSmsAgree() { return agreeSms; }
    public boolean isEmailAgree() { return agreeEmail; }
    public boolean isPushAgree() { return agreePush; }
}
