package com.daview.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.daview.dto.WishlistDTO;
import com.daview.mapper.WishlistMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistMapper wishlistMapper;

    // 찜 여부 확인
    public boolean isWishlisted(Long memberId, String facilityId) {
        return wishlistMapper.isWishlisted(memberId, facilityId);
    }

    // 찜 추가
    public void addToWishlist(Long memberId, String facilityId) {
        wishlistMapper.insertWishlist(new WishlistDTO(memberId, facilityId));
    }

    // 찜 삭제
    public void removeFromWishlist(Long memberId, String facilityId) {
        wishlistMapper.deleteWishlist(new WishlistDTO(memberId, facilityId));
    }

    // 찜 전체 목록 (요양원, 요양사, 실버타운 상세정보 포함)
    public Map<String, List<?>> getWishlistByMember(Long memberId) {
        Map<String, List<?>> result = new HashMap<>();
        result.put("nursingHomes", wishlistMapper.getNursingWishlist(memberId)); // FacilityDTO 리스트
        result.put("caregivers", wishlistMapper.getCaregiverWishlist(memberId)); // CaregiverDTO 리스트
        result.put("silvertowns", wishlistMapper.getSilvertownWishlist(memberId)); // FacilityDTO 리스트
        return result;
    }
}
