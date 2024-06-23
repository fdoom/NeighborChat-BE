package kit.hackathon.nearbysns.domain.article.policy;

import jakarta.persistence.Transient;

public interface Expirable {
    @Transient
    boolean isExpired();
}
