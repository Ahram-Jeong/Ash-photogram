package com.ashgram.photogram.handler;

import com.ashgram.photogram.handler.ex.CustomValidationApiException;
import com.ashgram.photogram.handler.ex.CustomValidationException;
import com.ashgram.photogram.util.Script;
import com.ashgram.photogram.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) { // HttpStatus 상태코드를 전달하기 위해 ResponseEntity 사용
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }
}
