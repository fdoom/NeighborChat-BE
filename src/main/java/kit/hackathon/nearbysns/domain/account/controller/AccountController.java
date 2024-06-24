package kit.hackathon.nearbysns.domain.account.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kit.hackathon.nearbysns.domain.account.dto.request.*;
import kit.hackathon.nearbysns.domain.account.dto.response.AccountUpdatedNameResponseDTO;
import kit.hackathon.nearbysns.domain.account.service.AccountService;
import kit.hackathon.nearbysns.global.base.exception.ErrorCode;
import kit.hackathon.nearbysns.global.base.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "빈칸이 존재하거나 잘못된 요청을 했습니다.",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "세션 정보가 존재하지 않습니다.",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "해당 내용이 존재하지 않습니다.",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
})
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/register")
    @Operation(summary = "사용자 계정 회원 가입",
    description = "요청 = 사용자 계정 이름: accountName, 사용자 계정 아이디: accountLoginId, 사용자 계정 비밀번호: accountLoginPw")
    @ApiResponse(responseCode = "409", description = "사용자 계정 아이디가 이미 존재하여 중복되었습니다.",
    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> register(@Valid @RequestBody AccountRegisterRequestDTO accountRegisterRequestDTO) {
        accountService.register(accountRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(summary = "사용자 계정 로그인",
    description = "요청 = 사용자 계정 아이디: accountLoginId, 사용자 계정 비밀번호: accountLoginPw")
    public ResponseEntity<Void> login(@Valid @RequestBody AccountLoginRequestDTO accountLoginRequestDTO, HttpSession session) {
        accountService.authenticate(accountLoginRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "사용자 계정 삭제",
    description = """
            요청 = 사용자 계정 비밀번호: accountLoginPw \n
            삭제 방식은 소프트 삭제 방식(실질적으로 DB에서 삭제하지 않고 삭제한 계정으로 취급) \n
            삭제 작업 이후 리디렉션을 통해 로그아웃하여 세션 정보 제거 (재로그인 필요)
            """)
    public ResponseEntity<Void> delete(@Valid @RequestBody AccountDeleteRequestDTO accountDeleteRequestDTO) {
        accountService.delete(accountDeleteRequestDTO);
        URI redirectUri = URI.create("/account/logout");
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(redirectUri).build();
    }

    @PatchMapping("/update/accountName")
    @Operation(summary = "사용자 계정의 이름 변경",
    description = """
            요청 = 사용자 계정 이름: accountName, 사용자 계정 비밀번호: accountLoginPw \n
            비밀번호 검증을 통해 사용자가 요청한 것인지 확인 후 사용자 계정의 이름 변경 \n
            응답 = 사용자 계정 이름: accountName
            """)
    public ResponseEntity<AccountUpdatedNameResponseDTO> updateAccountName(@Valid @RequestBody AccountUpdateNameRequestDTO accountUpdateNameRequestDTO) {
        return accountService.updateName(accountUpdateNameRequestDTO);
    }

    @PatchMapping("/update/accountLoginPw")
    @Operation(summary = "사용자의 비밀번호 변경",
    description = """
            요청 = 현재 비밀번호: currentPw, 변경할 비밀번호: newPw \n
            비밀번호 검증을 통해 사용자가 요청한 것인지 확인 후 사용자 계정의 비밀번호 변경 \n
            비밀번호 변경 이후 리디렉션을 통해 로그아웃하여 세션 정보 제거 (재로그인 필요)
            """)
    public ResponseEntity<Void> updateAccountLoginPw(@Valid @RequestBody AccountUpdateLoginPasswordRequestDTO accountUpdateLoginPasswordRequestDTO) {
        accountService.updatePassword(accountUpdateLoginPasswordRequestDTO);
        URI redirectUri = URI.create("/account/logout");
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(redirectUri).build();
    }
}
