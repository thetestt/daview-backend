package com.daview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.daview.dto.ReviewDTO;

@Mapper
public interface ReviewMapper {
	void insertReview(ReviewDTO review);
	
	List<ReviewDTO> getAllReviews();
}
