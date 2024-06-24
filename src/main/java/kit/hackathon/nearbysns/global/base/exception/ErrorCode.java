package kit.hackathon.nearbysns.global.base.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    // ARTICLE
    ARTICLE_NOT_FOUND(org.springframework.http.HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),
    ARTICLE_EXPIRED(org.springframework.http.HttpStatus.BAD_REQUEST, "게시글이 만료되었습니다."),

    // COMMENT
    COMMENT_NOT_FOUND(org.springframework.http.HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    COMMENT_ON_CHILD_COMMENT(org.springframework.http.HttpStatus.BAD_REQUEST, "대댓글에는 댓글을 달 수 없습니다."),


    // ACCOUNT
    REQUESTED_ACCOUNT_NOT_FOUND(HttpStatus.BAD_REQUEST, "요청한 사용자가 존재하지 않습니다."),

    DUPLICATED_USERNAME(org.springframework.http.HttpStatus.CONFLICT, "사용자의 아이디가 중복되었습니다."),
    USER_NOT_FOUND(org.springframework.http.HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다."),
    PASSWORD_INVALID(org.springframework.http.HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다."),

    UN_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증 정보가 존재하지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}