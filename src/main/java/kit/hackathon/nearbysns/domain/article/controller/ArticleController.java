package kit.hackathon.nearbysns.domain.article.controller;

import kit.hackathon.nearbysns.domain.article.dto.ArticleCreateRequest;
import kit.hackathon.nearbysns.domain.article.dto.ArticlePagedResponse;
import kit.hackathon.nearbysns.domain.article.dto.ArticleResponse;
import kit.hackathon.nearbysns.domain.article.entity.Article;
import kit.hackathon.nearbysns.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> createArticle(
            @RequestBody(required = true) ArticleCreateRequest request
    ) {
        //TODO : 추후 인증 완성되면 변경
        Long userId = 1L;
        ArticleResponse articleResponse = articleService.postArticle(
                userId,
                request.content(),
                request.latitude(),
                request.longitude()
        );
        return ResponseEntity.ok(articleResponse);
    }

    @GetMapping("/articles")
    public ResponseEntity<ArticlePagedResponse> getArticles(
            @RequestParam(name = "longitude", required = true) Double longitude,
            @RequestParam(name = "latitude", required = true) Double latitude,
            @RequestParam(name = "meters", required = false, defaultValue = "300") Double meters,
            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size,
            @RequestParam(name = "after", required = false) Long afterUnixEpochSeconds
            ) {

        Instant after = afterUnixEpochSeconds == null ? Instant.now().minus(Duration.ofHours(1)) : Instant.ofEpochSecond(afterUnixEpochSeconds);
        ArticlePagedResponse articlePagedResponse = articleService.getArticles(
                latitude,
                longitude,
                meters,
                after,
                size
        );
        return ResponseEntity.ok(articlePagedResponse);
    }

    @GetMapping("/articles/{articleId}")
    public ResponseEntity<ArticleResponse> getArticle(
            @PathVariable(name = "articleId") Long articleId
    ) {
        ArticleResponse articleResponse = articleService.getArticle(articleId);
        return ResponseEntity.ok(articleResponse);
    }

}
