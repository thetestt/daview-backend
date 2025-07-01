package com.daview.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.daview.dto.ReviewCommentDTO;
import com.daview.service.ReviewCommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/review-comments")
@RequiredArgsConstructor
public class ReviewCommentController {

    private final ReviewCommentService commentService;

    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody ReviewCommentDTO comment) {
        commentService.addComment(comment);
        return ResponseEntity.ok("댓글 등록 완료");
    }

    @GetMapping("/review/{revId}")
    public ResponseEntity<List<ReviewCommentDTO>> getCommentsByReview(@PathVariable Long revId) {
        List<ReviewCommentDTO> comments = commentService.getCommentsByReviewId(revId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId, @RequestBody ReviewCommentDTO comment) {
        comment.setCommentId(commentId);
        commentService.updateComment(comment);
        return ResponseEntity.ok("댓글 수정 완료");
    }

    @PutMapping("/{commentId}/delete")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("댓글 삭제 처리 완료");
    }
}
