package com.ashgram.photogram.service;

import com.ashgram.photogram.domain.user.User;
import com.ashgram.photogram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // ******************** 회원가입 ********************
    @Transactional // Write (Insert, Delete, Update)
    public User join(User user) {
        // PW 암호화
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        // 권한 부여
        user.setRole("ROLE_USER"); // 관리자 : ROLE_ADMIN

        User userEntity = userRepository.save(user);
        return userEntity;
    }
}
