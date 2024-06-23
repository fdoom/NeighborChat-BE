package kit.hackathon.nearbysns.domain.article.dto;

import kit.hackathon.nearbysns.domain.article.entity.Article;

import java.time.Instant;

public record ArticleResponse(
        Long articleId,
        Long ownerId,
        String content,
        Instant createdAt,
        Instant expiredAt,
        Instant lastModifiedAt,
        Double latitude,
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
