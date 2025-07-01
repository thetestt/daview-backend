package com.daview.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    private Long memberId;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String role;
    private String gender;
    
    private String bankName;
    private String accountNumber;

    private boolean agreeSms;
    private boolean agreeEmail;
    private boolean agreePush;

    public boolean isAgreeSms() { return agreeSms; }
    public void setAgreeSms(boolean agreeSms) { this.agreeSms = agreeSms; }

    public boolean isAgreeEmail() { return agreeEmail; }
    public void setAgreeEmail(boolean agreeEmail) { this.agreeEmail = agreeEmail; }

    public boolean isAgreePush() { return agreePush; }
    public void setAgreePush(boolean agreePush) { this.agreePush = agreePush; }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public Long getMemberId() {
        return memberId;
    }
}
