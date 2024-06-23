package kit.hackathon.nearbysns.domain.comment.dto;

public record CommentPostRequest (
        String commentContent,
        Long parentCommentId
) {
}
