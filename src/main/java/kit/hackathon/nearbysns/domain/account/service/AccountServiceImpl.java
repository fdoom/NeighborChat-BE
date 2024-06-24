package kit.hackathon.nearbysns.domain.account.service;

import jakarta.servlet.http.HttpSession;
import kit.hackathon.nearbysns.domain.account.dto.request.AccountLoginRequestDTO;
import kit.hackathon.nearbysns.domain.account.dto.request.AccountRegisterRequestDTO;
import kit.hackathon.nearbysns.domain.account.entity.Account;
import kit.hackathon.nearbysns.domain.account.repository.AccountRepository;
import kit.hackathon.nearbysns.global.base.exception.CustomException;
import kit.hackathon.nearbysns.global.base.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final HttpSession httpSession;

    @Override
    public void register(AccountRegisterRequestDTO accountRegisterRequestDTO) {
        accountRepository.findByAccountLoginId(accountRegisterRequestDTO.getAccountLoginId())
                .ifPresent(it -> {
                    throw new CustomException(ErrorCode.DUPLICATED_USERNAME);
                });
        accountRegisterRequestDTO.updatePassword(passwordEncoder.encode(accountRegisterRequestDTO.getAccountLoginPw()));
        Account account = modelMapper.map(accountRegisterRequestDTO, Account.class);
        accountRepository.save(account);
    }

    @Override
    public void authenticate(AccountLoginRequestDTO accountLoginRequestDTO) {
        Account account = accountRepository.findByAccountLoginId(accountLoginRequestDTO.getAccountLoginId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(accountLoginRequestDTO.getAccountLoginPw(), account.getAccountLoginPw())) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(accountLoginRequestDTO.getAccountLoginId(), accountLoginRequestDTO.getAccountLoginPw());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        httpSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }
}
