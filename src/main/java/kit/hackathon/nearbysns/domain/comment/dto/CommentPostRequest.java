package kit.hackathon.nearbysns.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "CommentPostRequest",
        description = "댓글 작성 요청",
        example = "{\n" +
                "  \"commentContent\": \"string\",\n" +
                "  \"parentCommentId\": 0\n" +
                "}"
)
public record CommentPostRequest (
        @Schema(
                name = "commentContent",
                description = "댓글 내용",
                example = "string",
                required = true
        )
        String commentContent,
        @Schema(
                name = "parentCommentId",
                description = "부모 댓글 ID",
                example = "0"
        )
        Long parentCommentId
) {
}
