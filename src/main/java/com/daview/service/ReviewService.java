package com.daview.service;

import java.util.List;

import com.daview.dto.ReviewDTO;

public interface ReviewService {
	void insertReview(ReviewDTO review);
	
	List<ReviewDTO> getReviewsByPage(int page, int size);
	
	int getTotalReviewCount();
	
	ReviewDTO getReviewById(Long revId);
}
