package com.daview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.daview.dto.ReviewDTO;

@Mapper
public interface ReviewMapper {
	void insertReview(ReviewDTO review);
	
	List<ReviewDTO> getReviewsByPage(Map<String, Integer> params);
	
	int getTotalReviewCount();
	
	ReviewDTO getReviewById(Long revId);
}
