package com.daview.service;

import java.util.List;

import com.daview.dto.ReviewCommentDTO;

public interface ReviewCommentService {
    void addComment(ReviewCommentDTO comment);
    
    List<ReviewCommentDTO> getCommentsByReviewId(Long revId);
    
    void updateComment(ReviewCommentDTO comment);
    
    void deleteComment(Long commentId);
}
