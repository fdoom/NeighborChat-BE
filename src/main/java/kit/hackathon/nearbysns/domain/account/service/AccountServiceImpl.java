package kit.hackathon.nearbysns.domain.account.service;

import kit.hackathon.nearbysns.domain.account.dto.request.AccountRegisterRequestDTO;
import kit.hackathon.nearbysns.domain.account.entity.Account;
import kit.hackathon.nearbysns.domain.account.repository.AccountRepository;
import kit.hackathon.nearbysns.global.base.exception.CustomException;
import kit.hackathon.nearbysns.global.base.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
}
