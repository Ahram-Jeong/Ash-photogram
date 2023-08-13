package com.ashgram.photogram.handler;

import com.ashgram.photogram.handler.ex.CustomValidationException;
import com.ashgram.photogram.util.Script;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController // return을 위해 사용
@ControllerAdvice // Controller에서 발생한 모든 Exception을 한 곳에서 처리하고 관리하는 어노테이션
public class ControllerExceptionHandler {
    /**
     * 반환 타입 : CMRespDto vs Script
     * 1. 클라이언트에게 응답 -> Script
     * 2. Ajax 통신, Android 통신 -> CMRespDto
     */

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {
        return Script.back(e.getErrorMap().toString());
    }
}
