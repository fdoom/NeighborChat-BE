package kit.hackathon.nearbysns.global.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_USERNAME(HttpStatus.CONFLICT, "Username이 중복되었습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다."),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다."),

    UN_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증 정보가 존재하지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    ;

    private HttpStatus status;
    private String message;
}