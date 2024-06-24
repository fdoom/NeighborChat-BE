package kit.hackathon.nearbysns.domain.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class AccountRegisterRequestDTO {
    @NotBlank(message = "사용자 계정에 대한 이름은 빈칸이 아니여야 합니다.")
    private String accountName;

    @NotBlank(message = "사용자 계정에 대한 로그인 아이디는 빈칸이 아니여야 합니다.")
    private String accountLoginId;

    @NotBlank(message = "사용자 계정에 대한 로그인 비밀번호는 빈칸이 아니여야 합니다.")
    private String accountLoginPw;

    public void updatePassword(String accountLoginPw) {
        this.accountLoginPw = accountLoginPw;
    }
}
