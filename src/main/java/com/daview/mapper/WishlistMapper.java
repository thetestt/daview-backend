package com.daview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.daview.dto.WishlistDTO;
import com.daview.dto.WishlistResponseDTO;

@Mapper
public interface WishlistMapper {
    Boolean isWishlisted(@Param("memberId") Long memberId, @Param("facilityId") String facilityId);
    void insertWishlist(WishlistDTO wishlistDTO);
    void deleteWishlist(WishlistDTO wishlistDTO);
    
    List<WishlistResponseDTO> getNursingWishlist(Long memberId);
    List<WishlistResponseDTO> getCaregiverWishlist(Long memberId);
    List<WishlistResponseDTO> getSilvertownWishlist(Long memberId);
}
