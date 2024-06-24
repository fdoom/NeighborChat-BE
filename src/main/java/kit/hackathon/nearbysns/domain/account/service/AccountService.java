package kit.hackathon.nearbysns.domain.account.service;

import kit.hackathon.nearbysns.domain.account.dto.request.*;
import kit.hackathon.nearbysns.domain.account.dto.response.AccountUpdatedNameResponseDTO;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    void register(AccountRegisterRequestDTO accountRegisterRequestDTO);

    void authenticate(AccountLoginRequestDTO accountLoginRequestDTO);

    void delete(AccountDeleteRequestDTO accountDeleteRequestDTO);

    ResponseEntity<AccountUpdatedNameResponseDTO> updateName(AccountUpdateNameRequestDTO accountUpdateNameRequestDTO);

    void updatePassword(AccountUpdateLoginPasswordRequestDTO accountUpdateLoginPasswordRequestDTO);
}
