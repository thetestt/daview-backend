package com.daview.dto;

import lombok.Data;


public class WishlistDTO {
    private Long memberId;
    private String facilityId;
    
    
    
	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}


	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}


	public WishlistDTO() {
	}


	public WishlistDTO(Long memberId, String facilityId) {
		super();
		this.memberId = memberId;
		this.facilityId = facilityId;
	}
	
	
    
	
    
    
}
