package kit.hackathon.nearbysns.domain.account.controller;

import kit.hackathon.nearbysns.domain.account.dto.request.AccountRegisterRequestDTO;
import kit.hackathon.nearbysns.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody AccountRegisterRequestDTO accountRegisterRequestDTO) {
        accountService.register(accountRegisterRequestDTO);
        return ResponseEntity.ok().build();
    }
}
