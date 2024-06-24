package kit.hackathon.nearbysns.domain.account.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kit.hackathon.nearbysns.domain.account.dto.request.AccountDeleteRequestDTO;
import kit.hackathon.nearbysns.domain.account.dto.request.AccountLoginRequestDTO;
import kit.hackathon.nearbysns.domain.account.dto.request.AccountRegisterRequestDTO;
import kit.hackathon.nearbysns.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody AccountRegisterRequestDTO accountRegisterRequestDTO) {
        accountService.register(accountRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody AccountLoginRequestDTO accountLoginRequestDTO, HttpSession session) {
        accountService.authenticate(accountLoginRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@Valid @RequestBody AccountDeleteRequestDTO accountDeleteRequestDTO) {
        accountService.delete(accountDeleteRequestDTO);
        URI redirectUri = URI.create("/account/logout");
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(redirectUri).build();
    }
}
