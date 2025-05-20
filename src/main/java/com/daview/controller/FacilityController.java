package com.daview.controller;

import com.daview.dto.FacilityDTO;
import com.daview.service.FacilityService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/facilities")
@CrossOrigin(origins = "http://localhost:3000")
public class FacilityController {

    private final FacilityService facilityService;
    
    
    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping("/silvertowns")
    public List<FacilityDTO> getSilvertowns() {
        return facilityService.getSilvertowns();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FacilityDTO> getFacilityDetail(@PathVariable("id") String id) {
    	System.out.println("💡 요청된 ID: " + id);
        FacilityDTO detail = facilityService.getFacilityDetail(id);
        System.out.println("💡 결과: " + detail);
        if (detail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detail);
    }
    
    
}

