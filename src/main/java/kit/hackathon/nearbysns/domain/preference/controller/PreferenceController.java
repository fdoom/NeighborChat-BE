package kit.hackathon.nearbysns.domain.preference.controller;

import kit.hackathon.nearbysns.domain.preference.dto.PreferenceDetailResponse;
import kit.hackathon.nearbysns.domain.preference.dto.PreferencePutRequest;
import kit.hackathon.nearbysns.domain.preference.dto.PreferenceSummaryResponse;
import kit.hackathon.nearbysns.domain.preference.service.PreferenceService;
import kit.hackathon.nearbysns.global.config.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PreferenceController {
    private final PreferenceService preferenceService;
    private final SecurityUtil securityUtil;

    @GetMapping("/articles/{articleId}/preference")
    public ResponseEntity<PreferenceSummaryResponse> getArticlePreferenceSummary(
            @PathVariable Long articleId
    ) {
        PreferenceSummaryResponse articlePreferenceSummary = preferenceService.getArticlePreferenceSummary(articleId);
        return ResponseEntity.ok(articlePreferenceSummary);
    }

    @GetMapping("/articles/{articleId}/preference/detail")
    public ResponseEntity<Page<PreferenceDetailResponse>> getArticlePreferenceDetail(
            @PathVariable Long articleId
    ) {
        Page<PreferenceDetailResponse> articlePreferenceDetail = preferenceService.getArticlePreferenceDetail(articleId);
        return ResponseEntity.ok(articlePreferenceDetail);
    }

    @PutMapping("/articles/{articleId}/preference")
    public ResponseEntity<Void> upsertPreference(
            @PathVariable Long articleId,
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
    public ResponseEntity<PreferenceDetailResponse> getUserArticlePreference(
            @PathVariable Long articleId,
            @PathVariable Long accountId
    ) {
        PreferenceDetailResponse userArticlePreference = preferenceService.getUserArticlePreference(articleId, accountId);
        return ResponseEntity.ok(userArticlePreference);
    }



}
