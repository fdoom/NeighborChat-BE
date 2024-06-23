package kit.hackathon.nearbysns.global.config.security;

import kit.hackathon.nearbysns.domain.account.entity.Account;
import kit.hackathon.nearbysns.domain.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceConfig implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Account> optionalAccount = accountRepository.findByAccountLoginId(username);
        if (optionalAccount.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        Account account = optionalAccount.get();
        return User
                .withUsername(account.getAccountLoginId())
                .password(account.getAccountLoginPw())
                .authorities(account.getAccountRole().toString())
                .build();
    }
}