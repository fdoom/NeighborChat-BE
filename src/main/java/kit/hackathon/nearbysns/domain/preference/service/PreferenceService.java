package kit.hackathon.nearbysns.domain.preference.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kit.hackathon.nearbysns.domain.preference.dto.PreferenceDetailResponse;
import kit.hackathon.nearbysns.domain.preference.dto.PreferenceSummaryResponse;
import kit.hackathon.nearbysns.domain.preference.entity.Preference;
import kit.hackathon.nearbysns.domain.preference.entity.PreferenceKey;
import kit.hackathon.nearbysns.domain.preference.entity.PreferenceType;
import kit.hackathon.nearbysns.domain.preference.repository.PreferenceRepository;
import kit.hackathon.nearbysns.global.validator.NonExpiredArticleId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Validated
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;

    @Transactional
    public void upsertPreference(
            @NotNull(message = "PreferenceType cannot be null")
            PreferenceType preferenceType,
            @NotNull(message = "accountId cannot be null") @Positive(message = "accountId must be positive")
            Long accountId,
            @NonExpiredArticleId(message = "article not found or expired")
            Long articleId) {
        PreferenceKey preferenceKey = new PreferenceKey(accountId, articleId);

        if (preferenceType == PreferenceType.NONE) {
            preferenceRepository.deleteById(preferenceKey);
        } else {
            Preference preference = new Preference(preferenceKey, preferenceType);
            preferenceRepository.upsert(preference);
        }
    }

    public PreferenceSummaryResponse getArticlePreferenceSummary(
            @NonExpiredArticleId(message = "article not found or expired")
            Long articleId
    ) {
        return preferenceRepository.getPreferenceSummary(articleId);
    }

    public Page<PreferenceDetailResponse> getArticlePreferenceDetail(
            @NonExpiredArticleId(message = "article not found or expired")
            Long articleId,
            Pageable pageable
    ) {
        return preferenceRepository.getPreferenceDetail(articleId, pageable);
    }

    public PreferenceDetailResponse getUserArticlePreference(
            @NotNull(message = "accountId cannot be null") @Positive(message = "accountId must be positive")
            Long accountId,
            @NonExpiredArticleId(message = "article not found or expired")
            Long articleId
    ) {
        PreferenceType preferenceType = preferenceRepository.findById(new PreferenceKey(accountId, articleId))
                .map(Preference::getPreferenceType)
                .orElse(PreferenceType.NONE);
        return new PreferenceDetailResponse(accountId, preferenceType);
    }

}
