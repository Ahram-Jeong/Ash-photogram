package com.ashgram.photogram.web;

import com.ashgram.photogram.domain.user.User;
import com.ashgram.photogram.handler.ex.CustomValidationException;
import com.ashgram.photogram.service.AuthService;
import com.ashgram.photogram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor // final 필드를 DI할 때 사용
@Slf4j
public class AuthController {
    private final AuthService authService;

    // ******************** 로그인 ********************
    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    // ******************** 회원가입 ********************
    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // 유효성 검사에서 에러 발생 시 -> BindingResult
        log.info("signupDto = {}", signupDto);

        if(bindingResult.hasErrors()) { // 1. BindingResult 에러가 담기면
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage()); // 2. errorMap에 담고,
            }
            throw new CustomValidationException("***** Failed validation chk *****", errorMap); // 3. CustomValidationException 예외를 강제로 발생
        } else {
            // User <- SignupDto
            User user = signupDto.toEntity();
            log.info("user = {}", user);
            authService.join(user);
            return "redirect:/auth/signin";
        }
    }
}
