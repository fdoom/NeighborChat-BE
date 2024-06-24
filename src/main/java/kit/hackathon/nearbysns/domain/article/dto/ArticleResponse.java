package kit.hackathon.nearbysns.domain.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kit.hackathon.nearbysns.domain.article.entity.Article;

import java.time.Instant;

@Schema(
        name = "ArticleResponse",
        description = "게시글 응답",
        example = "{\n" +
                "  \"articleId\": 0,\n" +
                "  \"ownerId\": 0,\n" +
                "  \"content\": \"string\",\n" +
                "  \"createdAt\": \"2021-08-08T00:00:00Z\",\n" +
                "  \"expiredAt\": \"2021-08-08T00:00:00Z\",\n" +
                "  \"lastModifiedAt\": \"2021-08-08T00:00:00Z\",\n" +
                "  \"latitude\": 0,\n" +
                "  \"longitude\": 0\n" +
                "}"
)
public record ArticleResponse(
        @Schema(
                name = "articleId",
                description = "게시글 ID",
                example = "0",
                required = true
        )
        Long articleId,
        @Schema(
                name = "ownerId",
                description = "게시글 작성자 ID",
                example = "0",
                required = true
        )
        Long ownerId,
        @Schema(
                name = "content",
                description = "게시글 내용",
                example = "string",
                required = true
        )
        String content,
        @Schema(
                name = "createdAt",
                description = "게시글 생성일",
                example = "2021-08-08T00:00:00Z",
                required = true
        )
        Instant createdAt,
        @Schema(
                name = "expiredAt",
                description = "게시글 만료일",
                example = "2021-08-08T00:00:00Z",
                required = true
        )
        Instant expiredAt,
        @Schema(
                name = "lastModifiedAt",
                description = "게시글 마지막 수정일",
                example = "2021-08-08T00:00:00Z",
                required = true
        )
        Instant lastModifiedAt,
        @Schema(
                name = "latitude",
                description = "위도",
                example = "0",
                required = true
        )
        Double latitude,
        @Schema(
                name = "longitude",
                description = "경도",
                example = "0",
                required = true
        )
        Double longitude
) {
    public static ArticleResponse of(Article article) {
        return new ArticleResponse(
                article.getArticleId(),
                article.getAccount().getAccountId(),
                article.getArticleContent(),
                article.getArticleCreatedAt(),
                article.getArticleExpiredAt(),
                article.getArticleLastModifiedAt(),
                article.getArticleLocation().getY(),
                article.getArticleLocation().getX()
        );
    }
}
