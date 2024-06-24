package kit.hackathon.nearbysns.domain.article.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import kit.hackathon.nearbysns.domain.account.entity.Account;
import kit.hackathon.nearbysns.domain.account.repository.AccountRepository;
import kit.hackathon.nearbysns.domain.article.dto.ArticlePagedResponse;
import kit.hackathon.nearbysns.domain.article.dto.ArticleResponse;
import kit.hackathon.nearbysns.domain.article.entity.Article;
import kit.hackathon.nearbysns.domain.article.policy.ArticleRetentionPolicy;
import kit.hackathon.nearbysns.domain.article.repository.ArticleRepository;
import kit.hackathon.nearbysns.global.base.exception.CustomException;
import kit.hackathon.nearbysns.global.base.exception.ErrorCode;
import kit.hackathon.nearbysns.global.validator.NonExpiredArticleId;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final AccountRepository accountRepository;
    private final GeometryFactory geometryFactory;

    @Transactional
    public ArticleResponse postArticle(
            @NotNull(message = "userId must not be null") @Positive(message = "userId must be positive")
            Long userId,
            @NotBlank(message = "content must not be blank")
            String content,
            @NotNull(message = "latitude must not be null") @Range(min = -90, max = 90) // latitude range: -90 ~ 90
            Double latitude,
            @NotNull(message = "longitude must not be null") @Range(min = -180, max = 180) // longitude range: -180 ~ 180
            Double longitude) {

        // FETCH ACCOUNT
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.REQUESTED_ACCOUNT_NOT_FOUND));

        // CREATE ARTICLE
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        Article createdArticle = Article.createNew(account, content, ArticleRetentionPolicy.ONE_DAY, point);

        // SAVE ARTICLE
        Article savedArticle = articleRepository.save(createdArticle);

        // RETURN RESPONSE
        return ArticleResponse.of(savedArticle);
    }

    public ArticlePagedResponse getArticles(
            @NotNull(message = "latitude must not be null") @Range(min = -90, max = 90) // latitude range: -90 ~ 90
            Double latitude,
            @NotNull(message = "longitude must not be null") @Range(min = -180, max = 180) // longitude range: -180 ~ 180
            Double longitude,
            @NotNull(message = "meters must not be null") @Positive(message = "meters must be positive")
            Double meters,
            @NotNull(message = "after must not be null") @PastOrPresent(message = "after must be past or present")
            Instant after,
            @NotNull(message = "size must not be null") @Positive(message = "size must be positive")
            Integer size) {
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        List<Article> allPagedArticleNearby = articleRepository.findAllPagedArticleNearby(point, meters, after, size);
        return ArticlePagedResponse.of(allPagedArticleNearby);
    }

    public ArticleResponse getArticle(
            @NonExpiredArticleId(message = "article not found or expired")
            Long articleId) {
        Article article = articleRepository.findArticleById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        return ArticleResponse.of(article);
    }

    @Transactional
    public void deleteArticle(
            @NonExpiredArticleId(message = "article not found or expired")
            Long articleId,
            @NotNull(message = "userId must not be null") @Positive(message = "userId must be positive")
            Long userId) {
        Article article = articleRepository.findArticleById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        if (!article.getAccount().getAccountId().equals(userId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        article.setArticleDeletedAt(Instant.now());
        Article saved = articleRepository.save(article);
    }

    @Transactional
    public void updateArticle(
            @NonExpiredArticleId(message = "article not found or expired")
            Long articleId,
            @NotNull(message = "userId must not be null") @Positive(message = "userId must be positive")
            Long userId,
            @NotBlank(message = "content must not be blank")
            String content) {
        Article article = articleRepository.findArticleById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        if (!article.getAccount().getAccountId().equals(userId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        article.setArticleContent(content);
        Article saved = articleRepository.save(article);
    }


}
