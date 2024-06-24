package kit.hackathon.nearbysns.domain.preference.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "PreferenceSummaryResponse",
        description = "선호도 요약 응답",
        example = "{\n" +
                "  \"articleId\": 0,\n" +
                "  \"likeCount\": 0,\n" +
                "  \"dislikeCount\": 0\n" +
                "}"
)
public record PreferenceSummaryResponse(
        @Schema(
                name = "articleId",
                description = "게시글 ID",
                example = "0",
                required = true
        )
        Long articleId,
        @Schema(
                name = "likeCount",
                description = "좋아요 수",
                example = "0",
                required = true
        )
        Long likeCount,
        @Schema(
                name = "dislikeCount",
                description = "싫어요 수",
                example = "0",
                required = true
        )
        Long dislikeCount
) {
}
