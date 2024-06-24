package kit.hackathon.nearbysns.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "CommentPutRequest",
        description = "댓글 수정 요청",
        example = "{\n" +
                "  \"commentContent\": \"string\"\n" +
                "}"
)
public record CommentPutRequest(
        @Schema(
                name = "commentContent",
                description = "댓글 내용",
                example = "string",
                required = true
        )
        String commentContent
) {
}
