package com.project.odok.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private boolean success;
    private T data;
    private String error;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }

    public static <T> ResponseDto<T> fail(String message) {
        return new ResponseDto<>(false, null, message);
    }
}
