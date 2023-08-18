package com.ashgram.photogram.service;

import com.ashgram.photogram.domain.user.User;
import com.ashgram.photogram.domain.user.UserRepository;
import com.ashgram.photogram.handler.ex.CustomException;
import com.ashgram.photogram.handler.ex.CustomValidationApiException;
import com.ashgram.photogram.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // ******************** 프로필 ********************
    @Transactional(readOnly = true) // 읽기 전용으로, 변경 감지를 하지 않음
    public UserProfileDto showUser(long pageUserId, long principalId) {
        UserProfileDto userProfileDto = new UserProfileDto();

        User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{ // id 값이 없으면 예외 발생
            throw new CustomException("요청하신 프로필이 존재하지 않습니다.");
        });

        userProfileDto.setUser(userEntity);
        userProfileDto.setPageOwnerState(pageUserId == principalId); // true or false
        userProfileDto.setImageCount(userEntity.getImages().size());
        return userProfileDto;
    }

    // ******************** 회원 정보 수정 ********************
    @Transactional
    public User modify(long id, User user) {
        // 1. 영속화
        // Optional 처리: .get()대신 .orElseThrow()를 사용하는 것이 바람직
        User userEntity = userRepository.findById(id).orElseThrow(() -> { // id 값이 없으면 예외 발생
            return new CustomValidationApiException("찾을 수 없는 ID입니다.");
        });
        // 2. 영속화 된 obj 수정
        userEntity.setName(user.getName());
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);

        userEntity.setWebsite(user.getWebsite());
        userEntity.setBio(user.getBio());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
    }
}
