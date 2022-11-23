package com.project.odok.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_ID(400,"DUPLICATED_ID","사용중인 아이디입니다."),
    DUPLICATED_EMAIL(400,"DUPLICATED_EMAIL","사용중인 이메일입니다."),
    INVALID_TOKEN(401,"NOT_VALID_TOKEN","권한 정보가 없는 token 입니다."),
    NOT_MATCHED_PASSWORD(401,"NOT_MATCHED_PASSWORD","비밀번호가 일치하지 않습니다."),
    INVALID_WRITER(403,"NOT_VALID_WRITER","모임의 작성자가 아닙니다."),
    NOT_FOUND_MEMBER(404,"NOT_FOUND_MEMBER","유저를 찾을 수 없습니다."),
    NOT_FOUND_CLUB(404,"NOT_FOUND_CLUB","모임을 찾을 수 없습니다."),
    UPLOAD_FAIL(415,"UPLOAD_FAIL","파일 업로드에 실패하였습니다.");

    private final Integer status;
    private final String code;
    private final String message;
}
