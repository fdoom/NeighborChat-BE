package kit.hackathon.nearbysns.domain.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ArticlePutRequest",
        description = "게시글 수정 요청",
        example = "{\n" +
                "  \"content\": \"string\"\n" +
                "}"
)
public record ArticlePutRequest(
        @Schema(
                name = "content",
                description = "게시글 내용",
                example = "string",
                required = true
        )
        String content
) {
}
