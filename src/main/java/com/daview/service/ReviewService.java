package com.daview.service;

import java.util.List;

import com.daview.dto.ReviewDTO;

public interface ReviewService {
	void insertReview(ReviewDTO review);
	
	List<ReviewDTO> getAllReviews();
}
