package com.ashgram.photogram.web;

import com.ashgram.photogram.config.auth.PrincipalDetails;
import com.ashgram.photogram.service.UserService;
import com.ashgram.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // ******************** 프로필 ********************
    @GetMapping("/user/{pageUserId}")
    public String profile(@PathVariable long pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        UserProfileDto userProfileDto = userService.showUser(pageUserId, principalDetails.getUser().getId());
        model.addAttribute("dto", userProfileDto);
        return "user/profile";
    }

    // ******************** 회원 정보 수정 ********************
    @GetMapping("/user/{id}/update")
    public String update(@PathVariable long id, @AuthenticationPrincipal PrincipalDetails principalDetails) { // @AuthenticationPrincipal : 세션 정보 접근을 위한 어노테이션
        return "user/update";
    }

}
