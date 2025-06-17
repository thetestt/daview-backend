package com.daview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daview.dto.WishlistDTO;
import com.daview.service.WishlistService;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkWishlist(@RequestParam Long memberId, @RequestParam String facilityId) {
        return ResponseEntity.ok(wishlistService.isWishlisted(memberId, facilityId));
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addToWishlist(@RequestBody WishlistDTO dto) {
        wishlistService.addToWishlist(dto.getMemberId(), dto.getFacilityId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removeFromWishlist(@RequestBody WishlistDTO dto) {
        wishlistService.removeFromWishlist(dto.getMemberId(), dto.getFacilityId());
        return ResponseEntity.ok().build();
    }
}
