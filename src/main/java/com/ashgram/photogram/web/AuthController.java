package com.ashgram.photogram.web;

import com.ashgram.photogram.domain.user.User;
import com.ashgram.photogram.service.AuthService;
import com.ashgram.photogram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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

        // 유효성 검사 AOP 실행 -> ValidationAdvice

        // User <- SignupDto
        User user = signupDto.toEntity();
        log.info("user = {}", user);
        authService.join(user);

        return "redirect:/auth/signin";
    }
}
