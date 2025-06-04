package com.daview.domain.user;

import lombok.Data;

@Data
public class User {
    private Long memberId;
    private String username;
    private String password;
    private String name;
    private String email;
    private String gender;
    private String phone;
    private String role;
}
