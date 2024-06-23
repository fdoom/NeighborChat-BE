package kit.hackathon.nearbysns.domain.comment.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

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










}
