package kit.hackathon.nearbysns.domain.account.repository;

import kit.hackathon.nearbysns.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository <Account, Long> {
    Optional<Account> findByAccountLoginId(String accountLoginId);
}
