package com.ashgram.photogram.web.api;

import com.ashgram.photogram.config.auth.PrincipalDetails;
import com.ashgram.photogram.domain.user.User;
import com.ashgram.photogram.handler.ex.CustomValidationApiException;
import com.ashgram.photogram.service.SubscribeService;
import com.ashgram.photogram.service.UserService;
import com.ashgram.photogram.web.dto.CMRespDto;
import com.ashgram.photogram.web.dto.subscribe.SubscribeDto;
import com.ashgram.photogram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final SubscribeService subscribeService;

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
            return new CMRespDto<>(1, "회원 정보 수정 완료", userEntity); // 응답 시, userEntity의 모든 getter 함수가 호출 되고, JSON으로 파싱하여 응답
        }
    }

    // ******************** 구독 정보 ********************
    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@PathVariable long pageUserId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<SubscribeDto> subscribeDto = subscribeService.subList(pageUserId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "subscribeList 불러오기 성공", subscribeDto), HttpStatus.OK);
    }
}
