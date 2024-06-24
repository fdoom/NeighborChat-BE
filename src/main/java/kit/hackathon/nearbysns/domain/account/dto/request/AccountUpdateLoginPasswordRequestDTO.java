package kit.hackathon.nearbysns.domain.account.dto.request;

import lombok.Getter;

@Getter
public class AccountUpdateLoginPasswordRequestDTO {
    private String currentPw;
    private String newPw;
}
