package com.daview.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.daview.dto.ReviewDTO;
import com.daview.mapper.ReviewMapper;
import com.daview.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
	
	private final ReviewMapper reviewMapper;
	private final UserMapper userMapper;
	
	@Override
	public void insertReview(ReviewDTO review) {
		reviewMapper.insertReview(review);
	}
	
	@Override
	public List<ReviewDTO> getAllReviews() {
		return reviewMapper.getAllReviews();
	}
	
	@Override
	public List<ReviewDTO> getReviewsByPage(int page, int size){
		Map<String, Integer> params = new HashMap<>();
		params.put("size", size);
		params.put("offset", (page - 1) * size);
		return reviewMapper.getReviewsByPage(params);
	}
	
	@Override
	public int getTotalReviewCount() {
		return reviewMapper.getTotalReviewCount();
	}
	
	@Override
	public ReviewDTO getReviewById(Long revId) {
		reviewMapper.increaseReviewViews(revId);
		return reviewMapper.getReviewById(revId);
	}
	
	@Override
	public void increaseReviewViews(Long revId) {
		reviewMapper.increaseReviewViews(revId);
	}
	
	@Override
	public ReviewDTO getReviewByIdForEdit(Long revId) {
		return reviewMapper.getReviewById(revId);
	}
	
	@Override
	public void updateReview(ReviewDTO review) {
		reviewMapper.updateReview(review);
	}
	@Override
	public List<ReviewDTO> getReviewsByMemberId(Long memberId) {
	    return reviewMapper.findByMemberId(memberId);
	}
	
	@Override
	public String findNameByMemberId(Long memberId) {
		return userMapper.findNameByMemberId(memberId);
	}

}
