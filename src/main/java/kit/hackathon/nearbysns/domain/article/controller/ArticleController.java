package kit.hackathon.nearbysns.domain.article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.hackathon.nearbysns.domain.article.dto.ArticleCreateRequest;
import kit.hackathon.nearbysns.domain.article.dto.ArticlePagedResponse;
import kit.hackathon.nearbysns.domain.article.dto.ArticlePutRequest;
import kit.hackathon.nearbysns.domain.article.dto.ArticleResponse;
import kit.hackathon.nearbysns.domain.article.entity.Article;
import kit.hackathon.nearbysns.domain.article.service.ArticleService;
import kit.hackathon.nearbysns.global.base.exception.ErrorResponse;
import kit.hackathon.nearbysns.global.config.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;

@Tag(name = "게시글", description = "게시글 관련 API")
@RestController
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "200",description = "성공"),
        @ApiResponse(responseCode = "204",description = "성공"),
        @ApiResponse(responseCode = "404",description = "리소스가 존재하지 않음",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "400", description = "빈칸이 존재하거나 잘못된 요청을 했습니다.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401", description = "인증 정보 없음",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "권한 없음",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
})
public class ArticleController {
    private final ArticleService articleService;
    private final SecurityUtil securityUtil;

    @PostMapping("/articles")
    @Operation(summary = "게시글 작성", description = "게시글을 작성합니다.")
    public ResponseEntity<ArticleResponse> createArticle(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "게시글 내용, 위도, 경도", required = true)
            @RequestBody(required = true) ArticleCreateRequest request
    ) {
        Long userId = securityUtil.getAccountId();
        ArticleResponse articleResponse = articleService.postArticle(
                userId,
                request.content(),
                request.latitude(),
                request.longitude()
        );
        return ResponseEntity.ok(articleResponse);
    }

    @GetMapping("/articles")
    @Operation(summary = "게시글 목록 조회", description = "게시글 목록을 조회합니다.")
    public ResponseEntity<ArticlePagedResponse> getArticles(
            @Parameter(description = "경도", required = true, example = "127.123456", schema = @Schema(type = "number"), in = ParameterIn.QUERY)
            @RequestParam(name = "longitude", required = true) Double longitude,
            @Parameter(description = "위도", required = true, example = "37.123456", schema = @Schema(type = "number"), in = ParameterIn.QUERY)
            @RequestParam(name = "latitude", required = true) Double latitude,
            @Parameter(description = "반경", required = false, example = "300", schema = @Schema(type = "number"), in = ParameterIn.QUERY)
            @RequestParam(name = "meters", required = false, defaultValue = "300") Double meters,
            @Parameter(description = "페이지 정보", required = false, schema = @Schema(type = "integer"), in = ParameterIn.QUERY)
            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size,
            @Parameter(description = "시간 범위 제한(이 시간 이후 메시지만 조회)", required = false, schema = @Schema(type = "integer"), in = ParameterIn.QUERY)
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
    @Operation(summary = "게시글 조회", description = "게시글을 조회합니다.")
    public ResponseEntity<ArticleResponse> getArticle(
            @Parameter(description = "게시글 ID", required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable(name = "articleId") Long articleId
    ) {
        ArticleResponse articleResponse = articleService.getArticle(articleId);
        return ResponseEntity.ok(articleResponse);
    }

    @DeleteMapping("/articles/{articleId}")
    @Operation(summary = "게시글 삭제", description = "게시글을 삭제합니다.")
    public ResponseEntity<Void> deleteArticle(
            @Parameter(description = "게시글 ID", required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable(name = "articleId") Long articleId
    ) {
        Long userId = securityUtil.getAccountId();
        articleService.deleteArticle(articleId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/articles/{articleId}")
    @Operation(summary = "게시글 수정", description = "게시글을 수정합니다.")
    public ResponseEntity<ArticleResponse> updateArticle(
            @Parameter(description = "게시글 ID", required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable(name = "articleId") Long articleId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "게시글 수정 요청", required = true, content = @Content(schema = @Schema(implementation = ArticlePutRequest.class)))
            @RequestBody(required = true) ArticlePutRequest request
            ) {
        Long userId = securityUtil.getAccountId();
        articleService.updateArticle(
                articleId,
                userId,
                request.content()
        );
        return ResponseEntity.noContent().build();
    }

}
