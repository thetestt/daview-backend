package com.daview.dto;

import java.util.List;

public class FacilityDTO {
    private String facilityId;
    private String facilityName;
    private Integer facilityCharge;
    private String facilityAddressLocation;
    private String facilityAddressCity;
    private String photoUrl; // ✅ 단일 이미지 필드


    // ✅ Getter/Setter
    public String getFacilityId() { return facilityId; }
    public void setFacilityId(String facilityId) { this.facilityId = facilityId; }

    public String getFacilityName() { return facilityName; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }

    public int getFacilityCharge() { return facilityCharge; }
    public void setFacilityCharge(int facilityCharge) { this.facilityCharge = facilityCharge; }

    public String getFacilityAddressLocation() { return facilityAddressLocation; }
    public void setFacilityAddressLocation(String facilityAddressLocation) { this.facilityAddressLocation = facilityAddressLocation; }

    public String getFacilityAddressCity() { return facilityAddressCity; }
    public void setFacilityAddressCity(String facilityAddressCity) { this.facilityAddressCity = facilityAddressCity; }

	public void setFacilityCharge(Integer facilityCharge) {
		this.facilityCharge = facilityCharge;
		
		
		
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
    
    
}
