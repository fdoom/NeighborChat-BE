package kit.hackathon.nearbysns.domain.comment.controller;

import kit.hackathon.nearbysns.domain.comment.dto.CommentResponse;
import kit.hackathon.nearbysns.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

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


}
