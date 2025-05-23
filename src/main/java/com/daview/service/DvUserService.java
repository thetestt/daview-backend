package com.daview.service;

import com.daview.domain.dvusers.DvUser;
import com.daview.dto.DvUserDTO;
import com.daview.repository.DvUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DvUserService {

    private final DvUserRepository dvUserRepository;

    public DvUserDTO getUserById(Long id) {
        DvUser user = dvUserRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return new DvUserDTO(
            user.getMemberId(),
            user.getUsername(),
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getRole().name()
        );
    }
}
