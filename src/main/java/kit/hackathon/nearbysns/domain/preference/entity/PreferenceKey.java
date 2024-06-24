package kit.hackathon.nearbysns.domain.preference.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class PreferenceKey implements Serializable {
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "article_id")
    private Long articleId;

    public PreferenceKey() {
    }

    public PreferenceKey(Long accountId, Long articleId) {
        this.accountId = accountId;
        this.articleId = articleId;
    }
}
