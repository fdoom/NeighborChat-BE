package kit.hackathon.nearbysns.domain.preference.dto;

public record PreferenceSummaryResponse(
        Long articleId,
        Long likeCount,
        Long dislikeCount
) {
}
