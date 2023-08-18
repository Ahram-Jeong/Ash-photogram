package com.ashgram.photogram.handler.ex;

import java.util.Map;

public class CustomException extends RuntimeException{
    static final long serialVersionUID = 1L; // JVM의 객체 구분 시, 사용

    public CustomException(String message) {
        super(message);
    }
}
