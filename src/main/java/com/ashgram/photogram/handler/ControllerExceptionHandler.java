package com.ashgram.photogram.handler;

import com.ashgram.photogram.handler.ex.CustomValidationException;
import com.ashgram.photogram.web.dto.CMRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // return을 위해 사용
@ControllerAdvice // Controller에서 발생한 모든 Exception을 한 곳에서 처리하고 관리하는 어노테이션
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public CMRespDto<?> validationException(CustomValidationException e) {
        return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap()); // CustomValidationException 발생 시, CMRespDto 반환 후 종료
    }
}
