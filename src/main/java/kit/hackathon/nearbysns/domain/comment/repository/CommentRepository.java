package kit.hackathon.nearbysns.domain.comment.repository;


import kit.hackathon.nearbysns.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface CommentRepository extends Repository<Comment, Long> {
    // 특정 게시글에 대한 댓글 목록 조회
    @Query("""
            SELECT c
            FROM Comment c
            LEFT JOIN FETCH c.childComments
            WHERE c.article.articleId = :articleId
            AND c.deletedAt IS NULL
            AND c.parentComment IS NULL
            ORDER BY c.createdAt DESC
            """)
    Page<Comment> findParentCommentsByArticle(Long articleId, Pageable pageable);

    @Query("""
            SELECT c
            FROM Comment c
            LEFT JOIN FETCH c.childComments
            WHERE c.parentComment.id = :parentCommentId
            AND c.deletedAt IS NULL
            ORDER BY c.createdAt DESC
            """)
    Page<Comment> findChildCommentsByParentComment(Long parentCommentId, Pageable pageable);

    Optional<Comment> findByIdAndDeletedAtIsNull(Long id);

    Comment save(Comment comment);

}
