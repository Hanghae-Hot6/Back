package com.project.odok.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(Exception e){
        e.printStackTrace();
        return ResponseEntity.status(ErrorCode.INVALID_TOKEN.getHttpStatus()).body(new Error(ErrorCode.INVALID_TOKEN.getCode(), ErrorCode.INVALID_TOKEN.getMessage()));
    }

    @ExceptionHandler(OdokExceptions.class)
    public ResponseEntity<?> handleOdokException(OdokExceptions e){
        e.printStackTrace();
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(new Error(e.getErrorCode().getCode(), e.getErrorCode().getMessage()));
    }

}