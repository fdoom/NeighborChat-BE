package kit.hackathon.nearbysns.domain.article.dto;
//swagger annotation
@io.swagger.v3.oas.annotations.media.Schema(
        name = "ArticleCreateRequest",
        description = "게시글 작성 요청",
        example = "{\n" +
                "  \"content\": \"string\",\n" +
                "  \"latitude\": 0,\n" +
                "  \"longitude\": 0\n" +
                "}"
)
public record ArticleCreateRequest(
        @io.swagger.v3.oas.annotations.media.Schema(
                name = "content",
                description = "게시글 내용",
                example = "string",
                required = true
        )
        String content,
        @io.swagger.v3.oas.annotations.media.Schema(
                name = "latitude",
                description = "위도",
                example = "0",
                required = true
        )
        Double latitude,
        @io.swagger.v3.oas.annotations.media.Schema(
                name = "longitude",
                description = "경도",
                example = "0",
                required = true
        )
        Double longitude
) {
}
