package com.daview.controller;

import com.daview.dto.DvUserDTO;
import com.daview.service.DvUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class DvUserController {

    private final DvUserService dvUserService;

    @GetMapping("/{id}")
    public ResponseEntity<DvUserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(dvUserService.getUserById(id));
    }
}
