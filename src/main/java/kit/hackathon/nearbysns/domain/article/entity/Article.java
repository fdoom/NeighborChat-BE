package kit.hackathon.nearbysns.domain.article.entity;

import jakarta.persistence.*;
import kit.hackathon.nearbysns.domain.account.entity.Account;
import kit.hackathon.nearbysns.domain.article.policy.ArticleRetentionPolicy;
import kit.hackathon.nearbysns.domain.article.policy.Expirable;
import kit.hackathon.nearbysns.domain.comment.entity.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "article")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class Article implements Expirable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long articleId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "article_content")
    private String articleContent;

    @CreatedDate
    @Column(name = "article_created_at")
    private Instant articleCreatedAt;

    @Column(name = "article_expired_at")
    private Instant articleExpiredAt;

    @LastModifiedDate
    @Column(name = "article_last_modified_at")
    private Instant articleLastModifiedAt;

    @Column(name = "article_deleted_at")
    private Instant articleDeletedAt;

    @Column(name = "article_location")
    private Point articleLocation;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();



    // *****************************
    // BOILERPLATE
    // *****************************

    public static Article createNew(Account account, String articleContent, ArticleRetentionPolicy articleRetentionPolicy, Point articleLocation) {
        Assert.notNull(account, "account must not be null");
        Assert.notNull(articleContent, "articleContent must not be null");
        Assert.notNull(articleLocation, "articleLocation must not be null");

        Instant articleCreatedAt = Instant.now();
        Instant articleExpiredAt = articleRetentionPolicy.getExpiredAt(articleCreatedAt);

        Article article = new Article();
        article.setAccount(account);
        article.setArticleContent(articleContent);
        article.setArticleCreatedAt(articleCreatedAt);
        article.setArticleExpiredAt(articleExpiredAt);
        article.setArticleLastModifiedAt(articleCreatedAt);
        article.setArticleDeletedAt(null);
        article.setArticleLocation(articleLocation);
        return article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        // articleId가 null 이면 비교할 수 없음
        if (getArticleId() == null || article.getArticleId() == null) {
            return false;
        }
        return Objects.equals(getArticleId(), article.getArticleId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getArticleId());
    }


    @Override
    public boolean isExpired() {
        if (articleExpiredAt == null) {
            return false;
        }
        return Instant.now().isAfter(articleExpiredAt);
    }
}

//CREATE TABLE article (
//    article_id BIGSERIAL PRIMARY KEY,
//    user_id BIGINT NOT NULL,
//    article_content TEXT NOT NULL,
//    article_created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
//    -- 기본 값은 1시간 뒤
//    article_expired_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP + INTERVAL '1 hour',
//    article_last_modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
//    article_deleted_at TIMESTAMP DEFAULT NULL,
//    article_location GEOMETRY NOT NULL,
//    FOREIGN KEY (user_id) REFERENCES user (user_id)
//);