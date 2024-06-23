package kit.hackathon.nearbysns.domain.account.service;

import kit.hackathon.nearbysns.domain.account.dto.request.AccountRegisterRequestDTO;

public interface AccountService {

    void register(AccountRegisterRequestDTO accountRegisterRequestDTO);
}
