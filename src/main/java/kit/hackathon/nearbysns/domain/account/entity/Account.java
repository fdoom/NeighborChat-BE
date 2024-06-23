package kit.hackathon.nearbysns.domain.account.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "account_name", nullable = false, length = 20)
    private String accountName;

    @Column(name = "account_login_id", nullable = false, unique = true, length = 20)
    private String accountLoginId;

    @Column(name = "account_login_pw", nullable = false, length = 254)
    private String accountLoginPw;

    @Column(name = "account_role", nullable = false)
    private Short accountRole = 0;

    @Column(name = "account_created_at", nullable = false)
    private LocalDateTime accountCreatedAt = LocalDateTime.now();

    @Column(name = "account_last_password_updated_at", nullable = false)
    private LocalDateTime accountLastPasswordUpdatedAt = LocalDateTime.now();

    @Column(name = "account_deleted_at")
    private LocalDateTime accountDeletedAt;
}
