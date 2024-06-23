package kit.hackathon.nearbysns.domain.article.repository;

import kit.hackathon.nearbysns.domain.article.entity.Article;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface ArticleRepository extends Repository<Long, Article> {
    Article save(Article article);
}
