package kit.hackathon.nearbysns.domain.preference.dto;

import kit.hackathon.nearbysns.domain.preference.entity.PreferenceType;

public record PreferencePutRequest(
        Long articleId,
        PreferenceType preferenceType
) {
}
