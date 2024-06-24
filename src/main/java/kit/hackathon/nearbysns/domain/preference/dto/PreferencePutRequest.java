package kit.hackathon.nearbysns.domain.preference.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kit.hackathon.nearbysns.domain.preference.entity.PreferenceType;

@Schema(
        name = "PreferencePutRequest",
        description = "선호도 수정 요청",
        example = "{\n" +
                "  \"articleId\": 0,\n" +
                "  \"preferenceType\": \"string\"\n" +
                "}"
)
public record PreferencePutRequest(
        @Schema(
                name = "articleId",
                description = "게시글 ID",
                example = "0",
                required = true
        )
        Long articleId,
        @Schema(
                name = "preferenceType",
                description = "선호도 타입",
                example = "LIKE",
                allowableValues = {"LIKE", "DISLIKE", "NONE"},
                required = true
        )
        PreferenceType preferenceType
) {
}
