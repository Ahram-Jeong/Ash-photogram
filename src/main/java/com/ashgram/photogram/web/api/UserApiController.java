package com.ashgram.photogram.web.api;

import com.ashgram.photogram.config.auth.PrincipalDetails;
import com.ashgram.photogram.domain.user.User;
import com.ashgram.photogram.handler.ex.CustomValidationApiException;
import com.ashgram.photogram.service.UserService;
import com.ashgram.photogram.web.dto.CMRespDto;
import com.ashgram.photogram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserApiController {
    private final UserService userService;

    // ******************** 회원 정보 수정 ********************
    @PutMapping("/api/user/{id}")
    public CMRespDto<?> update(@PathVariable long id,
                               @Valid UserUpdateDto userUpdateDto, BindingResult bindingResult, // BindingResul는 꼭 @Valid 다음 파라미터에 와야 함
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(bindingResult.hasErrors()) { // 1. BindingResult 에러가 담기면
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage()); // 2. errorMap에 담고,
            }
            throw new CustomValidationApiException("***** Failed validation chk *****", errorMap); // 3. CustomValidationException 예외를 강제로 발생
        } else {
            User userEntity = userService.modify(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity); // modify() 후, 세션 정보 변경
            return new CMRespDto<>(1, "회원 정보 수정 완료", userEntity);
        }
    }
}
