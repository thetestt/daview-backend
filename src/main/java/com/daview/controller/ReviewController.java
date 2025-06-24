package com.daview.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daview.dto.ReviewDTO;
import com.daview.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService reviewService;

	@PostMapping
	public ResponseEntity<ReviewDTO> insertReview(@RequestBody ReviewDTO review){
		reviewService.insertReview(review);
		return ResponseEntity.ok(review);
	}
	
	@GetMapping
	public ResponseEntity<List<ReviewDTO>> getAllReviews(){
		List<ReviewDTO> result = reviewService.getAllReviews();
		return ResponseEntity.ok(result);
	}

}
