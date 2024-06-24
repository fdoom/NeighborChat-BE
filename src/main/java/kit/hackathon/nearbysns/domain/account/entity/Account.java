package kit.hackathon.nearbysns.domain.account.entity;

import jakarta.persistence.*;
import kit.hackathon.nearbysns.domain.account.entity.role.AccountRole;
import kit.hackathon.nearbysns.global.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@Entity
@AttributeOverrides({
        @AttributeOverride(name = "createdAt", column = @Column(name = "account_created_at", nullable = false, updatable = false)),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "account_last_password_updated_at", nullable = false))
})
public class Account extends BaseEntity {

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
    @Enumerated(EnumType.ORDINAL)
    private AccountRole accountRole = AccountRole.USER;

    @Column(name = "account_deleted_at")
    private LocalDateTime accountDeletedAt;

    public void deleteNow() {
        accountDeletedAt = LocalDateTime.now();
    }
}