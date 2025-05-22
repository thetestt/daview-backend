package com.daview.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daview.dto.CaregiverDTO;
import com.daview.dto.FacilityDTO;
import com.daview.service.CaregiverService;
import com.daview.service.FacilityService;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final FacilityService facilityService;
    private final CaregiverService caregiverService;

    public SearchController(FacilityService facilityService, CaregiverService caregiverService) {
        this.facilityService = facilityService;
        this.caregiverService = caregiverService;
    }

    @GetMapping("/search")
    public Map<String, Object> searchAll(@RequestParam String query) {
        List<FacilityDTO> nursinghomes = facilityService.searchByTypeAndKeyword("요양원", query);
        List<FacilityDTO> silvertowns = facilityService.searchByTypeAndKeyword("실버타운", query);
        List<CaregiverDTO> caregivers = caregiverService.searchByKeyword(query);

        Map<String, Object> result = new HashMap<>();
        result.put("nursinghomes", nursinghomes);
        result.put("silvertowns", silvertowns);
        result.put("caregivers", caregivers);

        return result;
    }
}