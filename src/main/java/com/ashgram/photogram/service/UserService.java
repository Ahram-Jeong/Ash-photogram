package com.ashgram.photogram.service;

import com.ashgram.photogram.domain.user.User;
import com.ashgram.photogram.domain.user.UserRepository;
import com.ashgram.photogram.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
