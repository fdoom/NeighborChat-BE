package kit.hackathon.nearbysns.domain.comment.controller;

import kit.hackathon.nearbysns.domain.comment.dto.CommentPostRequest;
import kit.hackathon.nearbysns.domain.comment.dto.CommentPutRequest;
import kit.hackathon.nearbysns.domain.comment.dto.CommentResponse;
import kit.hackathon.nearbysns.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentResponse> postComment(
            @PathVariable Long articleId,
            @RequestBody CommentPostRequest commentPostRequest
    ) {
        // TODO: 로그인한 사용자의 ID를 가져와서 authorId에 넣어주어야 함
        Long authorId = 1L;
        CommentResponse commentResponse = commentService.postComment(
                articleId,
                authorId,
                commentPostRequest.commentContent(),
                commentPostRequest.parentCommentId());
        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId
    ) {
        // TODO: 로그인한 사용자의 ID를 가져와서 authorId에 넣어주어야 함
        Long authorId = 1L;
        commentService.deleteComment(commentId, authorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentPutRequest commentPutRequest
    ) {
        // TODO: 로그인한 사용자의 ID를 가져와서 authorId에 넣어주어야 함
        Long authorId = 1L;
        CommentResponse commentResponse = commentService.updateComment(commentId, authorId, commentPutRequest.commentContent());
        return ResponseEntity.ok(commentResponse);
    }



}
