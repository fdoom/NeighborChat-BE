package kit.hackathon.nearbysns.domain.preference.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kit.hackathon.nearbysns.domain.preference.entity.PreferenceType;

@Schema(
        name = "PreferenceDetailResponse",
        description = "선호도 상세 응답",
        example = "{\n" +
                "  \"accountId\": 0,\n" +
                "  \"preferenceType\": \"string\"\n" +
                "}"
)
public record PreferenceDetailResponse(
        @Schema(
                name = "accountId",
                description = "계정 ID",
                example = "0",
                required = true
        )
        Long accountId,
        @Schema(
                name = "preferenceType",
                description = "선호도 타입",
                example = "string",
                allowableValues = {"LIKE", "DISLIKE", "NONE"},
                required = true
        )
        PreferenceType preferenceType
) {
}
