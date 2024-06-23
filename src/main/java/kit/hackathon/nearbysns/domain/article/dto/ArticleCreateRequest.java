package kit.hackathon.nearbysns.domain.article.dto;

public record ArticleCreateRequest(
        String content,
        Double latitude,
        Double longitude
) {
}
