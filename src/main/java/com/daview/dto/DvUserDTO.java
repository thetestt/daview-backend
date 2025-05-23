package com.daview.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DvUserDTO {
    private Long memberId;
    private String username;
    private String name;
    private String email;
    private String phone;
    private String role;
}
