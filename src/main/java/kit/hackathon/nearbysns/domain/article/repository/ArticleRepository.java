package kit.hackathon.nearbysns.domain.article.repository;

import kit.hackathon.nearbysns.domain.article.entity.Article;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface ArticleRepository extends Repository<Article,Long> {
    Article save(Article article);

    @Query("""
            select a
            from Article a
            where a.articleDeletedAt is null
            and a.articleExpiredAt > current_timestamp
            and distance(a.articleLocation, :point) < :meters
            and a.articleCreatedAt > :after
            order by a.articleCreatedAt desc
            limit :size
            """)
    List<Article> findAllPagedArticleNearby(
            @Param("point") Point point,
            @Param("meters") Double meters,
            @Param("after") Instant after,
            @Param("size") Integer size
    );

    @Query("""
            select a
            from Article a
            where a.articleId = :articleId
            and a.articleDeletedAt is null
            and a.articleExpiredAt > current_timestamp
            """)
    Optional<Article> findArticleById(@Param("articleId") Long articleId);

    Optional<Article> findByArticleId(Long articleId);
}
