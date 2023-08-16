package com.ashgram.photogram.handler.ex;

import lombok.Getter;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException{
    static final long serialVersionUID = 1L; // JVM의 객체 구분 시, 사용

    @Getter
    private Map<String, String> errorMap;

    public CustomValidationApiException(String message, Map<String, String> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }

    public CustomValidationApiException(String message) {
        super(message);
    }
}
