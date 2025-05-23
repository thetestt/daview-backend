// ✅ UserDetailsServiceImpl.java - Spring Security 사용자 조회 서비스

package com.daview.security;

import com.daview.domain.dvusers.DvUser;
import com.daview.repository.DvUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final DvUserRepository dvUserRepository;

    public UserDetailsServiceImpl(DvUserRepository dvUserRepository) {
        this.dvUserRepository = dvUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DvUser user = dvUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new UserPrincipal(
                user.getMemberId(),
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities() // 예: ROLE_CAREGIVER 등 권한 반환 메서드 필요
        );
    }
} 
