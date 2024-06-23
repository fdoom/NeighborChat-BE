package kit.hackathon.nearbysns.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kit.hackathon.nearbysns.domain.article.entity.Article;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ArticlePagedResponse (
        Long totalElements,
        Instant lastElementCreatedAt,
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
