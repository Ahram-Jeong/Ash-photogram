package com.ashgram.photogram.web;

import com.ashgram.photogram.domain.user.User;
import com.ashgram.photogram.service.AuthService;
import com.ashgram.photogram.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor // final 필드를 DI할 때 사용
@Slf4j
public class AuthController {
    private final AuthService authService;

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    @PostMapping("/auth/signup")
    public String signup(SignupDto signupDto) {
        log.info("signupDto = {}", signupDto);

        // User <- SignupDto
        User user = signupDto.toEntity();
        log.info("user = {}", user);
        User userEntity = authService.join(user);
        return "redirect:/auth/signin";
    }
}
