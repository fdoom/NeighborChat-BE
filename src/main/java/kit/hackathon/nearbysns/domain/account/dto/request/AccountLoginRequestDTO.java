package kit.hackathon.nearbysns.domain.account.dto.request;

import lombok.Getter;

@Getter
public class AccountLoginRequestDTO {
    private String accountLoginId;
    private String accountLoginPw;
}
