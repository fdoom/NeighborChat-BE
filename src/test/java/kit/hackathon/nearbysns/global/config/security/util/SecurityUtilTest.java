package kit.hackathon.nearbysns.global.config.security.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 세션 정보를 가지고 있는 상태에서 요청 시 해당 세션의 계정에 대한 고유키 조회 예시
 */
@RestController
@RequiredArgsConstructor
public class SecurityUtilTest {
    private final SecurityUtil securityUtil;

    /**
     * 클라이언트에서 세션 정보를 가지고 전달을 받는다는 가정 하에 작성된 코드
     * @return
     */
    @GetMapping("/hello")
    public String hello() {
        System.out.println("세션 정보에 대한 계정의 고유키: " + securityUtil.getAccountId());
        return "Hello World";
    }
}
