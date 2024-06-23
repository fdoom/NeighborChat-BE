package kit.hackathon.nearbysns.domain.article.policy;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.time.Duration;
import java.time.Instant;

public interface ArticleRetentionPolicy {
    ArticleRetentionPolicy ONE_DAY = (createdAt) -> createdAt.plusSeconds(Duration.ofDays(1).getSeconds());

    @Nullable
    Instant getExpiredAt(@Nonnull Instant createdAt);
}
