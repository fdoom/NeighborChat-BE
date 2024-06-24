package kit.hackathon.nearbysns.domain.account.service;

import kit.hackathon.nearbysns.domain.account.dto.request.AccountLoginRequestDTO;
import kit.hackathon.nearbysns.domain.account.dto.request.AccountRegisterRequestDTO;

public interface AccountService {

    void register(AccountRegisterRequestDTO accountRegisterRequestDTO);

    void authenticate(AccountLoginRequestDTO accountLoginRequestDTO);
}
