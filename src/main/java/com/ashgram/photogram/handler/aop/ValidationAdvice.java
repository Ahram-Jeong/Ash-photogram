package com.ashgram.photogram.handler.aop;

import com.ashgram.photogram.handler.ex.CustomValidationApiException;
import com.ashgram.photogram.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ValidationAdvice {
    // web.api AOP
    @Around("execution(* com.ashgram.photogram.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // Controller의 메소드들 보다 먼저 실행
        // proceedingJoinPoint Controller의 모든 메소드에 접근할 수 있는 변수
        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg:args) {

            if(arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg; // 다운캐스팅
                // 유효성 검사 로직 추가
                if(bindingResult.hasErrors()) { // 1. BindingResult 에러가 담기면
                    Map<String, String> errorMap = new HashMap<>();
                    for(FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage()); // 2. errorMap에 담고,
                    }
                    throw new CustomValidationApiException("***** Failed validation chk *****", errorMap); // 3. CustomValidationApiException 예외를 강제로 발생
                }
            }

        }
        return proceedingJoinPoint.proceed(); // Controller의 메소드 실행
    }

    // web AOP
    @Around("execution(* com.ashgram.photogram.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        for(Object arg:args) {

            if(arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                // 유효성 검사 로직 추가
                if(bindingResult.hasErrors()) { // 1. BindingResult 에러가 담기면
                    Map<String, String> errorMap = new HashMap<>();
                    for(FieldError error : bindingResult.getFieldErrors()) {
                        errorMap.put(error.getField(), error.getDefaultMessage()); // 2. errorMap에 담고,
                    }
                    throw new CustomValidationException("***** Failed validation chk *****", errorMap); // 3. CustomValidationException 예외를 강제로 발생
                }
            }

        }
        return proceedingJoinPoint.proceed();
    }
}
