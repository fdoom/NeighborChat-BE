package kit.hackathon.nearbysns.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kit.hackathon.nearbysns.domain.comment.entity.Comment;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CommentResponse(
        Long commentId,
        Long articleId,
        Long authorId,
        Long parentCommentId,
        Boolean hasChildComments,
        Boolean isSubComment,
        String commentContent,
        Instant commentCreatedAt,
        Instant commentLastModifiedAt,
        Instant commentDeletedAt
) {
    public static CommentResponse of(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getArticle().getArticleId(),
                comment.getAccount().getAccountId(),
                comment.getParentComment() == null ? null : comment.getParentComment().getId(),
                !comment.getChildComments().isEmpty(),
                comment.getParentComment() != null,
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getLastModifiedAt(),
                comment.getDeletedAt()
        );
    }
}
