package kit.hackathon.nearbysns.domain.preference.dto;

import kit.hackathon.nearbysns.domain.preference.entity.PreferenceType;

public record PreferenceDetailResponse(
        Long accountId,
        PreferenceType preferenceType
) {
}
