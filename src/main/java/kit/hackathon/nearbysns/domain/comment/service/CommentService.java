package kit.hackathon.nearbysns.domain.comment.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kit.hackathon.nearbysns.domain.account.entity.Account;
import kit.hackathon.nearbysns.domain.account.repository.AccountRepository;
import kit.hackathon.nearbysns.domain.article.entity.Article;
import kit.hackathon.nearbysns.domain.article.repository.ArticleRepository;
import kit.hackathon.nearbysns.domain.comment.dto.CommentResponse;
import kit.hackathon.nearbysns.domain.comment.entity.Comment;
import kit.hackathon.nearbysns.domain.comment.repository.CommentRepository;
import kit.hackathon.nearbysns.global.base.exception.CustomException;
import kit.hackathon.nearbysns.global.base.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;

@Service
@Validated
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final AccountRepository accountRepository;

    public Page<CommentResponse> getParentComments(
            @NotNull(message = "articleId is required") @Positive(message = "articleId must be positive")
            Long articleId,
            @NotNull(message = "pageable is required")
            Pageable pageable
    ) {
        Article article = articleRepository.findArticleById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        Page<Comment> comments = commentRepository.findParentCommentsByArticle(articleId, pageable);
        return comments.map(CommentResponse::of);
    }

    public Page<CommentResponse> getChildComments(
            @NotNull(message = "articleId is required") @Positive(message = "articleId must be positive")
            Long articleId,
            @NotNull(message = "parentCommentId is required") @Positive(message = "parentCommentId must be positive")
            Long parentCommentId,
            @NotNull(message = "pageable is required")
            Pageable pageable
    ) {
        Page<Comment> comments = commentRepository.findChildCommentsByParentComment(parentCommentId, pageable);
        return comments.map(CommentResponse::of);
    }

    @Transactional
    public CommentResponse postComment(
            @NotNull(message = "articleId is required") @Positive(message = "articleId must be positive")
            Long articleId,
            @NotNull(message = "authorId is required") @Positive(message = "authorId must be positive")
            Long authorId,
            @NotBlank(message = "commentContent is required")
            String commentContent,
            Long parentCommentId
    ) {
        Article article = articleRepository.findArticleById(articleId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
        Account account = accountRepository.findById(authorId)
                .orElseThrow(() -> new CustomException(ErrorCode.REQUESTED_ACCOUNT_NOT_FOUND));
        Comment parentComment = null;
        if (parentCommentId != null) {
            parentComment = commentRepository.findByIdAndDeletedAtIsNull(parentCommentId)
                    .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
            if (parentComment.getParentComment() != null) {
                throw new CustomException(ErrorCode.COMMENT_ON_CHILD_COMMENT);
            }
        }

        Comment created = Comment.createNew(account, article, parentComment, commentContent);
        Comment saved = commentRepository.save(created);
        return CommentResponse.of(saved);
    }

    public void deleteComment(
            @NotNull(message = "commentId is required") @Positive(message = "commentId must be positive")
            Long commentId,
            @NotNull(message = "authorId is required") @Positive(message = "authorId must be positive")
            Long authorId
    ) {
        Comment comment = commentRepository.findByIdAndDeletedAtIsNull(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        if (!comment.getAccount().getAccountId().equals(authorId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        comment.setDeletedAt(Instant.now());
        commentRepository.save(comment);
    }

    @Transactional
    public CommentResponse updateComment(
            @NotNull(message = "commentId is required") @Positive(message = "commentId must be positive")
            Long commentId,
            @NotNull(message = "userId is required") @Positive(message = "userId must be positive")
            Long userId,
            @NotBlank(message = "commentContent is required")
            String commentContent
    ) {
        Comment comment = commentRepository.findByIdAndDeletedAtIsNull(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        if (!comment.getAccount().getAccountId().equals(userId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        comment.setContent(commentContent);
        Comment saved = commentRepository.save(comment);
        return CommentResponse.of(saved);
    }

}
