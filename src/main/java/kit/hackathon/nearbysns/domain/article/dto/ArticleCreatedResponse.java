package kit.hackathon.nearbysns.domain.article.dto;

import kit.hackathon.nearbysns.domain.article.entity.Article;

import java.time.Instant;

public record ArticleCreatedResponse(
        Long articleId,
        Long ownerId,
        String content,
        Instant createdAt,
        Instant expiredAt,
        Instant lastModifiedAt,
        Double latitude,
        Double longitude
) {
    public static ArticleCreatedResponse of(Article article) {
        return new ArticleCreatedResponse(
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
