package com.daview.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.daview.dto.ReviewCommentDTO;

@Mapper
public interface ReviewCommentMapper {
    void insertComment(ReviewCommentDTO comment);
    
    List<ReviewCommentDTO> getCommentsByReviewId(Long revId);
    
    void updateComment(ReviewCommentDTO comment);
    
    void deleteComment(Long commentId);
}
