package kit.hackathon.nearbysns.domain.article.controller;

import kit.hackathon.nearbysns.domain.article.dto.ArticleCreateRequest;
import kit.hackathon.nearbysns.domain.article.dto.ArticleCreatedResponse;
import kit.hackathon.nearbysns.domain.article.entity.Article;
import kit.hackathon.nearbysns.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/article")
    public ResponseEntity<ArticleCreatedResponse> createArticle(
            @RequestBody(required = true) ArticleCreateRequest request
    ) {
        //TODO : 추후 인증 완성되면 변경
        Long userId = 1L;
        ArticleCreatedResponse articleCreatedResponse = articleService.postArticle(
                userId,
                request.content(),
                request.latitude(),
                request.longitude()
        );
        return ResponseEntity.ok(articleCreatedResponse);
    }

}
