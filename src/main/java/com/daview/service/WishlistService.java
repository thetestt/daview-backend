package com.daview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daview.dto.WishlistDTO;
import com.daview.mapper.WishlistMapper;

@Service
public class WishlistService {

    @Autowired
    private WishlistMapper wishlistMapper;

    public boolean isWishlisted(Long memberId, String facilityId) {
        return wishlistMapper.isWishlisted(memberId, facilityId);
    }

    public void addToWishlist(Long memberId, String facilityId) {
        wishlistMapper.insertWishlist(new WishlistDTO(memberId, facilityId));
    }

    public void removeFromWishlist(Long memberId, String facilityId) {
        wishlistMapper.deleteWishlist(new WishlistDTO(memberId, facilityId));
    }
}
