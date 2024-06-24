package kit.hackathon.nearbysns.global.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kit.hackathon.nearbysns.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class NonExpiredArticleIdValidator implements ConstraintValidator<NonExpiredArticleId, Long> {
    private final ArticleRepository articleRepository;

    @Override
    public boolean isValid(Long articleId, ConstraintValidatorContext constraintValidatorContext) {
        if (articleId == null || articleId <= 0) {
            return false;
        }

        boolean match = articleRepository.findByArticleId(articleId).stream().anyMatch(article -> article.getArticleExpiredAt().isAfter(Instant.now()));
        if (!match) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("article not found or expired").addConstraintViolation();
        }
        return match;
    }
}
