package kit.hackathon.nearbysns.domain.article.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kit.hackathon.nearbysns.domain.account.entity.Account;
import kit.hackathon.nearbysns.domain.account.repository.AccountRepository;
import kit.hackathon.nearbysns.domain.article.dto.ArticleCreatedResponse;
import kit.hackathon.nearbysns.domain.article.entity.Article;
import kit.hackathon.nearbysns.domain.article.policy.ArticleRetentionPolicy;
import kit.hackathon.nearbysns.domain.article.repository.ArticleRepository;
import kit.hackathon.nearbysns.global.base.exception.CustomException;
import kit.hackathon.nearbysns.global.base.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final AccountRepository accountRepository;
    private final GeometryFactory geometryFactory;

    //TODO 이거 맞나 확인
    @Transactional
    @PreAuthorize("isFullyAuthenticated()")
    public ArticleCreatedResponse postArticle(
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
        return ArticleCreatedResponse.of(savedArticle);
    }


}
