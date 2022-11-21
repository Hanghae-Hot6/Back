package com.project.odok.security.exception;

import com.project.odok.dto.ResponseDto;
import com.project.odok.security.exception.customExceptions.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseDto<Error> handleExpiredTokenException(){
        return ResponseDto.fail(new Error(ErrorCode.EXPIRED_TOKEN.getStatus(), ErrorCode.EXPIRED_TOKEN.getCode(), ErrorCode.EXPIRED_TOKEN.getMessage()));
    }

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseDto<Error> handleNotFoundMemberException(){
        return ResponseDto.fail(new Error(ErrorCode.NOT_FOUND_MEMBER.getStatus(), ErrorCode.NOT_FOUND_MEMBER.getCode(), ErrorCode.NOT_FOUND_MEMBER.getMessage()));
    }

    @ExceptionHandler(NotFoundClubException.class)
    public ResponseDto<Error> handleNotFoundClubException(){
        return ResponseDto.fail(new Error(ErrorCode.NOT_FOUND_CLUB.getStatus(), ErrorCode.NOT_FOUND_CLUB.getCode(), ErrorCode.NOT_FOUND_CLUB.getMessage()));
    }

    @ExceptionHandler(NotValidWriterException.class)
    public ResponseDto<Error> handleNotValidWriterException(){
        return ResponseDto.fail(new Error(ErrorCode.NOT_VALID_WRITER.getStatus(), ErrorCode.NOT_VALID_WRITER.getCode(), ErrorCode.NOT_VALID_WRITER.getMessage()));
    }

    @ExceptionHandler(NotMatchedPasswordException.class)
    public ResponseDto<Error> handleNotMatchedPasswordException(){
        return ResponseDto.fail(new Error(ErrorCode.NOT_MATCHED_PASSWORD.getStatus(), ErrorCode.NOT_MATCHED_PASSWORD.getCode(), ErrorCode.NOT_MATCHED_PASSWORD.getMessage()));
    }
}
