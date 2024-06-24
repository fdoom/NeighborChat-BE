package kit.hackathon.nearbysns.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kit.hackathon.nearbysns.domain.article.entity.Article;

import java.time.Instant;
import java.util.List;

@Schema(
        name = "ArticlePagedResponse",
        description = "게시글 페이징 응답",
        example = "{\n" +
                "  \"totalElements\": 0,\n" +
                "  \"lastElementCreatedAt\": \"2021-08-08T00:00:00Z\",\n" +
                "  \"articles\": [\n" +
                "    {\n" +
                "      \"articleId\": 0,\n" +
                "      \"account\": {\n" +
                "        \"accountId\": 0,\n" +
                "        \"accountName\": \"string\",\n" +
                "        \"accountLoginId\": \"string\",\n" +
                "        \"accountProfileImageUrl\": \"string\"\n" +
                "      },\n" +
                "      \"content\": \"string\",\n" +
                "      \"latitude\": 0,\n" +
                "      \"longitude\": 0,\n" +
                "      \"articleCreatedAt\": \"2021-08-08T00:00:00Z\",\n" +
                "      \"articleLastModifiedAt\": \"2021-08-08T00:00:00Z\",\n" +
                "      \"articleDeletedAt\": \"2021-08-08T00:00:00Z\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ArticlePagedResponse (
        @Schema(
                name = "totalElements",
                description = "전체 요소 수",
                example = "0",
                required = true
        )
        Long totalElements,
        @Schema(
                name = "lastElementCreatedAt",
                description = "요소 중 가장 마지막 생성일",
                example = "2021-08-08T00:00:00Z",
                required = true
        )
        Instant lastElementCreatedAt,
        @Schema(
                name = "articles",
                description = "게시글 목록",
                required = true
        )
        List<ArticleResponse> articles
) {
    private static final ArticlePagedResponse EMPTY = new ArticlePagedResponse(0L, null, List.of());
    public static ArticlePagedResponse of(List<Article> articles) {
        if (articles == null) {
            return EMPTY;
        }
        if (articles.isEmpty()) {
            return EMPTY;
        }

        Long totalElements = (long) articles.size();
        Instant lastElementCreatedAt = articles.get(articles.size() - 1).getArticleCreatedAt();
        List<ArticleResponse> articleResponses = articles.stream()
                .map(ArticleResponse::of)
                .toList();
        return new ArticlePagedResponse(totalElements, lastElementCreatedAt, articleResponses);
    }
}
