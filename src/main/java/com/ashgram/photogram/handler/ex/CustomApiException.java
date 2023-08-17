package com.ashgram.photogram.handler.ex;

public class CustomApiException extends RuntimeException{
    static final long serialVersionUID = 1L; // JVM의 객체 구분 시, 사용

    public CustomApiException(String message) {
        super(message);
    }
}
