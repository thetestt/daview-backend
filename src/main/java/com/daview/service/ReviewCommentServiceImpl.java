package com.daview.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daview.dto.ReviewCommentDTO;
import com.daview.mapper.ReviewCommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewCommentServiceImpl implements ReviewCommentService {

    private final ReviewCommentMapper commentMapper;

    @Override
    public void addComment(ReviewCommentDTO comment) {
        commentMapper.insertComment(comment);
    }

    @Override
    public List<ReviewCommentDTO> getCommentsByReviewId(Long revId) {
        return commentMapper.getCommentsByReviewId(revId);
    }

    @Override
    public void updateComment(ReviewCommentDTO comment) {
        commentMapper.updateComment(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentMapper.deleteComment(commentId);
    }
}
