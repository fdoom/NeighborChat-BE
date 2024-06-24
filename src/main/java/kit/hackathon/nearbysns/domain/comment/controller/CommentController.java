package kit.hackathon.nearbysns.domain.comment.controller;

import kit.hackathon.nearbysns.domain.comment.dto.CommentPostRequest;
import kit.hackathon.nearbysns.domain.comment.dto.CommentPutRequest;
import kit.hackathon.nearbysns.domain.comment.dto.CommentResponse;
import kit.hackathon.nearbysns.domain.comment.service.CommentService;
import kit.hackathon.nearbysns.global.config.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final SecurityUtil securityUtil;

    // 1단계 댓글 조회
    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<Page<CommentResponse>> getParentComments(
            @PathVariable Long articleId,
            Pageable pageable
    ) {
        Page<CommentResponse> parentComments = commentService.getParentComments(articleId, pageable);
        return ResponseEntity.ok(parentComments);
    }

    // 2단계 댓글 조회
    @GetMapping("/articles/{articleId}/comments/{parentCommentId}/comments")
    public ResponseEntity<Page<CommentResponse>> getChildComments(
            @PathVariable Long articleId,
            @PathVariable Long parentCommentId,
            Pageable pageable
    ) {
        Page<CommentResponse> childComments = commentService.getChildComments(articleId, parentCommentId, pageable);
        return ResponseEntity.ok(childComments);
    }


    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentResponse> postComment(
            @PathVariable Long articleId,
            @RequestBody CommentPostRequest commentPostRequest
    ) {
        Long authorId = securityUtil.getAccountId();
        CommentResponse commentResponse = commentService.postComment(
                articleId,
                authorId,
                commentPostRequest.commentContent(),
                commentPostRequest.parentCommentId());
        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long articleId,
            @PathVariable Long commentId
    ) {
        Long authorId = securityUtil.getAccountId();
        commentService.deleteComment(articleId, commentId, authorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/articles/{articleId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long articleId,
            @PathVariable Long commentId,
            @RequestBody CommentPutRequest commentPutRequest
    ) {
        Long authorId = securityUtil.getAccountId();
        CommentResponse commentResponse = commentService.updateComment(articleId, commentId, authorId, commentPutRequest.commentContent());
        return ResponseEntity.ok(commentResponse);
    }



}
