package com.daview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.daview.dto.CaregiverDTO;
import com.daview.service.CaregiverService;


@RestController
@RequestMapping("/api/caregivers")
@CrossOrigin(origins = "http://localhost:3000")
public class CaregiverController {

    @Autowired
    private CaregiverService caregiverService;


    @GetMapping("/")
    public List<CaregiverDTO> getAllCaregivers() {
        return caregiverService.getAllCaregivers();
    }


    @GetMapping("/{id}")
    public CaregiverDTO getCaregiverById(@PathVariable("id") String caregiverId) {
        return caregiverService.getCaregiverById(caregiverId);
    }

}
