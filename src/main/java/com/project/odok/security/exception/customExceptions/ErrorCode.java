package com.project.odok.security.exception.customExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXPIRED_TOKEN(500,"TOKEN_IS_EXPIRED","JWT토큰이 만료되었습니다."),
    NOT_FOUND_MEMBER(401,"NOT_FOUND_MEMBER","유저를 찾을 수 없습니다."),
    NOT_FOUND_CLUB(404,"NOT_FOUND_CLUB","모임을 찾을 수 없습니다."),
    NOT_VALID_WRITER(500,"NOT_VALID_WRITER","모임의 작성자가 아닙니다."),
    NOT_MATCHED_PASSWORD(401,"NOT_MATCHED_PASSWORD","비밀번호가 일치하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;
}
