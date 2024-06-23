package kit.hackathon.nearbysns.domain.account.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountLoginResponseDTO {
    private String sessionId;
    private String accountLoginId;
    private String accountRole;
}
