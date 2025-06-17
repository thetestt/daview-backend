package com.daview.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.daview.dto.WishlistDTO;

@Mapper
public interface WishlistMapper {
    Boolean isWishlisted(@Param("memberId") Long memberId, @Param("facilityId") String facilityId);
    void insertWishlist(WishlistDTO wishlistDTO);
    void deleteWishlist(WishlistDTO wishlistDTO);
}
