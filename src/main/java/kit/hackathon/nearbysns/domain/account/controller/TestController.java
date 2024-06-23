package kit.hackathon.nearbysns.domain.account.controller;

import kit.hackathon.nearbysns.global.config.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 세션 정보를 가지고 있는 상태에서 요청 시 해당 세션의 계정에 대한 고유키 조회 예시
 */
@RestController
@RequiredArgsConstructor
public class TestController {
    private final SecurityUtil securityUtil;

    @GetMapping("/hello")
    public String hello() {
        System.out.println("세션 정보에 대한 계정의 고유키: " + securityUtil.getAccountId());
        return "Hello World";
    }
}
