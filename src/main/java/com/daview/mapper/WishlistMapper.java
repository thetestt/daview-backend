package com.daview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.daview.dto.CaregiverDTO;
import com.daview.dto.FacilityDTO;
import com.daview.dto.WishlistDTO;

@Mapper
public interface WishlistMapper {
    Boolean isWishlisted(@Param("memberId") Long memberId, @Param("facilityId") String facilityId);
    void insertWishlist(WishlistDTO wishlistDTO);
    void deleteWishlist(WishlistDTO wishlistDTO);
    
    List<FacilityDTO> getNursingWishlist(Long memberId);
    List<FacilityDTO> getSilvertownWishlist(Long memberId);
    List<CaregiverDTO> getCaregiverWishlist(Long memberId);

}
