package com.ashgram.photogram.web;

import com.ashgram.photogram.config.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class UserController {
    // ******************** 프로필 ********************
    @GetMapping("/user/{id}")
    public String profile(@PathVariable long id) {
        return "/user/profile";
    }

    // ******************** 회원 정보 수정 ********************
    @GetMapping("/user/{id}/update")
    public String update(@PathVariable long id, @AuthenticationPrincipal PrincipalDetails principalDetails) { // @AuthenticationPrincipal : 세션 정보 접근을 위한 어노테이션
        log.info("session_info = {}", principalDetails.getUser());
        return "user/update";
    }

}
