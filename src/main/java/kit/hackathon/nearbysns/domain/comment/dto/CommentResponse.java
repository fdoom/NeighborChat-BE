package kit.hackathon.nearbysns.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kit.hackathon.nearbysns.domain.comment.entity.Comment;

import java.time.Instant;

@Schema(
        name = "CommentResponse",
        description = "댓글 응답",
        example = "{\n" +
                "  \"commentId\": 0,\n" +
                "  \"articleId\": 0,\n" +
                "  \"authorId\": 0,\n" +
                "  \"parentCommentId\": 0,\n" +
                "  \"hasChildComments\": true,\n" +
                "  \"isSubComment\": true,\n" +
                "  \"commentContent\": \"string\",\n" +
                "  \"commentCreatedAt\": \"2021-08-08T00:00:00Z\",\n" +
                "  \"commentLastModifiedAt\": \"2021-08-08T00:00:00Z\",\n" +
                "  \"commentDeletedAt\": \"2021-08-08T00:00:00Z\"\n" +
                "}"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentResponse(
        @Schema(
                name = "commentId",
                description = "댓글 ID",
                example = "0",
                required = true
        )
        Long commentId,
        @Schema(
                name = "articleId",
                description = "게시글 ID",
                example = "0",
                required = true
        )
        Long articleId,
        @Schema(
                name = "authorId",
                description = "댓글 작성자 ID",
                example = "0",
                required = true
        )
        Long authorId,
        @Schema(
                name = "parentCommentId",
                description = "부모 댓글 ID",
                example = "0"
        )
        Long parentCommentId,
        @Schema(
                name = "hasChildComments",
                description = "하위 댓글 여부",
                example = "true",
                required = true
        )
        Boolean hasChildComments,
        @Schema(
                name = "isSubComment",
                description = "자식 댓글인지 여부",
                example = "true",
                required = true
        )
        Boolean isSubComment,
        @Schema(
                name = "commentContent",
                description = "댓글 내용",
                example = "string",
                required = true
        )
        String commentContent,
        @Schema(
                name = "commentCreatedAt",
                description = "댓글 생성일",
                example = "2021-08-08T00:00:00Z",
                required = true
        )
        Instant commentCreatedAt,
        @Schema(
                name = "commentLastModifiedAt",
                description = "댓글 마지막 수정일",
                example = "2021-08-08T00:00:00Z",
                required = true
        )
        Instant commentLastModifiedAt
) {
    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getArticle().getArticleId(),
                comment.getAccount().getAccountId(),
                comment.getParentComment() == null ? null : comment.getParentComment().getId(),
                !comment.getChildComments().isEmpty(),
                comment.getParentComment() != null,
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getLastModifiedAt()
        );
    }
}
