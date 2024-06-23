package kit.hackathon.nearbysns.domain.account.service;

import kit.hackathon.nearbysns.domain.account.dto.request.AccountLoginRequestDTO;
import kit.hackathon.nearbysns.domain.account.dto.request.AccountRegisterRequestDTO;
import kit.hackathon.nearbysns.domain.account.dto.response.AccountLoginResponseDTO;

public interface AccountService {

    void register(AccountRegisterRequestDTO accountRegisterRequestDTO);

    AccountLoginResponseDTO authenticate(AccountLoginRequestDTO accountLoginRequestDTO);
}
