package kit.hackathon.nearbysns.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.hackathon.nearbysns.domain.comment.dto.CommentPostRequest;
import kit.hackathon.nearbysns.domain.comment.dto.CommentPutRequest;
import kit.hackathon.nearbysns.domain.comment.dto.CommentResponse;
import kit.hackathon.nearbysns.domain.comment.service.CommentService;
import kit.hackathon.nearbysns.global.base.exception.ErrorResponse;
import kit.hackathon.nearbysns.global.config.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "댓글", description = "댓글 관련 API")
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "성공"),
        @ApiResponse(responseCode = "204",description = "성공"),
        @ApiResponse(responseCode = "404",description = "리소스가 존재하지 않음",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "빈칸이 존재하거나 잘못된 요청을 했습니다.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "인증 정보 없음",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "권한 없음",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
})
public class CommentController {
    private final CommentService commentService;
    private final SecurityUtil securityUtil;

    // 1단계 댓글 조회
    @GetMapping("/articles/{articleId}/comments")
    @Operation(summary = "루트 댓글(게시글에 직접 달린 댓글) 가져오기", description = "게시글에 직접 달린 댓글을 가져옵니다.")
    public ResponseEntity<Page<CommentResponse>> getParentComments(
            @Parameter(description = "게시글 ID",required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable Long articleId,
            @Parameter(description = "페이지 정보", required = false, schema = @Schema(type = "integer"),in = ParameterIn.QUERY)
            @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", required = false, schema = @Schema(type = "integer"), in = ParameterIn.QUERY)
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentResponse> parentComments = commentService.getParentComments(articleId, pageable);
        return ResponseEntity.ok(parentComments);
    }

    // 2단계 댓글 조회
    @GetMapping("/articles/{articleId}/comments/{parentCommentId}/comments")
    @Operation(summary = "자식 댓글(댓글에 달린 댓글) 가져오기", description = "댓글에 달린 댓글을 가져옵니다.")
    public ResponseEntity<Page<CommentResponse>> getChildComments(
            @Parameter(description = "게시글 ID",required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable Long articleId,
            @Parameter(description = "부모 댓글 ID",required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable Long parentCommentId,
            @Parameter(description = "페이지 정보", required = false, schema = @Schema(type = "integer"),in = ParameterIn.QUERY)
            @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", required = false, schema = @Schema(type = "integer"), in = ParameterIn.QUERY)
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentResponse> childComments = commentService.getChildComments(articleId, parentCommentId, pageable);
        return ResponseEntity.ok(childComments);
    }


    @PostMapping("/articles/{articleId}/comments")
    @Operation(summary = "댓글 작성", description = "게시글에 댓글을 작성합니다.")
    public ResponseEntity<CommentResponse> postComment(
            @Parameter(description = "게시글 ID",required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable Long articleId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "댓글 작성 요청", required = true)
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
    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "게시글 ID",required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable Long articleId,
            @Parameter(description = "댓글 ID",required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable Long commentId
    ) {
        Long authorId = securityUtil.getAccountId();
        commentService.deleteComment(articleId, commentId, authorId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/articles/{articleId}/comments/{commentId}")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<CommentResponse> updateComment(
            @Parameter(description = "게시글 ID",required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable Long articleId,
            @Parameter(description = "댓글 ID",required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable Long commentId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "댓글 수정 요청", required = true, content = @Content(schema = @Schema(implementation = CommentPutRequest.class)))
            @RequestBody CommentPutRequest commentPutRequest
    ) {
        Long authorId = securityUtil.getAccountId();
        CommentResponse commentResponse = commentService.updateComment(articleId, commentId, authorId, commentPutRequest.commentContent());
        return ResponseEntity.ok(commentResponse);
    }



}
