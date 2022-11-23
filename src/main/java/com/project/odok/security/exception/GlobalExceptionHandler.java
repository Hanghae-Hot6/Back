package com.project.odok.security.exception;

import com.project.odok.dto.ResponseDto;
import com.project.odok.security.exception.customexceptions.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicatedIdException.class)
    public ResponseDto<Error> handleDuplicatedIdException(){
        return ResponseDto.fail(new Error(ErrorCode.DUPLICATED_ID.getStatus(), ErrorCode.DUPLICATED_ID.getCode(), ErrorCode.DUPLICATED_ID.getMessage()));
    }
    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseDto<Error> handleDuplicatedEmailException(){
        return ResponseDto.fail(new Error(ErrorCode.DUPLICATED_EMAIL.getStatus(), ErrorCode.DUPLICATED_EMAIL.getCode(), ErrorCode.DUPLICATED_EMAIL.getMessage()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseDto<Error> handleInvalidTokenException(){
        return ResponseDto.fail(new Error(ErrorCode.INVALID_TOKEN.getStatus(), ErrorCode.INVALID_TOKEN.getCode(), ErrorCode.INVALID_TOKEN.getMessage()));
    }

    @ExceptionHandler(NotMatchedPasswordException.class)
    public ResponseDto<Error> handleNotMatchedPasswordException(){
        return ResponseDto.fail(new Error(ErrorCode.NOT_MATCHED_PASSWORD.getStatus(), ErrorCode.NOT_MATCHED_PASSWORD.getCode(), ErrorCode.NOT_MATCHED_PASSWORD.getMessage()));
    }

    @ExceptionHandler(InvalidWriterException.class)
    public ResponseDto<Error> handleNotValidWriterException(){
        return ResponseDto.fail(new Error(ErrorCode.INVALID_WRITER.getStatus(), ErrorCode.INVALID_WRITER.getCode(), ErrorCode.INVALID_WRITER.getMessage()));
    }

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseDto<Error> handleNotFoundMemberException(){
        return ResponseDto.fail(new Error(ErrorCode.NOT_FOUND_MEMBER.getStatus(), ErrorCode.NOT_FOUND_MEMBER.getCode(), ErrorCode.NOT_FOUND_MEMBER.getMessage()));
    }

    @ExceptionHandler(NotFoundClubException.class)
    public ResponseDto<Error> handleNotFoundClubException(){
        return ResponseDto.fail(new Error(ErrorCode.NOT_FOUND_CLUB.getStatus(), ErrorCode.NOT_FOUND_CLUB.getCode(), ErrorCode.NOT_FOUND_CLUB.getMessage()));
    }

    @ExceptionHandler(UploadFailException.class)
    public ResponseDto<Error> handleUploadFailException(){
        return ResponseDto.fail(new Error(ErrorCode.UPLOAD_FAIL.getStatus(), ErrorCode.UPLOAD_FAIL.getCode(), ErrorCode.UPLOAD_FAIL.getMessage()));
    }
}
