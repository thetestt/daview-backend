package com.daview.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daview.dto.WishlistDTO;
import com.daview.dto.WishlistResponseDTO;
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
    
    public Map<String, List<WishlistResponseDTO>> getWishlistByMember(Long memberId) {
        Map<String, List<WishlistResponseDTO>> result = new HashMap<>();
        result.put("nursingHomes", wishlistMapper.getNursingWishlist(memberId));
        result.put("caregivers", wishlistMapper.getCaregiverWishlist(memberId));
        result.put("silvertowns", wishlistMapper.getSilvertownWishlist(memberId));
        return result;
    }
}
