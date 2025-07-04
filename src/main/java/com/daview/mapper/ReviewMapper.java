package com.daview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.daview.dto.ReviewDTO;

@Mapper
public interface ReviewMapper {
	void insertReview(ReviewDTO review);

	List<ReviewDTO> getAllReviews();
	
	List<ReviewDTO> getReviewsByPage(Map<String, Integer> params);
	
	int getTotalReviewCount();
	
	ReviewDTO getReviewById(Long revId);
	
	void increaseReviewViews(Long revId);
	
	void updateReview(ReviewDTO review);
	
	List<ReviewDTO> findByMemberId(Long memberId);
	
	List<ReviewDTO> getReviewsByProdNm(String prodNm);
	
	List<ReviewDTO> getReviewsWithCommentCount(Map<String, Object> param);

}
