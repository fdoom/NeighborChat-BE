package kit.hackathon.nearbysns.domain.preference.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kit.hackathon.nearbysns.domain.preference.dto.PreferenceDetailResponse;
import kit.hackathon.nearbysns.domain.preference.dto.PreferencePutRequest;
import kit.hackathon.nearbysns.domain.preference.dto.PreferenceSummaryResponse;
import kit.hackathon.nearbysns.domain.preference.service.PreferenceService;
import kit.hackathon.nearbysns.global.base.exception.ErrorResponse;
import kit.hackathon.nearbysns.global.config.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "선호도", description = "선호도 관련 API")
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
public class PreferenceController {
    private final PreferenceService preferenceService;
    private final SecurityUtil securityUtil;

    @GetMapping("/articles/{articleId}/preference")
    @Operation(summary = "게시글 선호도 요약", description = "게시글에 대한 선호도 요약을 가져옵니다.")
    public ResponseEntity<PreferenceSummaryResponse> getArticlePreferenceSummary(
            @Parameter(description = "게시글 ID",required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable Long articleId
    ) {
        PreferenceSummaryResponse articlePreferenceSummary = preferenceService.getArticlePreferenceSummary(articleId);
        return ResponseEntity.ok(articlePreferenceSummary);
    }

    @GetMapping("/articles/{articleId}/preference/detail")
    @Operation(summary = "게시글 선호도 상세", description = "게시글에 대한 선호도 상세를 가져옵니다.")
    public ResponseEntity<Page<PreferenceDetailResponse>> getArticlePreferenceDetail(
            @Parameter(description = "게시글 ID",required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable Long articleId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", required = false, schema = @Schema(type = "integer"), in = ParameterIn.QUERY)
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<PreferenceDetailResponse> articlePreferenceDetail = preferenceService.getArticlePreferenceDetail(articleId, pageable);
        return ResponseEntity.ok(articlePreferenceDetail);
    }

    @PutMapping("/articles/{articleId}/preference")
    @Operation(summary = "게시글 선호도 업데이트", description = "게시글에 대한 선호도를 업데이트합니다.")
    public ResponseEntity<Void> upsertPreference(
            @Parameter(description = "게시글 ID",required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable Long articleId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "선호도 요청 바디", required = true)
            @RequestBody PreferencePutRequest preferencePutRequest
    ) {
        Long accountId = securityUtil.getAccountId();
        preferenceService.upsertPreference(
                preferencePutRequest.preferenceType(),
                accountId,
                articleId
        );
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/articles/{articleId}/preference/{accountId}")
    @Operation(summary = "게시글 선호도 상세", description = "게시글에 대한 선호도 상세를 가져옵니다.")
    public ResponseEntity<PreferenceDetailResponse> getUserArticlePreference(
            @Parameter(description = "게시글 ID",required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable Long articleId,
            @Parameter(description = "사용자 ID",required = true, example = "1", schema = @Schema(type = "integer"), in = ParameterIn.PATH)
            @PathVariable Long accountId
    ) {
        PreferenceDetailResponse userArticlePreference = preferenceService.getUserArticlePreference(articleId, accountId);
        return ResponseEntity.ok(userArticlePreference);
    }



}
