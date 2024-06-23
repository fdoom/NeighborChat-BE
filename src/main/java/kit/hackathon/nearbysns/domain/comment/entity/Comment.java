package kit.hackathon.nearbysns.domain.comment.entity;

import jakarta.persistence.*;
import kit.hackathon.nearbysns.domain.account.entity.Account;
import kit.hackathon.nearbysns.domain.article.entity.Article;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_comment")
    private Comment parentComment;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "parentComment", orphanRemoval = true)
    private List<Comment> childComments = new ArrayList<>();

    @CreatedDate
    @Column(name = "comment_created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "comment_last_modified_at")
    private Instant lastModifiedAt;

    @Column(name = "comment_deleted_at")
    private Instant deletedAt;

    @Column(name = "comment_content")
    private String content;

    public static Comment createNew(Account account, Article article, Comment parentComment, String content) {
        Comment comment = new Comment();
        comment.setAccount(account);
        comment.setParentComment(parentComment);
        comment.setArticle(article);
        comment.setContent(content);
        return comment;
    }


}
