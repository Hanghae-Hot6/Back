package com.project.odok.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Error {

    private Integer status;
    private String field;
    private String message;

}
