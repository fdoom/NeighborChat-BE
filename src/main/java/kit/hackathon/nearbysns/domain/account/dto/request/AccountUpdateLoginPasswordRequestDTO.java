package kit.hackathon.nearbysns.domain.account.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AccountUpdateLoginPasswordRequestDTO {
    @NotBlank(message = "사용자 계정에 대한 현재 비밀번호는 빈칸이 아니여야 합니다.")
    private String currentPw;

    @NotBlank(message = "사용자 계정에 대한 변경할 비밀번호는 빈칸이 아니여야 합니다.")
    private String newPw;
}
