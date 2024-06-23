package kit.hackathon.nearbysns.domain.account.dto.request;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class AccountRegisterRequestDTO {
    private String accountName;
    private String accountLoginId;
    private String accountLoginPw;

    public void updatePassword(String accountLoginPw) {
        this.accountLoginPw = accountLoginPw;
    }
}
