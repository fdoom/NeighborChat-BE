package kit.hackathon.nearbysns.domain.account.service;

import kit.hackathon.nearbysns.domain.account.dto.request.*;
import kit.hackathon.nearbysns.domain.account.dto.response.AccountLoginResponseDTO;
import kit.hackathon.nearbysns.domain.account.dto.response.AccountUpdatedNameResponseDTO;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    void register(AccountRegisterRequestDTO accountRegisterRequestDTO);

    ResponseEntity<AccountLoginResponseDTO> authenticate(AccountLoginRequestDTO accountLoginRequestDTO);

    void delete(AccountDeleteRequestDTO accountDeleteRequestDTO);

    ResponseEntity<AccountUpdatedNameResponseDTO> updateName(AccountUpdateNameRequestDTO accountUpdateNameRequestDTO);

    void updatePassword(AccountUpdateLoginPasswordRequestDTO accountUpdateLoginPasswordRequestDTO);

    ResponseEntity<AccountLoginResponseDTO> whoAmI();
}
