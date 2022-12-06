package com.project.odok.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_MATCHED_PASSWORD(HttpStatus.BAD_REQUEST,"NOT_MATCHED_PASSWORD","비밀번호가 일치하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"NOT_VALID_TOKEN","권한 정보가 없는 token 입니다."),
    INVALID_WRITER(HttpStatus.FORBIDDEN,"NOT_VALID_WRITER","모임의 작성자가 아닙니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND,"NOT_FOUND_MEMBER","해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND,"NOT_FOUND_EMAIL","해당 이메일을 찾을 수 없습니다."),
    NOT_FOUND_CLUB(HttpStatus.NOT_FOUND,"NOT_FOUND_CLUB","해당 모임을 찾을 수 없습니다."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND,"NOT_FOUND_REVIEW","해당 후기를 찾을 수 없습니다."),
    NOT_FOUND_CHATROOM(HttpStatus.NOT_FOUND,"NOT_FOUND_CHATROOM","해당 채팅방을 찾을 수 없습니다."),
    UPLOAD_FAIL(HttpStatus.UNSUPPORTED_MEDIA_TYPE,"UPLOAD_FAIL","파일 업로드에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}