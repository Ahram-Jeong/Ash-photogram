package com.ashgram.photogram.web.api;

import com.ashgram.photogram.config.auth.PrincipalDetails;
import com.ashgram.photogram.domain.user.User;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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
        // 유효성 검사 AOP 실행 -> ValidationAdvice

        User userEntity = userService.modify(id, userUpdateDto.toEntity());
        principalDetails.setUser(userEntity); // modify() 후, 세션 정보 변경
        return new CMRespDto<>(1, "회원 정보 수정 완료", userEntity); // 응답 시, userEntity의 모든 getter 함수가 호출 되고, JSON으로 파싱하여 응답
    }

    // ******************** 프로필 사진 변경 ********************
    @PutMapping("/api/user/{principalId}/profileImageUrl")
    public ResponseEntity<?> profileImageUrlUpdate(@PathVariable long principalId, MultipartFile profileImageFile, // MultipartFile의 변수명은 꼭 해당 태그 name 값을 가져와야 함
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User userEntity = userService.updateProfileImage(principalId, profileImageFile);
        principalDetails.setUser(userEntity); // 세션 변경
        return new ResponseEntity<>(new CMRespDto<>(1, "사진 수정 성공", null), HttpStatus.OK);
    }

    // ******************** 구독 정보 ********************
    @GetMapping("/api/user/{pageUserId}/subscribe")
    public ResponseEntity<?> subscribeList(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long pageUserId) {
        List<SubscribeDto> subscribeDto = subscribeService.subList(principalDetails.getUser().getId(), pageUserId);
        return new ResponseEntity<>(new CMRespDto<>(1, "subscribeList 불러오기 성공", subscribeDto), HttpStatus.OK);
    }
}
