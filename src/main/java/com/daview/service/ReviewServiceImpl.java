package com.daview.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daview.dto.ReviewDTO;
import com.daview.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
	
	private final ReviewMapper reviewMapper;
	
	@Override
	public void insertReview(ReviewDTO review) {
		reviewMapper.insertReview(review);
	}
	
	@Override
	public List<ReviewDTO> getAllReviews(){
		return reviewMapper.getAllReviews();
	}
}
