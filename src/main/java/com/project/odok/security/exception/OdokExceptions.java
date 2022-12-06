package com.project.odok.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OdokExceptions extends RuntimeException {
    private ErrorCode errorCode;
}
